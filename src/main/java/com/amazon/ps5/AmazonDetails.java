package com.amazon.ps5;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "account")
public class AmazonDetails {
    private String emailAddress;
    private String password;
    private String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
