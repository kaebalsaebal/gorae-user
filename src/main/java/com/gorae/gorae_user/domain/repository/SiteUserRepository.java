package com.gorae.gorae_user.domain.repository;

import com.gorae.gorae_user.domain.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteUserRepository extends JpaRepository<SiteUser, Long> {
    SiteUser findByUserId(String userId);
    SiteUser findByUserIdAndDeleted(String userId, Boolean deleted);
}