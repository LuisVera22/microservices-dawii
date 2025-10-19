package com.auth.service.controller;

import com.auth.service.model.AuthResponse;
import com.auth.service.model.LoginRequest;
import com.auth.service.model.RecoveryPasswordRequest;
import com.auth.service.model.RegisterRequest;
import com.auth.service.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request)
    {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request)
    {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping(value = "recovery-password")
    public ResponseEntity<?> recoveryPassword(@RequestBody RecoveryPasswordRequest request)
    {
        authService.recoveryPassword(request);
        return ResponseEntity.ok().build();
    }
}
