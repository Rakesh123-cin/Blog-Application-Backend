package com.rakesh.blog_application.service.impl;

import com.rakesh.blog_application.entity.Comment;
import com.rakesh.blog_application.entity.Post;
import com.rakesh.blog_application.exception.BlogApiException;
import com.rakesh.blog_application.exception.ResourceNotFoundException;
import com.rakesh.blog_application.payload.CommentDto;
import com.rakesh.blog_application.repository.CommentRepository;
import com.rakesh.blog_application.repository.PostRepository;
import com.rakesh.blog_application.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    private ModelMapper modelMapper;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper)
    {
        this.commentRepository=commentRepository;
        this.postRepository=postRepository;
        this.modelMapper=modelMapper;
    }
    @Override
    public CommentDto createComment(Long postID, CommentDto commentDto) {

        Comment comment = mapToEntity(commentDto);

        // get post entity by id
        Post post = postRepository.findById(postID).orElseThrow(()->new ResourceNotFoundException("Post","id",postID));

        comment.setPost(post);

        return mapTODto(commentRepository.save(comment));
    }

    public List<CommentDto> getCommentsByPostId(Long postId)
    {
        postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(this::mapTODto).collect(Collectors.toList());
    }

    public CommentDto getCommentByCommentId(Long postId,Long commentId)
    {
        // get post entity by id
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","id",commentId));

        if(!comment.getPost().getId().equals(post.getId()))
        {
            throw new BlogApiException("Comment does not belong to post");
        }
        return mapTODto(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","id",commentId));

        if(!comment.getPost().getId().equals(post.getId()))
        {
            throw new BlogApiException("Comment does not belong to post");
        }

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        return mapTODto(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","id",commentId));

        if(!comment.getPost().getId().equals(post.getId()))
        {
            throw new BlogApiException("Comment does not belong to post");
        }

        commentRepository.delete(comment);
    }

    private CommentDto mapTODto(Comment comment)
    {
        CommentDto commentDto = modelMapper.map(comment,CommentDto.class);
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());

        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto)
    {
        Comment comment = modelMapper.map(commentDto,Comment.class);
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());
        return comment;
    }
}
