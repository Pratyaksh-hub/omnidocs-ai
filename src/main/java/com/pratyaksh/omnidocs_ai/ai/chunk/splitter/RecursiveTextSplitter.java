package com.pratyaksh.omnidocs_ai.ai.chunk.splitter;

import com.pratyaksh.omnidocs_ai.ai.chunk.model.TextChunk;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class RecursiveTextSplitter implements TextSplitter {

  private static final int CHUNK_SIZE = 1000;
  private static final int CHUNK_OVERLAP = 200;

  @Override
  public List<TextChunk> split(String text) {

    List<TextChunk> chunks = new ArrayList<>();

    if (text == null || text.isBlank()) {
      return chunks;
    }

    int chunkIndex = 0;
    int start = 0;

    while (start < text.length()) {

      int end = Math.min(start + CHUNK_SIZE, text.length());

      if (end < text.length()) {

        int paragraph = text.lastIndexOf("\n\n", end);

        if (paragraph > start + CHUNK_SIZE / 2) {
          end = paragraph;
        } else {

          int newline = text.lastIndexOf('\n', end);

          if (newline > start + CHUNK_SIZE / 2) {
            end = newline;
          } else {

            int sentence = text.lastIndexOf('.', end);

            if (sentence > start + CHUNK_SIZE / 2) {
              end = sentence + 1;
            } else {

              int space = text.lastIndexOf(' ', end);

              if (space > start + CHUNK_SIZE / 2) {
                end = space;
              }
            }
          }
        }
      }

      String content = text.substring(start, end).trim();

      chunks.add(
          TextChunk.builder()
              .chunkIndex(chunkIndex++)
              .content(content)
              .startOffset(start)
              .endOffset(end)
              .characterCount(content.length())
              .build()
      );

      if (end == text.length()) {
        break;
      }

      start = Math.max(end - CHUNK_OVERLAP, start + 1);
    }

    return chunks;
  }
}