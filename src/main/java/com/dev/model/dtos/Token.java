package com.dev.model.dtos;

public class Token {

    private String token;
    private String login;

    public Token() {
     super();
    }

    public Token( String login, String token) {
        this.token = token;
        this.login = login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
