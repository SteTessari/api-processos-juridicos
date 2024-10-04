package com.example.api_processos_juridicos.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatusCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ApiException extends RuntimeException{

    private HttpStatusCode code;
    private String message;
    private String description;

    public ApiException(HttpStatusCode code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public ApiException(HttpStatusCode code, String message, String description) {
        super(description);
        this.code = code;
        this.message = message;
        this.description = description;
    }

    @Override
    public String toString() {
        return "ApiException{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
