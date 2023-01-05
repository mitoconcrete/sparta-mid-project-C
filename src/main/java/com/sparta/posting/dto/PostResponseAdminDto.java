package com.sparta.posting.dto;

import com.sparta.posting.entity.Comment;
import com.sparta.posting.entity.Post;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class PostResponseAdminDto {
    private Long id;
    private String title;
    private String contents;
    private String writer;
<<<<<<< HEAD
    private int likecnt;
=======

>>>>>>> feature/commentlike
    private List<CommentResponseAdminDto> comments = new ArrayList<>();

    public PostResponseAdminDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.writer = post.getWriter();
        this.likecnt = post.getLikecnt();
        for(Comment comment :post.getComments()){this.comments.add(new CommentResponseAdminDto(comment));}
    }
}
