package com.gorae.gorae_user.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Table(name = "site_user")
public class SiteUser {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Column(name = "user_id", unique = true, nullable = false)
    @Getter @Setter
    private String userId;

    @Column(name = "user_name", nullable = false)
    @Getter @Setter
    private String userName;

    @Column(name = "password", nullable = false)
    @Getter @Setter
    private String password;

    @Column(name = "phone_number", nullable = false)
    @Setter @Getter
    private String phoneNumber;

    @Column(name = "email", nullable = false)
    @Setter @Getter
    private String email;

    @Column(name = "userProfile", nullable = true)
    @Setter @Getter
    private String userProfile;

    @Column(name = "deleted", nullable = false)
    @Getter @Setter
    private Boolean deleted = false;
}