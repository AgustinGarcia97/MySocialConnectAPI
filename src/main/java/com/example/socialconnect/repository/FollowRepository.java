package com.example.socialconnect.repository;

import com.example.socialconnect.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository <Follow, Long> {
}
