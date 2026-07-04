package com.pratyaksh.omnidocs_ai.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {

  @NotBlank(message = "First name is required")
  @Size(max = 100, message = "First name cannot exceed 100 characters")
  private String firstName;

  @NotBlank(message = "Last name is required")
  @Size(max = 100, message = "Last name cannot exceed 100 characters")
  private String lastName;

  @NotBlank(message = "Email address is required")
  @Email(message = "Please provide a valid email address")
  private String email;

  @NotBlank(message = "Password is required")
  @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters long")
  private String password;

}