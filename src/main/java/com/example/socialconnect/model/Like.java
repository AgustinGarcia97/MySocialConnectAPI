package com.example.socialconnect.model;

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
    private Comment comment;

    @ManyToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST})
    private Post post;

    @ManyToOne
    private User user;


}
