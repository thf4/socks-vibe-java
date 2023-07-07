package br.com.socksvibe.exceptions.handler;

import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public class ErrorApi implements Serializable {

    @Serial
    private static final long serialVersionUID = 4546548465468454L;

    private final String status;
    private final String path;
    private final String timestamp;
    private final String message;

    public String getMessage() {
        return message;
    }


    public String getStatus() {
        return status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getPath() {
        return path;
    }

    public ErrorApi(String path, HttpStatus status, String message) {
        this.status = String.valueOf(status);
        this.message = message;
        this.timestamp = LocalDateTime.now().toString();
        this.path = path;
    }
}
