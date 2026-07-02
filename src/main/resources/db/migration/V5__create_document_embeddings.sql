CREATE EXTENSION IF NOT EXISTS vector;

CREATE TABLE document_embeddings
(
    id BIGSERIAL PRIMARY KEY,

    uuid UUID NOT NULL UNIQUE,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL,

    version BIGINT NOT NULL,

    deleted BOOLEAN NOT NULL,

    deleted_at TIMESTAMP,

    chunk_id BIGINT NOT NULL,

    provider VARCHAR(50) NOT NULL,

    model VARCHAR(100) NOT NULL,

    dimensions INT NOT NULL,

    embedding vector(1536) NOT NULL,

    CONSTRAINT fk_embedding_chunk
        FOREIGN KEY (chunk_id)
            REFERENCES document_chunks(id)
);

CREATE INDEX idx_embedding_chunk
ON document_embeddings(chunk_id);