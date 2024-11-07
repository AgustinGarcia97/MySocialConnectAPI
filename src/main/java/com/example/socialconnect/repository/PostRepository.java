package com.example.socialconnect.repository;

import com.example.socialconnect.dto.PostDTO;
import com.example.socialconnect.model.Post;
import com.example.socialconnect.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAll(Pageable pageable);
}
