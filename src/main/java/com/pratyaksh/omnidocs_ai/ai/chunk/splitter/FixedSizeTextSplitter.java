package com.pratyaksh.omnidocs_ai.ai.chunk.splitter;

import com.pratyaksh.omnidocs_ai.ai.chunk.model.TextChunk;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class FixedSizeTextSplitter implements TextSplitter {

  private static final int CHUNK_SIZE = 1000;

  @Override
  public List<TextChunk> split(String text) {

    List<TextChunk> chunks = new ArrayList<>();

    int chunkIndex = 0;

    for (int start = 0; start < text.length(); start += CHUNK_SIZE) {

      int end = Math.min(start + CHUNK_SIZE, text.length());

      String content = text.substring(start, end);

      chunks.add(
          TextChunk.builder()
              .chunkIndex(chunkIndex++)
              .content(content)
              .startOffset(start)
              .endOffset(end)
              .characterCount(content.length())
              .build()
      );
    }

    return chunks;
  }
}