package com.security.sga.Authentication.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank
    private String first_name;

    @NotBlank
    private String last_name;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

}
