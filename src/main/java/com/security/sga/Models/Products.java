package com.security.sga.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "products")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    private Long id;

    @NotNull
    @Size(min = 3, max = 100)
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotNull
    @Size(min = 3, max = 255)
    @Column(nullable = false, length = 255)
    private String descripcion;

    @DecimalMin(value = "0.01", message = "El precio debe ser mayor o igual a 0.01")
    private Double precio;

    @DecimalMin(value = "0.01", message = "El costo debe ser mayor o igual a 0.01")
    private Double costo;

    @DecimalMin(value = "0.01", message = "El stock debe ser mayor o igual a 0.01")
    private Float stock;

    @Column(nullable = true)
    private String image_url;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false, referencedColumnName = "id")
    private Category categoria_id;

}


