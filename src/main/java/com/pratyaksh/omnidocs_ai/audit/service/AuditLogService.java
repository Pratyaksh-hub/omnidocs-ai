package com.pratyaksh.omnidocs_ai.audit.service;

import com.pratyaksh.omnidocs_ai.audit.dto.AuditLogRequest;

public interface AuditLogService {

  void log(AuditLogRequest request);

}