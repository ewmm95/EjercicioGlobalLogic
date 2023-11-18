package com.autenticadorgl.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ErrorResponseDTO {
    private List<ErrorDetailDTO> errors = new ArrayList<>();

    public void addError(ErrorDetailDTO error) {
        errors.add(error);
    }
}
