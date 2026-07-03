CREATE TABLE audit_logs
(
    id BIGSERIAL PRIMARY KEY,

    uuid UUID NOT NULL UNIQUE,

    user_id BIGINT,

    action VARCHAR(100) NOT NULL,

    resource_type VARCHAR(100),

    resource_uuid UUID,

    status VARCHAR(30) NOT NULL,

    ip_address VARCHAR(100),

    user_agent VARCHAR(1000),

    metadata JSONB,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL,

    deleted BOOLEAN NOT NULL DEFAULT FALSE,

    deleted_at TIMESTAMP,

    version BIGINT NOT NULL DEFAULT 0,

    CONSTRAINT fk_audit_log_user
        FOREIGN KEY (user_id)
            REFERENCES users(id)
);

CREATE INDEX idx_audit_uuid
    ON audit_logs(uuid);

CREATE INDEX idx_audit_user
    ON audit_logs(user_id);

CREATE INDEX idx_audit_action
    ON audit_logs(action);

CREATE INDEX idx_audit_resource
    ON audit_logs(resource_uuid);

CREATE INDEX idx_audit_created
    ON audit_logs(created_at);