package com.security.sga.Authentication.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.security.sga.Authentication.Dto.AuthResponse;
import com.security.sga.Authentication.Dto.LoginRequest;
import com.security.sga.Authentication.Dto.RegisterRequest;
import com.security.sga.Authentication.Repository.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final AuthService authService;
  
    // @PostMapping("/register")
    // public ResponseEntity<AuthResponse> register( @Valid @RequestBody RegisterRequest request) {
    //     return ResponseEntity.ok(authService.register(request));
    // }

    @PostMapping(value = "/register", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE}, produces = "application/json")
    public ResponseEntity<AuthResponse> register(@Valid RegisterRequest request, @RequestParam MultiValueMap<String, String> formData) {
       
        RegisterRequest formRequest = new RegisterRequest();

        formRequest.setFirst_name(formData.getFirst("first_name"));
        formRequest.setLast_name(formData.getFirst("last_name"));
        formRequest.setEmail(formData.getFirst("email"));
        formRequest.setPassword(formData.getFirst("password"));
        request = formRequest;

        return ResponseEntity.ok(authService.register(formRequest));
    }

    // @PostMapping("/login")
    // public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
    //     return ResponseEntity.ok(authService.authenticate(request));
    // }
    
    // @PostMapping(value = "/login", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE}, produces = "application/json")
    // public ResponseEntity<AuthResponse> login(@Valid @RequestParam MultiValueMap<String, String> formData, LoginRequest request) {

    //     LoginRequest formRequest = new LoginRequest();

    //     if(formData.getFirst("email") == null || formData.getFirst("password") == null) {            
    //         if(request.getEmail() == null || request.getPassword() == null) {
    //             throw new IllegalArgumentException("Email and Password must be provided");
    //         } else {
    //             formRequest.setEmail(request.getEmail());
    //             formRequest.setPassword(request.getPassword());
    //             return ResponseEntity.ok(authService.authenticate(formRequest));
                
    //         }
    //     }

    //     formRequest.setEmail(formData.getFirst("email"));
    //     formRequest.setPassword(formData.getFirst("password"));
       
    //     return ResponseEntity.ok(authService.authenticate(formRequest));
        
    // }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid LoginRequest request) {
        LoginRequest formRequest = new LoginRequest();
    
        formRequest.setEmail(request.getEmail());
        formRequest.setPassword(request.getPassword());
        return ResponseEntity.ok(authService.authenticate(formRequest));
    }
    
}
