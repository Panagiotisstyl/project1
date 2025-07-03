package com.crudapi.example.crudemo.ExceptionHandling;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
public class ErrorResponse {

    private int statusCode;
    private String message;

}
