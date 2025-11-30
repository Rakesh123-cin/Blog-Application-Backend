package com.rakesh.blog_application.controller;

import com.rakesh.blog_application.payload.PostDto;
import com.rakesh.blog_application.payload.PostResponse;
import com.rakesh.blog_application.service.PostService;
import com.rakesh.blog_application.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Tag(name="CRUD REST APIs for Post Resource")
public class PostController {

    private PostService postService;
    @Autowired
    public PostController(PostService postService)
    {
        this.postService=postService;
    }
    // create blog post rest api
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Create a Post",
            description = "Create Post REST API is used to create a new post by admin"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Post created successfully"
    )
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto)
    {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    // get all posts rest api
    @Operation(
            summary = "Get all Posts",
            description = "Get all posts from the database with pagination and sorting"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Posts retrieved successfully"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
            @RequestParam(value="pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value="sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value="sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    )
    {
        return new ResponseEntity<>(postService.getAllPosts(pageNo,pageSize,sortBy,sortDir),HttpStatus.OK);
    }

    // get a post by id rest api
    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Get a Post",
            description = "Get a post by Id from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Post retrieved successfully"
    )
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id)
    {
        return new ResponseEntity<>(postService.getPostById(id),HttpStatus.OK);
    }

    // update a post by id rest api
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Update a Post",
            description = "Update an existing post by Id by Admin"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Post updated successfully"
    )
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable Long id)
    {
        return new ResponseEntity<>(postService.updatePost(postDto,id),HttpStatus.OK);
    }

    // delete a post by id rest api
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Delete a Post",
            description = "Delete an existing post by Id by Admin"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Posts deleted successfully"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable Long id)
    {
        postService.deletePostById(id);
        return new ResponseEntity<>("Post deleted successfully",HttpStatus.OK);
    }

    // get all posts by category Id
    @GetMapping("/categories/{categoryId}")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Get all Posts By category",
            description = "Get all posts by category Id from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Posts retrieved successfully"
    )
    public ResponseEntity<List<PostDto>> getPostsByCategoryId(@PathVariable Long categoryId)
    {
        return new ResponseEntity<>(postService.getPostsByCategory(categoryId),HttpStatus.OK);
    }
}
