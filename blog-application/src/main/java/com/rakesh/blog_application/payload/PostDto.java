package com.rakesh.blog_application.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
@Schema(description = "Post Data Transfer Object")
public class PostDto {
    private Long id;

    // post title should not be null or empty
    // title should not exceed 20 characters
    @Schema(description = "Title of the blog post")
    @NotEmpty
    @Size(min=1, max=20, message="title should have at least 1 character and not exceed 20 characters")
    private String title;

    // post description should not be null or empty
    // Description should not exceed 50 characters
    @Schema(description = "Description of the blog post")
    @NotEmpty
    @Size(min=5, max=50, message = "Description should have at least 5 characters and not exceed 50 characters")
    private String description;

    // post content should not be null or empty
    // content should not exceed 2000 characters
    @Schema(description = "Content of the blog post")
    @NotEmpty
    @Size(min= 30, max = 2000, message = "content should have at least 30 characters and not exceed 2000 characters")
    private String content;

    private Set<CommentDto> comments;

    @Schema(description = "Category of the blog post")
    private Long categoryId;
}
