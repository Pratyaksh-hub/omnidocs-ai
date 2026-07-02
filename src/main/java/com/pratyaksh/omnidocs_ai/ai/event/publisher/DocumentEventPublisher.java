package com.pratyaksh.omnidocs_ai.ai.event.publisher;

import com.pratyaksh.omnidocs_ai.ai.event.DocumentUploadedEvent;

public interface DocumentEventPublisher {

  void publish(DocumentUploadedEvent event);

}