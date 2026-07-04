package com.pratyaksh.omnidocs_ai.audit.service;

import com.pratyaksh.omnidocs_ai.audit.dto.AuditLogRequest;
import com.pratyaksh.omnidocs_ai.audit.entity.AuditLog;
import com.pratyaksh.omnidocs_ai.audit.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuditLogServiceImpl implements AuditLogService {

  private final AuditLogRepository repository;

  @Override
  public void log(AuditLogRequest request) {

    repository.save(
        AuditLog.from(request)
    );

    log.debug(
        "Audit log saved. Action={}, ResourceType={}, ResourceUuid={}",
        request.getAction(),
        request.getResourceType(),
        request.getResourceUuid()
    );
  }
}