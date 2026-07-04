package com.pratyaksh.omnidocs_ai.ai.chunk.mapper;

import com.pratyaksh.omnidocs_ai.ai.chunk.entity.DocumentChunk;
import com.pratyaksh.omnidocs_ai.ai.chunk.model.TextChunk;
import com.pratyaksh.omnidocs_ai.document.entity.Document;
import org.springframework.stereotype.Component;

@Component
public class DocumentChunkMapper {

  public DocumentChunk toEntity(
      TextChunk chunk,
      Document document
  ) {

    return DocumentChunk.create(
        document,
        chunk.getChunkIndex(),
        chunk.getContent(),
        chunk.getCharacterCount()
    );
  }
}