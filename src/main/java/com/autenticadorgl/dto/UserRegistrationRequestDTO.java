package com.autenticadorgl.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationRequestDTO {

    private String name;

    @NotEmpty(message = "El email no puede ser vacio")
    @NotBlank(message = "El email no puede estar en blanco")
    @Pattern(
            regexp="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message="El formato de email es incorrecto."
    )
    private String email;

    @NotEmpty( message = "La contrasena no puede ser vacia")
    @NotBlank(message = "La contrasena no puede estar en blanco")
    @Size(min =8 ,max =12 ,message = "El largo de la contraseña debe ser entre 8 y 12 caracteres")
    @Pattern(
            regexp = "^(?=(?:[^A-Z]*[A-Z]){1}[^A-Z]*$)(?=(?:\\D*\\d){2}\\D*$)[A-Za-z\\d]{8,12}$",
            message = "El formato de la contraseña no es valido. Debe tener una mayuscula, al menos 2 numeros y un largo entre 8 y 12."
    )
    private String password;

    private List<PhoneDTO> phones;

    public UserRegistrationRequestDTO(@NotEmpty(message = "El email no puede ser vacio") @NotBlank(message = "El email no puede estar en blanco") @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "El formato de email es incorrecto."
    ) String email, @NotEmpty(message = "La contrasena no puede ser vacia") @NotBlank(message = "La contrasena no puede estar en blanco") @Size(min = 8, max = 12, message = "El largo de la contraseña debe ser entre 8 y 12 caracteres") @Pattern(
            regexp = "^(?=(?:[^A-Z]*[A-Z]){1}[^A-Z]*$)(?=(?:\\D*\\d){2}\\D*$)[A-Za-z\\d]{8,12}$",
            message = "El formato de la contraseña no es valido. Debe tener una mayuscula, al menos 2 numeros y un largo entre 8 y 12."
    ) String password) {
        this.email = email;
        this.password = password;
    }
}
