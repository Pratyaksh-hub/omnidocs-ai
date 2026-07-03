CREATE TABLE document_chunks
(
    id BIGSERIAL PRIMARY KEY,

    uuid UUID NOT NULL UNIQUE,

    document_id BIGINT NOT NULL,

    chunk_index INT NOT NULL,

    content TEXT NOT NULL,

    character_count INT NOT NULL,

    token_count INT,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL,

    version BIGINT NOT NULL,

    deleted BOOLEAN NOT NULL DEFAULT FALSE,

    deleted_at TIMESTAMP,

    CONSTRAINT fk_chunk_document
        FOREIGN KEY (document_id)
        REFERENCES documents(id)
);