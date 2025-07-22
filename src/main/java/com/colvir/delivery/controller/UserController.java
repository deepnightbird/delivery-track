package com.colvir.delivery.controller;

import com.colvir.delivery.dto.AuthRequest;
import com.colvir.delivery.dto.AuthResponse;
import com.colvir.delivery.dto.RegistrationRequestDto;
import com.colvir.delivery.model.User;
import com.colvir.delivery.repository.UserRepository;
import com.colvir.delivery.security.JwtTokenUtil;
import com.colvir.delivery.service.impl.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Validated
@Slf4j
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService customUserDetailsService;

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest request) {
        if (userRepository.findByUsername(request.username()).isPresent()) {
            return ResponseEntity.badRequest().body(new AuthResponse("Username already exists"));
        }

        User user = new User();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));

        user.setRoles(Set.of("USER"));

        userRepository.save(user);

        return ResponseEntity.ok(new AuthResponse("User registered successfully"));
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.username());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse("Login successful, token = " + token));
    }

}
