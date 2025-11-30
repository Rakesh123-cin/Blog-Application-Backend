package com.rakesh.blog_application.controller;

import com.rakesh.blog_application.payload.CommentDto;
import com.rakesh.blog_application.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Tag(name="CRUD REST APIs for Comment resource")
public class CommentController {
    private CommentService commentService;
    @Autowired
    public CommentController(CommentService commentService)
    {
        this.commentService=commentService;
    }
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Create a comment for a post",
            description = "Create Comment REST API is used to create a new comment for a post"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Comment created successfully"
    )
    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") Long postId,
                                                    @Valid @RequestBody CommentDto commentDto)
    {
        return new ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);
    }
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Get all comments for a post",
            description = "Get Comments REST API is used to get all comments by post id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Comments retrieved successfully"
    )
    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable("postId") Long postId)
    {
        return new ResponseEntity<>(commentService.getCommentsByPostId(postId),HttpStatus.OK);
    }
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Get a comment by id for a post",
            description = "Get Comment REST API is used to get a comment by comment id and post id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Comment retrieved successfully"
    )
    @GetMapping("/{postId}/comments/{commentId}")

    public ResponseEntity<CommentDto> getCommentByCommentId(@PathVariable Long postId, @PathVariable Long commentId)
    {
        return new ResponseEntity<>(commentService.getCommentByCommentId(postId, commentId),HttpStatus.OK);
    }
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Update a comment for a post",
            description = "Update Comment REST API is used to update a comment by comment id and post id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Comment updated successfully"
    )
    @PutMapping("/{postId}/comments/{commentId}")

    public ResponseEntity<CommentDto> updateComment(@PathVariable Long postId, @PathVariable Long commentId, @Valid @RequestBody CommentDto commentDto)
    {
        return new ResponseEntity<>(commentService.updateComment(postId, commentId, commentDto),HttpStatus.OK);
    }
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Delete a comment for a post",
            description = "Delete Comment REST API is used to delete a comment by comment id and post id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Comment deleted successfully"
    )
    @DeleteMapping("/{postId}/comments/{commentId}")

    public ResponseEntity<String> deleteComment(@PathVariable Long postId, @PathVariable Long commentId)
    {
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("Comment deleted.....",HttpStatus.OK);
    }
}
