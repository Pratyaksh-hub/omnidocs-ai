package com.pratyaksh.omnidocs_ai.ai.event.publisher;

import com.pratyaksh.omnidocs_ai.ai.event.DocumentUploadedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DocumentEventPublisherImpl
    implements DocumentEventPublisher {

  private final ApplicationEventPublisher publisher;

  @Override
  public void publish(DocumentUploadedEvent event) {
    publisher.publishEvent(event);
  }
}