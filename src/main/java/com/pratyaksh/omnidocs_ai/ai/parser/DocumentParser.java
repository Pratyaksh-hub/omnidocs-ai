package com.pratyaksh.omnidocs_ai.ai.parser;

import com.pratyaksh.omnidocs_ai.document.entity.Document;

public interface DocumentParser {

  boolean supports(String contentType);

  String extractText(Document document);

}
