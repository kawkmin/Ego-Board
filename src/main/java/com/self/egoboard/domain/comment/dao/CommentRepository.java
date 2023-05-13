package com.self.egoboard.domain.comment.dao;

import com.self.egoboard.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {


}
