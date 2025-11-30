package com.rakesh.blog_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rakesh.blog_application.entity.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByPostId(Long postId);
}
