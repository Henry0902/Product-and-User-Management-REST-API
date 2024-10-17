package com.project.table;


import javax.persistence.*;
import java.time.Instant;

@Entity(name = "refresh_token")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "userInfo_id", referencedColumnName = "id")
    private UserInfo userInfo;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expirationDate;

    public RefreshToken() {
    }

    public RefreshToken(Long id, UserInfo userInfo, String token, Instant expirationDate) {
        this.id = id;
        this.userInfo = userInfo;
        this.token = token;
        this.expirationDate = expirationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Instant expirationDate) {
        this.expirationDate = expirationDate;
    }
}
