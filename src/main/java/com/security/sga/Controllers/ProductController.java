package com.security.sga.Controllers;

import java.util.Optional;


import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.security.sga.DTOs.ProductDto;
import com.security.sga.Models.Category;
import com.security.sga.Models.Products;
import com.security.sga.Services.CategoryService;
import com.security.sga.Services.ProductService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/products")
@EnableWebSecurity
public class ProductController {

    @Autowired
    private final ProductService productService;

    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping()
    public String getAllProducts(@RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "0") int pagina,  Model model) {
        // para paginacion
        int pageSize = 9; // Número de productos por página

        Page<Products> productsPage;
        if (keyword.isEmpty()) {
            productsPage = productService.getProductsPage(pagina, pageSize);
        } else {
            productsPage = productService.searchProducts(keyword, pagina, pageSize);
        }

        model.addAttribute("productsPage", productsPage);
        model.addAttribute("products", productsPage.getContent());
        return "products_list";
    }
    
    @GetMapping("/create_product")
    public String addProductForm(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("product", new ProductDto());
        return "new_product";
    }

    @PostMapping("/add_product")
    public String addProduct(@Valid @ModelAttribute ProductDto productDto, BindingResult bindingResult, Model model) {

        Products product = new Products();

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());           
            model.addAttribute("org.springframework.validation.BindingResult.product", bindingResult);
            
            return "new_product";
        }

        Optional<Category> categoryOpt = categoryService.getCategoryById((long) productDto.getCategoria_id());  
                
        if (!categoryOpt.isPresent()) {
            bindingResult.rejectValue("categoria_id", "error.productDto", "Categoría no válida");
            return "new_product";
        }

        Category category = categoryOpt.get();


        product.setNombre(productDto.getNombre());
        product.setDescripcion(productDto.getDescripcion());
        product.setPrecio(productDto.getPrecio());
        product.setCosto(productDto.getCosto());
        product.setStock(productDto.getStock());
        product.setImage_url(productDto.getImage_url());
        product.setCategoria_id(category);
        productService.saveProduct(product);

        return "redirect:/products";
    }

    @GetMapping("/edit_product/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        // encontrar el producto por id
        Optional<Products> productOpt = productService.getProductById(id);

        if (productOpt.isPresent()) {
            Products product = productOpt.get();
            ProductDto productDto = new ProductDto();
            productDto.setNombre(product.getNombre());
            productDto.setDescripcion(product.getDescripcion());
            productDto.setPrecio(product.getPrecio());
            productDto.setCosto(product.getCosto());
            productDto.setStock(product.getStock());
            productDto.setImage_url(product.getImage_url());
            productDto.setCategoria_id(product.getCategoria_id().getId().intValue());

            model.addAttribute("id", product.getId());
            model.addAttribute("product", productDto);
            model.addAttribute("categories", categoryService.getAllCategories());
            return "edititar_producto";
        } else {
            return "redirect:/products";
        }
    }

    @PostMapping("/update_product/{id}")
    public String updateProduct( @Valid @PathVariable Long id, @Valid @ModelAttribute("product") ProductDto productDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "edititar_producto";
        }

        try {
            productService.updateProduct(id, productDto);
        } catch (RuntimeException e) {
            bindingResult.rejectValue("categoria_id", "error.productDto", e.getMessage());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "edititar_producto";
        }

        return "redirect:/products";
    }

}