package com.staples.tests.data;

public class StaplesUserCredentials {
    private String email;
    private String Password;

    public StaplesUserCredentials(String email, String password) {
        this.email = email;
        Password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public String toString() {
        return "StaplesCurrentUserCredentials{" +
                "email='" + email + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }
}
