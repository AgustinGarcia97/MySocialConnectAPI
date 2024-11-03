package com.example.socialconnect.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id")
    private Long postId;

    @Column()
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    //Ubication

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="post_id")
    private List<Photo> photoList;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private List<Comment> comments;

    @OneToMany()
    @JoinColumn(name = "post_id")
    private List<Like> likes;

    @OneToMany()
    @JoinColumn(name = "post_id")
    private List<Hashtag> hashtags;

    @OneToMany()
    @JoinColumn(name = "post_id")
    private List<Tag> tagged;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void setCommentToList(Comment comment) {
        comments.add(comment);
    }

    public void setLikeToList(Like like) {
        likes.add(like);
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                "}"
                ;
    }
}
