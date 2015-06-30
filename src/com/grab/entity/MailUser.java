package com.grab.entity;

/**
 * Created by baiqunwei on 15/6/24.
 */
public class MailUser {
    private String imapHost;
    private int imapPort;
    private String name;
    private String password;

    public MailUser(String imapHost, int imapPort, String name, String password) {
        this.imapHost = imapHost;
        this.imapPort = imapPort;
        this.name = name;
        this.password = password;
    }

    public String getImapHost() {
        return imapHost;
    }

    public void setImapHost(String imapHost) {
        this.imapHost = imapHost;
    }

    public int getImapPort() {
        return imapPort;
    }

    public void setImapPort(int imapPort) {
        this.imapPort = imapPort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

