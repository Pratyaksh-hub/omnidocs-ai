package com.pratyaksh.omnidocs_ai.document.service.download;

import com.pratyaksh.omnidocs_ai.document.response.DownloadDocumentResponse;
import com.pratyaksh.omnidocs_ai.document.entity.Document;
import com.pratyaksh.omnidocs_ai.document.exception.DocumentNotFoundException;
import com.pratyaksh.omnidocs_ai.document.repository.DocumentRepository;
import com.pratyaksh.omnidocs_ai.storage.service.StorageService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DocumentDownloadServiceImpl implements DocumentDownloadService {

  private final DocumentRepository documentRepository;
  private final StorageService storageService;

  @Override
  public DownloadDocumentResponse download(UUID documentUuid) {

    Document document = documentRepository.findByUuid(documentUuid)
        .orElseThrow(() -> new DocumentNotFoundException(documentUuid));

    Resource resource = storageService.load(
        document.getStoredFile().getStoredFileName()
    );

    return DownloadDocumentResponse.builder()
        .resource(resource)
        .originalFileName(document.getOriginalFileName())
        .contentType(document.getContentType())
        .fileSize(document.getStoredFile().getFileSize())
        .build();
  }
}