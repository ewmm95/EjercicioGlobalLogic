package com.autenticadorgl.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ErrorDetailDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    private int code;
    private String detail;

    public ErrorDetailDTO(LocalDateTime timestamp, int code, String detail) {
        this.timestamp = timestamp;
        this.code = code;
        this.detail = detail;
    }

}
