-- =========================================================================
-- 1. WORKSPACES TABLE
-- Holds the top-level container isolation for documents and settings
-- =========================================================================
CREATE TABLE workspaces
(
    id BIGSERIAL PRIMARY KEY,

    -- Unique identifier used for API exposure to prevent sequential ID leaking
    uuid UUID NOT NULL UNIQUE,

    name VARCHAR(150) NOT NULL,

    description VARCHAR(1000),

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL,

    -- Used for optimistic locking in Hibernate (@Version)
    version BIGINT NOT NULL
);

-- Index to optimize lookups by business UUID
CREATE INDEX idx_workspace_uuid
ON workspaces(uuid);


-- =========================================================================
-- 2. STORED FILES TABLE
-- Tracks the physical metadata and deduplication constraints of files
-- =========================================================================
CREATE TABLE stored_files
(
    id BIGSERIAL PRIMARY KEY,

    -- Added to fix Hibernate validation error: maps to 'uuid' field in Java entity
    uuid UUID NOT NULL UNIQUE,

    -- SHA-256 or similar hash used to enforce global file deduplication
    checksum VARCHAR(64) NOT NULL,

    stored_file_name VARCHAR(255) NOT NULL,

    file_size BIGINT NOT NULL,

    -- Tracks how many documents point to this physical file block
    reference_count BIGINT NOT NULL DEFAULT 1,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL,

    -- Used for optimistic locking in Hibernate (@Version)
    version BIGINT NOT NULL,

    CONSTRAINT uk_stored_file_checksum
        UNIQUE(checksum),

    CONSTRAINT chk_reference_count
        CHECK (reference_count > 0),

    CONSTRAINT chk_file_size
        CHECK (file_size > 0)
);

-- Index to optimize lookups by stored file business UUID
CREATE INDEX idx_stored_file_uuid
ON stored_files(uuid);

-- Index to optimize file deduplication lookups during new uploads
CREATE INDEX idx_stored_file_checksum
ON stored_files(checksum);


-- =========================================================================
-- 3. DOCUMENTS TABLE
-- Represents the business entity combining metadata, workspace, and raw file
-- =========================================================================
CREATE TABLE documents
(
    id BIGSERIAL PRIMARY KEY,

    -- Unique identifier used for API exposure to prevent sequential ID leaking
    uuid UUID NOT NULL UNIQUE,

    workspace_id BIGINT NOT NULL,

    stored_file_id BIGINT NOT NULL,

    original_file_name VARCHAR(255) NOT NULL,

    content_type VARCHAR(100) NOT NULL,

    status VARCHAR(30) NOT NULL,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL,

    -- Used for optimistic locking in Hibernate (@Version)
    version BIGINT NOT NULL,

    CONSTRAINT fk_document_workspace
        FOREIGN KEY (workspace_id)
            REFERENCES workspaces(id),

    CONSTRAINT fk_document_stored_file
        FOREIGN KEY (stored_file_id)
            REFERENCES stored_files(id),

    -- Enforces strict lifecycle state boundaries matching Java Enums
    CONSTRAINT chk_document_status
        CHECK (
            status IN (
                'UPLOADED',
                'PROCESSING',
                'READY',
                'FAILED'
            )
        )
);

-- Performance indexes for querying documents
CREATE INDEX idx_document_uuid
ON documents(uuid);

CREATE INDEX idx_document_workspace
ON documents(workspace_id);

CREATE INDEX idx_document_status
ON documents(status);