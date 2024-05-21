package com.viewnext.crud_service.persistence.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "seee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pepe {

    /** Identificador unico del usuario **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    /** Nombre de usuario **/
    @Column(name = "nombre", nullable = false)
    @NotBlank(message = "El nombre no puede estar vacio")
    private String nombre;

    /** Apellidos del usuario **/
    @Column(name = "apellidos", nullable = false)
    @NotBlank(message = "Los apellidos no puede estar vacio")
    private String apellidos;

    /** Edad del usuario **/
    @Column(name = "edad")
    private int edad;

    /** Email del usuario **/
    @Column(name = "correo", unique = true, nullable = false)
    @Email(message = "El correo no puede estar vacio")
    private String correo;

    /** Direccion del usuario **/
    @Column(name = "direccion")
    private String direccion;

    /** Teléfono del usuario **/
    @Column(name = "telefono")
    @Pattern(regexp = "^\\+?[0-9]*$", message = "El teléfono debe contener solo números y puede tener un prefijo opcional con el símbolo '+'")
    private String telefono;
    /** Estado para aprobar el registro de usuarios **/
    @Column(name = "estado", nullable = false)
    boolean estado;

    /** Contraseña del usuario **/
    @Column(name = "contrasena", nullable = false)
    @NotBlank(message = "La contraseña no puede estar vacia")
    private String contrasena;


    public void setTelefono(String telefono) {
        /** Verificar si el teléfono ya tiene el prefijo '+' **/
        if (!telefono.startsWith("+")) {

            this.telefono = "+" + telefono;
        } else {
            this.telefono = telefono;
        }
    }

}
