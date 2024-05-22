package com.viewnext.register_service.persistence.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Usuarios")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable, UserDetails {

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

    /** Telefono del usuario **/
    @Column(name = "telefono")
    private int telefono;

    /** Contraseña del usuario **/
    @Column(name = "contrasena", nullable = false)
    @NotBlank(message = "La contraseña no puede estar vacia")
    private String contrasena;

    /** Estado para aprobar el registro de usuarios **/
    @Column(name = "estado")
    private boolean estado;

    /** Rol del usuario **/
    @Enumerated(EnumType.STRING)
    private Role role;

    /** Facotor de doble autenticacion **/
    @Column(name = "mfaEnabled")
    private boolean mfaEnabled;

    /** Clave secretea del factor de doble autenticacion **/
    @Column(name = "FAKey")
    private String secret;

    public User(String nombre, String apellidos, String correo, String contrasena,
                boolean mfaEnabled) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.contrasena = contrasena;
        this.estado = false;
        this.role = role;
        this.mfaEnabled = mfaEnabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return contrasena;
    }

    @Override
    public String getUsername() {
        return correo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
