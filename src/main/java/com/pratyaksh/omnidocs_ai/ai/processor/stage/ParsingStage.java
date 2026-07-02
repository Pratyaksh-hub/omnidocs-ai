package com.pratyaksh.omnidocs_ai.ai.processor.stage;

import com.pratyaksh.omnidocs_ai.ai.parser.DocumentParser;
import com.pratyaksh.omnidocs_ai.ai.parser.factory.DocumentParserFactory;
import com.pratyaksh.omnidocs_ai.ai.processor.model.ProcessingContext;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@RequiredArgsConstructor
public class ParsingStage implements ProcessingStage {

  private final DocumentParserFactory parserFactory;

  @Override
  public void process(ProcessingContext context) {

    DocumentParser parser =
        parserFactory.getParser(context.getDocument());

    context.setExtractedText(
        parser.extractText(context.getDocument())
    );
  }
}