package com.pratyaksh.omnidocs_ai.document.service;

import com.pratyaksh.omnidocs_ai.document.dto.UploadDocumentRequest;
import com.pratyaksh.omnidocs_ai.document.dto.UploadDocumentResponse;
import com.pratyaksh.omnidocs_ai.document.entity.Document;
import com.pratyaksh.omnidocs_ai.document.entity.StoredFile;
import com.pratyaksh.omnidocs_ai.document.entity.Workspace;
import com.pratyaksh.omnidocs_ai.document.exception.DocumentUploadException;
import com.pratyaksh.omnidocs_ai.document.mapper.DocumentMapper;
import com.pratyaksh.omnidocs_ai.document.repository.DocumentRepository;
import com.pratyaksh.omnidocs_ai.document.repository.StoredFileRepository;
import com.pratyaksh.omnidocs_ai.storage.model.StorageRequest;
import com.pratyaksh.omnidocs_ai.storage.model.StoredFileMetadata;
import com.pratyaksh.omnidocs_ai.storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DocumentUploadServiceImpl implements DocumentUploadService {

  private final StoredFileRepository storedFileRepository;
  private final DocumentRepository documentRepository;
  private final StorageService storageService;
  private final DocumentMapper documentMapper;

  @Override
  public UploadDocumentResponse upload(Workspace workspace,
      UploadDocumentRequest request) {

    StoredFileMetadata metadata = storeFile(request);

    StoredFile storedFile = getOrCreateStoredFile(metadata);

    Document document = Document.create(
        workspace,
        storedFile,
        request.getOriginalFilename(),
        request.getContentType()
    );

    documentRepository.save(document);

    return documentMapper.toResponse(document);
  }

  private StoredFileMetadata storeFile(UploadDocumentRequest request) {

    try {

      return storageService.store(
          StorageRequest.builder()
              .inputStream(request.getInputStream())
              .originalFilename(request.getOriginalFilename())
              .fileSize(request.getFileSize())
              .build());

    } catch (Exception ex) {
      throw new DocumentUploadException(
          "Unable to store uploaded document.",
          ex
      );
    }
  }

  private StoredFile getOrCreateStoredFile(StoredFileMetadata metadata) {

    return storedFileRepository.findByChecksum(metadata.getChecksum())
        .map(existing -> {
          existing.setReferenceCount(existing.getReferenceCount() + 1);
          return existing;
        })
        .orElseGet(() ->
            storedFileRepository.save(
                StoredFile.builder()
                    .checksum(metadata.getChecksum())
                    .storedFileName(metadata.getStoredFileName())
                    .fileSize(metadata.getFileSize())
                    .referenceCount(1L)
                    .build()));
  }
}