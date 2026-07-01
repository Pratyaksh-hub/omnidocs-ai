package com.pratyaksh.omnidocs_ai.common.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse{

    LocalDateTime timestamp;

    int status;

    String message;

    List<String> errors;
}