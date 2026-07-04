package com.pratyaksh.omnidocs_ai.ai.chunk.splitter;

import com.pratyaksh.omnidocs_ai.ai.chunk.model.TextChunk;
import java.util.List;

public interface TextSplitter {

  List<TextChunk> split(String text);

}