package com.rakesh.blog_application.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CommentDto {

    private Long id;

    @NotEmpty(message="name can't be empty")
    private String name;

    @NotEmpty(message="email id is required")
    @Email
    private String email;

    @Size(min=1, max=20, message="body should have at least 1 character and not exceed 20 characters")
    private String body;
}
