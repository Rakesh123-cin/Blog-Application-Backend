package com.rakesh.blog_application.service;

import com.rakesh.blog_application.payload.LoginDto;
import com.rakesh.blog_application.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
}
