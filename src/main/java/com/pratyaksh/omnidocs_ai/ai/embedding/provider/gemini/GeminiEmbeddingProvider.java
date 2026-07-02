package com.pratyaksh.omnidocs_ai.ai.embedding.provider.gemini;

import com.pratyaksh.omnidocs_ai.ai.config.GeminiProperties;
import com.pratyaksh.omnidocs_ai.ai.dto.Content;
import com.pratyaksh.omnidocs_ai.ai.dto.EmbeddingRequest;
import com.pratyaksh.omnidocs_ai.ai.dto.Part;
import com.pratyaksh.omnidocs_ai.ai.dto.embedding.GeminiEmbeddingResponse;
import com.pratyaksh.omnidocs_ai.ai.embedding.provider.EmbeddingProvider;
import com.pratyaksh.omnidocs_ai.ai.exception.GeminiException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class GeminiEmbeddingProvider implements EmbeddingProvider {

  private final RestClient restClient;
  private final GeminiProperties properties;

  @Override
  public float[] generateEmbedding(String text) {

    EmbeddingRequest request =
        EmbeddingRequest.builder()
            .content(
                Content.builder()
                    .parts(
                        new Part[]{
                            Part.builder()
                                .text(text)
                                .build()
                        }
                    )
                    .build()
            )
            .build();

    GeminiEmbeddingResponse response =
        restClient.post()
            .uri(
                properties.getBaseUrl()
                    + "/v1beta/models/"
                    + properties.getEmbeddingModel()
                    + ":embedContent?key="
                    + properties.getApiKey()
            )
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .body(GeminiEmbeddingResponse.class);

    if (response == null ||
        response.getEmbedding() == null ||
        response.getEmbedding().getValues() == null) {

      throw new GeminiException("Failed to generate embedding.");
    }

    List<Float> values =
        response.getEmbedding().getValues();

    float[] result = new float[values.size()];

    for (int i = 0; i < values.size(); i++) {
      result[i] = values.get(i);
    }

    return result;
  }
}