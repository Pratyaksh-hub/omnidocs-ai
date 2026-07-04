package com.pratyaksh.omnidocs_ai.document.service.delete;

import com.pratyaksh.omnidocs_ai.auth.service.CurrentUserService;
import com.pratyaksh.omnidocs_ai.dashboard.repository.UserStatsRepository;
import com.pratyaksh.omnidocs_ai.document.entity.Document;
import com.pratyaksh.omnidocs_ai.document.exception.DocumentNotFoundException;
import com.pratyaksh.omnidocs_ai.document.repository.DocumentRepository;
import com.pratyaksh.omnidocs_ai.document.repository.StoredFileRepository;
import com.pratyaksh.omnidocs_ai.storage.service.StorageService;
import com.pratyaksh.omnidocs_ai.user.entity.User;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DocumentDeleteServiceImpl implements DocumentDeleteService {

  private final DocumentRepository documentRepository;
  private final StoredFileRepository storedFileRepository;
  private final StorageService storageService;
  private final UserStatsRepository userStatsRepository;
  private final CurrentUserService currentUserService;

  @Override
  public void delete(UUID documentUuid) {

    User user = currentUserService.getCurrentUser();

    Document document = documentRepository
        .findByUuidAndWorkspace_OwnerAndDeletedFalse(
            documentUuid,
            user)
        .orElseThrow(() ->
            new DocumentNotFoundException(documentUuid));

    document.markDeleted();

    userStatsRepository.decrementDocumentCount(user.getId());
  }
}