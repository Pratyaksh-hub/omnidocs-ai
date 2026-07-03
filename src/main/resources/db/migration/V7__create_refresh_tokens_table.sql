CREATE TABLE refresh_tokens
(
    id BIGSERIAL PRIMARY KEY,

    uuid UUID NOT NULL UNIQUE,

    user_id BIGINT NOT NULL,

    token VARCHAR(512) NOT NULL UNIQUE,

    expires_at TIMESTAMP NOT NULL,

    revoked BOOLEAN NOT NULL DEFAULT FALSE,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_refresh_token_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_refresh_token_user
ON refresh_tokens(user_id);

CREATE INDEX idx_refresh_token_uuid
ON refresh_tokens(uuid);

CREATE INDEX idx_refresh_token_token
ON refresh_tokens(token);