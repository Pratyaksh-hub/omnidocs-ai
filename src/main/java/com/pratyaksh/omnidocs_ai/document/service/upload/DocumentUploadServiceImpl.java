package com.pratyaksh.omnidocs_ai.document.service.upload;

import com.pratyaksh.omnidocs_ai.ai.event.DocumentUploadedEvent;
import com.pratyaksh.omnidocs_ai.ai.event.publisher.DocumentEventPublisher;
import com.pratyaksh.omnidocs_ai.auth.service.CurrentUserService;
import com.pratyaksh.omnidocs_ai.dashboard.repository.UserStatsRepository;
import com.pratyaksh.omnidocs_ai.document.entity.Document;
import com.pratyaksh.omnidocs_ai.document.entity.StoredFile;
import com.pratyaksh.omnidocs_ai.document.exception.DocumentUploadException;
import com.pratyaksh.omnidocs_ai.document.mapper.DocumentMapper;
import com.pratyaksh.omnidocs_ai.document.repository.DocumentRepository;
import com.pratyaksh.omnidocs_ai.document.repository.StoredFileRepository;
import com.pratyaksh.omnidocs_ai.document.request.UploadDocumentRequest;
import com.pratyaksh.omnidocs_ai.document.response.UploadDocumentResponse;
import com.pratyaksh.omnidocs_ai.storage.model.StorageRequest;
import com.pratyaksh.omnidocs_ai.storage.model.StoredFileMetadata;
import com.pratyaksh.omnidocs_ai.storage.service.StorageService;
import com.pratyaksh.omnidocs_ai.user.entity.User;
import com.pratyaksh.omnidocs_ai.workspace.entity.Workspace;
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
  private final DocumentEventPublisher documentEventPublisher;
  private final UserStatsRepository userStatsRepository;
  private final CurrentUserService currentUserService;

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

    Document savedDocument = documentRepository.save(document);

    documentEventPublisher.publish(
        DocumentUploadedEvent.builder()
            .documentUuid(savedDocument.getUuid())
            .build()
    );

    User user = currentUserService.getCurrentUser();
    userStatsRepository.incrementDocumentCount(
        user.getId()
    );

    return documentMapper.toResponse(savedDocument);
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
          existing.incrementReferenceCount();
          return existing;
        })
        .orElseGet(() ->
            storedFileRepository.save(
                StoredFile.create(
                    metadata.getChecksum(),
                    metadata.getStoredFileName(),
                    metadata.getFileSize()
                )
            )
        );
  }
}