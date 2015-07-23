/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.grab.entity;

import java.util.Date;

public class User {
    /**
     * git username
     */
    private String loginName;
    /**
     * git full name
     */
    private String name;
    private String plainPassword;
    private String password;
    private String salt;
    private String roles;
    private Date registerDate;
    /**
     * git user id
     */
    private Integer gitUserId;
    /**
     * git private token
     */
    private String gitPrivateToken;


    public User() {
    }


    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // 不持久化到数据库，也不显示在Restful接口的属性.
    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Integer getGitUserId() {
        return gitUserId;
    }

    public void setGitUserId(Integer gitUserId) {
        this.gitUserId = gitUserId;
    }

    public String getGitPrivateToken() {
        return gitPrivateToken;
    }

    public void setGitPrivateToken(String gitPrivateToken) {
        this.gitPrivateToken = gitPrivateToken;
    }


}