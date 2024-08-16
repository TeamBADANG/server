package com.gbcontentagency.arlo.users;

import com.gbcontentagency.arlo.travels.TravelEntity;
import com.gbcontentagency.arlo.users.dto.OAuth2Response;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@Table(name = "users")
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private String nickname = "Anonymous";

    @Column(nullable = false)
    private String profileImg = "/images/defaultProfileImg.png";

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TravelEntity> travels;

    @Builder
    public UserEntity(String username, OAuth2Response oAuth2Response) {
        this.username = username;
        this.role = "ROLE_USER";
        this.nickname = oAuth2Response.getNickname();
        this.profileImg = oAuth2Response.getProfileImage();
    }

    public void update(OAuth2Response oAuth2Response) {
        this.nickname = oAuth2Response.getNickname();
        this.profileImg = oAuth2Response.getProfileImage();
    }

}
