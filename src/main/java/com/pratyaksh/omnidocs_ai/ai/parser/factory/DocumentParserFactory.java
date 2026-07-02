package com.pratyaksh.omnidocs_ai.ai.parser.factory;

import com.pratyaksh.omnidocs_ai.ai.parser.DocumentParser;
import com.pratyaksh.omnidocs_ai.ai.parser.exception.UnsupportedDocumentTypeException;
import com.pratyaksh.omnidocs_ai.document.entity.Document;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DocumentParserFactory {

  private final List<DocumentParser> parsers;

  public DocumentParser getParser(Document document) {

    return parsers.stream()
        .filter(parser -> parser.supports(document.getContentType()))
        .findFirst()
        .orElseThrow(() ->
            new UnsupportedDocumentTypeException(
                document.getContentType()
            ));
  }
}