package com.pratyaksh.omnidocs_ai.audit.repository;

import com.pratyaksh.omnidocs_ai.audit.entity.AuditAction;
import com.pratyaksh.omnidocs_ai.audit.entity.AuditLog;
import com.pratyaksh.omnidocs_ai.user.entity.User;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

  List<AuditLog> findByUserOrderByCreatedAtDesc(User user);

  List<AuditLog> findByAction(AuditAction action);

  List<AuditLog> findByResourceUuid(UUID resourceUuid);

}