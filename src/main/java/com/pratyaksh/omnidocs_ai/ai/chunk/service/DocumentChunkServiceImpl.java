package com.pratyaksh.omnidocs_ai.ai.chunk.service;

import com.pratyaksh.omnidocs_ai.ai.chunk.mapper.DocumentChunkMapper;
import com.pratyaksh.omnidocs_ai.ai.chunk.repository.DocumentChunkRepository;
import com.pratyaksh.omnidocs_ai.ai.embedding.repository.DocumentEmbeddingRepository;
import com.pratyaksh.omnidocs_ai.ai.processor.model.ProcessingContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DocumentChunkServiceImpl
    implements DocumentChunkService {

  private final DocumentChunkRepository repository;
  private final DocumentEmbeddingRepository embeddingRepository;
  private final DocumentChunkMapper mapper;

  @Override
  public void saveChunks(ProcessingContext context) {

    embeddingRepository.deleteByChunk_Document_Uuid(
        context.getDocument().getUuid()
    );

    repository.deleteByDocument_Uuid(
        context.getDocument().getUuid()
    );

    repository.saveAll(
        context.getChunks()
            .stream()
            .map(chunk ->
                mapper.toEntity(
                    chunk,
                    context.getDocument()
                )
            )
            .toList()
    );
  }
}