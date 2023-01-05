package com.sparta.posting.service;

import com.sparta.posting.dto.CommentRequestDto;
import com.sparta.posting.dto.CommentResponseDto;
import com.sparta.posting.dto.HttpResponseDto;
import com.sparta.posting.entity.*;
import com.sparta.posting.repository.CommentRepository;


import com.sparta.posting.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    @Transactional
    public CommentResponseDto addComment(Long postId, CommentRequestDto commentRequestDto, User user) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("게시물이 존재하지 않습니다.")
        );
        Comment comment = new Comment(commentRequestDto, user, postId);
        commentRepository.save(comment);
        post.addcomment(commentRepository.findAllByPostIdOrderByCreatedAtDesc(postId));
        return new CommentResponseDto(comment);
    }

    @Transactional
    public Object update(Long commentId, CommentRequestDto commentRequestDto, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NullPointerException("댓글이 존재하지 않습니다.")
        );
        Post post = postRepository.findById(comment.getPostId()).orElseThrow(
                () -> new NullPointerException("게시물이 존재하지 않습니다.")
        );
        if (user.getId().equals(comment.getUserId())) {
            comment.update(commentRequestDto);
            post.addcomment(commentRepository.findAllByPostIdOrderByCreatedAtDesc(comment.getPostId()));
            return new CommentResponseDto(comment);
        } else {
            return new HttpResponseDto("작성자만 수정할 수 있습니다.", 400);
        }

    }
    @Transactional
    public HttpResponseDto delete(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NullPointerException("댓글이 존재하지 않습니다.")
        );
        Post post = postRepository.findById(comment.getPostId()).orElseThrow(
                () -> new NullPointerException("게시물이 존재하지 않습니다.")
        );
        if (user.getId().equals(comment.getUserId())) {
            post.removecomment(comment);
            commentRepository.delete(comment);
            return new HttpResponseDto("댓글삭제가 완료되었습니다.", HttpStatus.UNAUTHORIZED.value());
        } else {
            return new HttpResponseDto("작성자만 삭제할 수 있습니다.",400);
        }
    }
}
