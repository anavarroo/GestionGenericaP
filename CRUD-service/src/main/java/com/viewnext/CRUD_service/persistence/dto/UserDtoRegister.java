package com.viewnext.crud_service.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoRegister implements Serializable {

    /** Nombre de usuario **/
    private String nombre;

    /** Apellidos del usuario **/
    private String apellidos;

    /** Email del usuario **/
    private String correo;

}
