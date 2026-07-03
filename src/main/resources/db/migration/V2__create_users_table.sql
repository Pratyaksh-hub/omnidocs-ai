CREATE TABLE users
(
    id BIGSERIAL PRIMARY KEY,

    uuid UUID NOT NULL UNIQUE,

    first_name VARCHAR(100) NOT NULL,

    last_name VARCHAR(100),

    email VARCHAR(255) NOT NULL UNIQUE,

    password_hash VARCHAR(255) NOT NULL,

    role VARCHAR(30) NOT NULL,

    status VARCHAR(40) NOT NULL,

    email_verified BOOLEAN NOT NULL DEFAULT FALSE,

    email_verified_at TIMESTAMP,

    failed_login_attempts INTEGER NOT NULL DEFAULT 0,

    locked_until TIMESTAMP,

    last_login_at TIMESTAMP,

    last_password_changed_at TIMESTAMP,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL,

    deleted BOOLEAN NOT NULL DEFAULT FALSE,

    deleted_at TIMESTAMP,

    version BIGINT NOT NULL DEFAULT 0
);

CREATE INDEX idx_users_uuid
ON users(uuid);

CREATE INDEX idx_users_email
ON users(email);

CREATE INDEX idx_users_status
ON users(status);