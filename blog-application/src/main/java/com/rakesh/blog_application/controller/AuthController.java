package com.rakesh.blog_application.controller;

import com.rakesh.blog_application.payload.JwtAuthResponse;
import com.rakesh.blog_application.payload.LoginDto;
import com.rakesh.blog_application.payload.RegisterDto;
import com.rakesh.blog_application.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name="CRUD REST APIs for Signin and Signup")
public class AuthController {
    private AuthService authService;
    @Autowired
    public AuthController(AuthService authService)
    {
        this.authService=authService;
    }

    // Build login REST Api
    @Operation(
            summary = "Login into the application",
            description = "Login REST API is used to authenticate a user and generate JWT token"
    )
    @ApiResponse(
            responseCode = "200",
            description = "User logged in successfully"
    )
    @PostMapping(value = {"/signin","/login"})
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto)
    {
        String jwtToken = authService.login(loginDto);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(jwtToken);

        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }

    // Build Register REST Api
    @Operation(
            summary = "Register a new user",
            description = "Register REST API is used to register a new user into the application"
    )
    @ApiResponse(
            responseCode = "201",
            description = "User Registered successfully"
    )
    @PostMapping(value={"/register","/signup"})
    public ResponseEntity<String> register( @Valid @RequestBody RegisterDto registerDto)
    {
        return new ResponseEntity<>(authService.register(registerDto),HttpStatus.CREATED);
    }
}
