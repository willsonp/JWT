package com.security.sga.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; // Correct importimport 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.security.sga.Models.Products;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {

    Optional<Products> findById(Long id);

    @Query("SELECT p FROM Products p WHERE p.nombre LIKE %?1%")
    Page<Products> findByNombreContaining(String nombre, Pageable pageable);

    Page<Products> findAll(Pageable pageable);

    
    
}
