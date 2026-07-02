package com.pratyaksh.omnidocs_ai.document.controller;

import com.pratyaksh.omnidocs_ai.common.response.ApiResponse;
import com.pratyaksh.omnidocs_ai.common.response.PageResponse;
import com.pratyaksh.omnidocs_ai.document.exception.DocumentUploadException;
import com.pratyaksh.omnidocs_ai.document.exception.InvalidDocumentException;
import com.pratyaksh.omnidocs_ai.document.facade.DocumentFacade;
import com.pratyaksh.omnidocs_ai.document.request.UploadDocumentRequest;
import com.pratyaksh.omnidocs_ai.document.response.DocumentResponse;
import com.pratyaksh.omnidocs_ai.document.response.DocumentSummaryResponse;
import com.pratyaksh.omnidocs_ai.document.response.DownloadDocumentResponse;
import com.pratyaksh.omnidocs_ai.document.response.UploadDocumentResponse;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
@Validated
@CrossOrigin
public class DocumentController {

  private static final long MAX_FILE_SIZE = 20 * 1024 * 1024;
  private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
      MediaType.APPLICATION_PDF_VALUE,
      MediaType.TEXT_PLAIN_VALUE,
      "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
      "application/msword"
  );

  private final DocumentFacade documentFacade;

  @PostMapping(
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<ApiResponse<UploadDocumentResponse>> uploadDocument(
      @RequestParam UUID workspaceUuid,
      @RequestPart("file") @NotNull MultipartFile file) throws IOException {

    validate(file);

    UploadDocumentRequest request;

    try {
      request = UploadDocumentRequest.builder()
          .workspaceUuid(workspaceUuid)
          .inputStream(file.getInputStream())
          .originalFilename(file.getOriginalFilename())
          .contentType(file.getContentType())
          .fileSize(file.getSize())
          .build();
    } catch (IOException ex) {
      throw new DocumentUploadException("Failed to read uploaded file.", ex);
    }

    UploadDocumentResponse response =
        documentFacade.uploadDocument(request);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(response));
  }

  private void validate(MultipartFile file) {

    if (file.isEmpty()) {
      throw new InvalidDocumentException("File cannot be empty.");
    }

    if (!StringUtils.hasText(file.getOriginalFilename())) {
      throw new InvalidDocumentException("Original filename is required.");
    }

    if (!ALLOWED_CONTENT_TYPES.contains(file.getContentType())) {
      throw new InvalidDocumentException("Unsupported file type.");
    }

    if (file.getSize() > MAX_FILE_SIZE) {
      throw new InvalidDocumentException("Maximum upload size is 20 MB.");
    }
  }

  @GetMapping("/{documentUuid}")
  public ResponseEntity<ApiResponse<DocumentResponse>> getDocument(
      @PathVariable UUID documentUuid) {

    return ResponseEntity.ok(
        ApiResponse.success(
            documentFacade.getDocument(documentUuid)
        )
    );
  }

  @GetMapping("/{documentUuid}/download")
  public ResponseEntity<Resource> downloadDocument(
      @PathVariable UUID documentUuid,
      @RequestParam(value = "inline", required = false, defaultValue = "false") boolean inline) {

    DownloadDocumentResponse response =
        documentFacade.downloadDocument(documentUuid);

    // Determine disposition layout based on the UI request flag
    String dispositionType = inline ? "inline" : "attachment";
    String contentDisposition = dispositionType + "; filename=\"" + response.getOriginalFileName() + "\"";

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
        .header(
            HttpHeaders.CONTENT_LENGTH,
            String.valueOf(response.getFileSize())
        )
        .contentType(MediaType.parseMediaType(response.getContentType()))
        .body(response.getResource());
  }

  @DeleteMapping("/{documentUuid}")
  public ResponseEntity<Void> delete(
      @PathVariable UUID documentUuid) {

    documentFacade.deleteDocument(documentUuid);

    return ResponseEntity.noContent().build();
  }

  @GetMapping("/deleted")
  public ResponseEntity<ApiResponse<PageResponse<DocumentSummaryResponse>>> getDeletedDocuments(
      Pageable pageable) {

    return ResponseEntity.ok(
        ApiResponse.success(
            documentFacade.getDeletedDocuments(pageable)
        )
    );
  }
}