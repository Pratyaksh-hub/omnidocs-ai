package com.pratyaksh.omnidocs_ai.document.controller;

import com.pratyaksh.omnidocs_ai.common.response.ApiResponse;
import com.pratyaksh.omnidocs_ai.document.dto.UploadDocumentRequest;
import com.pratyaksh.omnidocs_ai.document.dto.UploadDocumentResponse;
import com.pratyaksh.omnidocs_ai.document.exception.DocumentUploadException;
import com.pratyaksh.omnidocs_ai.document.exception.InvalidDocumentException;
import com.pratyaksh.omnidocs_ai.document.service.application.DocumentApplicationService;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
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
public class DocumentController {

  private final DocumentApplicationService documentApplicationService;

  private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
      MediaType.APPLICATION_PDF_VALUE,
      MediaType.TEXT_PLAIN_VALUE,
      "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
      "application/msword"
  );

  private static final long MAX_FILE_SIZE = 20 * 1024 * 1024;

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
        documentApplicationService.uploadDocument(request);

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
}