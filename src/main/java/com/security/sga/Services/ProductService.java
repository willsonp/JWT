package com.security.sga.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.sga.DTOs.ProductDto;
import com.security.sga.Models.Category;
import com.security.sga.Models.Products;
import com.security.sga.Repository.ProductRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;

    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }
   
    public Products saveProduct(Products product) {
        productRepository.save(product);
        return product;
    }

    public List<Products> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Products> getProductById(Long id) {
        return productRepository.findById(id);
    }
    
    public Page<Products> getProductsPage(int number,int size) {
        Pageable pageable = PageRequest.of(number, size, Sort.by(Sort.Direction.ASC, "nombre"));
        return productRepository.findAll(pageable);
    }

    public Page<Products> searchProducts(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "nombre"));
        return productRepository.findByNombreContaining(keyword, pageable);
    }

    public Products updateProduct(Long id, ProductDto updatedProduct) {

        Optional<Category> categoryOpt = categoryService.getCategoryById((long) updatedProduct.getCategoria_id());
               
           if (!categoryOpt.isPresent()) {
                throw new RuntimeException("Categoría no encontrada con id: " + updatedProduct.getCategoria_id());
            }

        return productRepository.findById(id).map(product -> {
            product.setNombre(updatedProduct.getNombre());
            product.setDescripcion(updatedProduct.getDescripcion());
            product.setPrecio(updatedProduct.getPrecio());
            product.setStock(updatedProduct.getStock());
            product.setCategoria_id(categoryOpt.get());
            return productRepository.save(product);
        }).orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
    }

   
}
