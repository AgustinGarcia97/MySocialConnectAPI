package com.example.socialconnect.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="like_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Like {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")

    private Long likeId;

    @ManyToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name="comment_id")
    private Comment comment;

    @ManyToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name="post_id")
    private Post post;



    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Override
    public String toString() {
        return "Like{" +
                "likeId=" + likeId +

                '}';
    }
}
