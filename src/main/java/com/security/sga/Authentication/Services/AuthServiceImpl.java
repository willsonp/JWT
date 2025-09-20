package com.security.sga.Authentication.Services;

import org.springframework.data.convert.ReadingConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.sga.Authentication.Config.JwtService;
import com.security.sga.Authentication.Dto.AuthResponse;
import com.security.sga.Authentication.Dto.LoginRequest;
import com.security.sga.Authentication.Dto.RegisterRequest;
import com.security.sga.Authentication.Enums.Roles;
import com.security.sga.Authentication.Models.User;
import com.security.sga.Authentication.Repository.AuthService;
import com.security.sga.Authentication.Repository.UserRepository;

@Service
@ReadingConverter
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    
    public AuthServiceImpl(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder,
                AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
}

    @Override
    public AuthResponse register(RegisterRequest request) {
        // Implement registration logic
        User user = User.builder()
                .first_name(request.getFirst_name())
                .last_name(request.getLast_name())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // Hash the password
                .role(Roles.ROLE_USER) // Default role
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user);

        return AuthResponse.builder().token(token).build();
    }

    @Override
    public AuthResponse authenticate(LoginRequest request) {
        // Implement authentication logic
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(); // Handle user not found
        return AuthResponse.builder().token(jwtService.generateToken(user)).build();
    }

}
