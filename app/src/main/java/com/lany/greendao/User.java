package com.lany.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

@Entity
public class User {
    @Id
    private Long id;
    @Property(nameInDb = "user_name")
    private String username;
    @Property(nameInDb = "nick_name")
    private String nickname;
    private int age;

    @Generated(hash = 1453834046)
    public User(Long id, String username, String nickname, int age) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.age = age;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}