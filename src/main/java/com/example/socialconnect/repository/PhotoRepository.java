package com.example.socialconnect.repository;

import com.example.socialconnect.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    Photo findByphotoUrl(String photoUrl);
}
