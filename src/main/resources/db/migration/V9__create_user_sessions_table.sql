CREATE TABLE user_sessions
(
    id BIGSERIAL PRIMARY KEY,

    uuid UUID NOT NULL UNIQUE,

    user_id BIGINT NOT NULL,

    device_name VARCHAR(255),

    browser VARCHAR(255),

    operating_system VARCHAR(255),

    ip_address VARCHAR(100),

    user_agent TEXT,

    last_activity_at TIMESTAMP NOT NULL,

    expires_at TIMESTAMP NOT NULL,

    revoked BOOLEAN NOT NULL DEFAULT FALSE,

    created_by VARCHAR(255),

    created_at TIMESTAMP NOT NULL,

    updated_by VARCHAR(255),

    updated_at TIMESTAMP NOT NULL,

    deleted BOOLEAN NOT NULL DEFAULT FALSE,

    deleted_at TIMESTAMP,

    CONSTRAINT fk_user_session_user
        FOREIGN KEY(user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_user_session_user
ON user_sessions(user_id);

CREATE INDEX idx_user_session_uuid
ON user_sessions(uuid);

CREATE INDEX idx_user_session_revoked
ON user_sessions(revoked);