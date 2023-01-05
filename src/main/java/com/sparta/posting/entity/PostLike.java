package com.sparta.posting.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class PostLike {
    public PostLike(Long postId, Long userId) {
        this.postId = postId;
        this.userId = userId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(nullable = false)
    private Long postId;

    @JoinColumn(nullable = false)
    private Long userId;
}
