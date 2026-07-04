package com.pratyaksh.omnidocs_ai.ai.parser.exception;

public class UnsupportedDocumentTypeException extends RuntimeException {

  public UnsupportedDocumentTypeException(String contentType) {
    super("Unsupported document type: " + contentType);
  }
}