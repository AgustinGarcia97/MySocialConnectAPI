package com.example.socialconnect.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id")
    private Long commentId;

    private LocalDateTime createdAt;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @ManyToOne()
    @JoinColumn(name="post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.DETACH)
    private List<Like> likes;

    public void addLikeToList(Like like) {
        this.likes.add(like);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", createdAt=" + createdAt +
                ", comment='" + comment + '\'' +
                '}';
    }
}
