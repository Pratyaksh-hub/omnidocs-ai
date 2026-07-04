CREATE TABLE refresh_tokens
(
    id BIGSERIAL PRIMARY KEY,

    -- From BaseEntity
    uuid UUID NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    version BIGINT NOT NULL,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at TIMESTAMP,

    -- From RefreshToken Entity Mappings
    session_id BIGINT NOT NULL,
    token VARCHAR(512) NOT NULL UNIQUE,
    expires_at TIMESTAMP NOT NULL,
    revoked BOOLEAN NOT NULL DEFAULT FALSE,
    revoked_at TIMESTAMP,
    device_name VARCHAR(255),
    ip_address VARCHAR(100),
    user_agent VARCHAR(1000),

    CONSTRAINT fk_refresh_token_session
        FOREIGN KEY (session_id)
        REFERENCES user_sessions(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_refresh_token_uuid ON refresh_tokens(uuid);
CREATE INDEX idx_refresh_token_session ON refresh_tokens(session_id);
CREATE INDEX idx_refresh_token_token ON refresh_tokens(token);