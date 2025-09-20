package com.security.sga.DTOs;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {

    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    @NotNull(message = "El nombre no puede ser nulo")
    @NotBlank(message = "El nombre no puede estar vacío")    
    private String nombre;

    @Size(min = 3, max = 255, message = "La descripción debe tener entre 3 y 255 caracteres")
    @NotNull(message = "La descripción no puede ser nula")
    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcion;

    @NotNull(message = "El precio no puede ser nulo")
    @DecimalMin(value = "0.1", message = "El precio debe ser mayor o igual a 0.1")
    private Double precio;

    @NotNull(message = "El costo no puede ser nulo")
    @DecimalMin(value = "0.1", message = "El costo debe ser mayor o igual a 0.1")
    private Double costo;

    @NotNull(message = "El stock no puede ser nulo")
    @DecimalMin(value = "0.1", message = "El stock debe ser mayor o igual a 0.1")
    private Float stock;

    private String image_url;

    @NotNull(message = "La categoría no puede ser nula")    
    private int categoria_id;
}
