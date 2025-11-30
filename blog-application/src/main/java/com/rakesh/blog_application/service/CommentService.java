package com.rakesh.blog_application.service;

import com.rakesh.blog_application.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(Long postID, CommentDto commentDto);
    List<CommentDto> getCommentsByPostId(Long postId);
    CommentDto getCommentByCommentId(Long postId,Long commentId);
    CommentDto updateComment(Long postId,Long commentId,CommentDto commentDto);
    void deleteComment(Long postId,Long commentId);
}
