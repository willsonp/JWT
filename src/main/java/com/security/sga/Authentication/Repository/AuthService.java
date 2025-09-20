package com.security.sga.Authentication.Repository;

import org.springframework.stereotype.Service;

import com.security.sga.Authentication.Dto.AuthResponse;
import com.security.sga.Authentication.Dto.LoginRequest;
import com.security.sga.Authentication.Dto.RegisterRequest;

@Service
public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse authenticate(LoginRequest request);

    

}
