package tz.co.ubunifusolutions.screens.models;

public class LoginResponse {

    private  boolean error;
    private String message;
    private String user;

    public LoginResponse(boolean error, String message, String user) {
        this.error = error;
        this.message = message;
        this.user = user;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getUser() {
        return user;
    }
}
