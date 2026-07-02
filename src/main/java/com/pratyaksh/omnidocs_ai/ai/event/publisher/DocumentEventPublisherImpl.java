package com.pratyaksh.omnidocs_ai.ai.event.publisher;

import com.pratyaksh.omnidocs_ai.ai.event.DocumentUploadedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DocumentEventPublisherImpl
    implements DocumentEventPublisher {

  private final ApplicationEventPublisher publisher;

  @Override
  public void publish(DocumentUploadedEvent event) {
    publisher.publishEvent(event);
    log.info("Event published successfully");
  }
}