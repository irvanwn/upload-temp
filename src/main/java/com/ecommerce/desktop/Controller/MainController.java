package com.ecommerce.desktop.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecommerce.desktop.DTO.ProductDTO;
import com.ecommerce.desktop.DTO.ProductFetchDTO;
import com.ecommerce.desktop.DTO.ProductResponse;
import com.ecommerce.desktop.Model.Product;
import com.ecommerce.desktop.Services.ProductManagement;

import jakarta.websocket.server.PathParam;

@Controller
@RequestMapping("/api")
public class MainController {

  @Autowired
  private ProductManagement productManagement;

  @PostMapping("/products")
  public @ResponseBody ProductResponse addProduct(@RequestBody Product newProduct) {
    ProductDTO productDTO = new ProductDTO(newProduct.getName(), newProduct.getDescription(), newProduct.getCategory(),
        newProduct.getPrice(), newProduct.getStock(), newProduct.getImage());
    if (productManagement.addProduct(newProduct)) {
      return new ProductResponse(200, "Product added successfully", productDTO);
    } else {
      return new ProductResponse(500, "Failed to add product", null);
    }
  }

  @GetMapping("/products")
  public @ResponseBody ProductFetchDTO getAllProducts() {
    return new ProductFetchDTO(200, "Products fetched successfully", productManagement.getAllProducts());
  }

  @PutMapping("/products/{id}")
  public @ResponseBody ProductResponse updateProduct(@RequestBody Product product, @PathParam("id") String id) {
    ProductDTO productDTO = new ProductDTO(product.getName(), product.getDescription(), product.getCategory(),
        product.getPrice(), product.getStock(), product.getImage());
    if (productManagement.updateProduct(product, id)) {
      return new ProductResponse(200, "Product updated successfully", productDTO);
    } else {
      return new ProductResponse(500, "Failed to update product", null);
    }
  }

  @DeleteMapping("/products/{id}")
  public @ResponseBody ProductResponse deleteProduct(@PathParam("id") String id) {
    if (productManagement.deleteProduct(id)) {
      return new ProductResponse(200, "Product deleted successfully", null);
    } else {
      return new ProductResponse(500, "Failed to delete product", null);
    }
  }

}
