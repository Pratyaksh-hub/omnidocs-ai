package com.pratyaksh.omnidocs_ai.document.service.delete;

import com.pratyaksh.omnidocs_ai.document.entity.Document;
import com.pratyaksh.omnidocs_ai.document.entity.StoredFile;
import com.pratyaksh.omnidocs_ai.document.exception.DocumentNotFoundException;
import com.pratyaksh.omnidocs_ai.document.repository.DocumentRepository;
import com.pratyaksh.omnidocs_ai.document.repository.StoredFileRepository;
import com.pratyaksh.omnidocs_ai.storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class DocumentDeleteServiceImpl implements DocumentDeleteService {

  private final DocumentRepository documentRepository;
  private final StoredFileRepository storedFileRepository;
  private final StorageService storageService;

  @Override
  public void delete(UUID documentUuid) {

    Document document = documentRepository
        .findByUuidAndDeletedFalse(documentUuid)
        .orElseThrow(() -> new DocumentNotFoundException(documentUuid));

    document.markDeleted();

    StoredFile storedFile = document.getStoredFile();

    storedFile.setReferenceCount(
        storedFile.getReferenceCount() - 1
    );

    if (storedFile.getReferenceCount() == 0) {

      storageService.delete(
          storedFile.getStoredFileName()
      );

      storedFile.markDeleted();

      storedFileRepository.save(storedFile);
    }

    documentRepository.save(document);
  }
}