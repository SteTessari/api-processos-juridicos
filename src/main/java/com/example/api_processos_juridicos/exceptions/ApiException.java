package com.example.api_processos_juridicos.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatusCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ApiException extends RuntimeException{

    private final HttpStatusCode code;
    private final String message;

    public ApiException(HttpStatusCode code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
    @Override
    public String toString() {
        return "ApiException{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
