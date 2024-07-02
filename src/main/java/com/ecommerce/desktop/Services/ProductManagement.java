package com.ecommerce.desktop.Services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecommerce.desktop.Model.Product;
import com.ecommerce.desktop.Repository.ProductRepository;
import com.ecommerce.desktop.Util.Randomizer;

@Service
public class ProductManagement {

  @Autowired
  private ProductRepository collections;

  public @ResponseBody boolean addProduct(Product product) {
    String prefix = "prod-";
    product.setId(prefix + Randomizer.generateRandomNumber(10));
    product.setCreatedAt(new Date().toString());
    product.setUpdatedAt(new Date().toString());
    collections.save(product);
    return true;
  }

  public @ResponseBody boolean updateProduct(Product product, String id) {
    Product existingProduct = collections.findById(id).orElse(null);
    if (existingProduct != null) {
      existingProduct.setName(product.getName());
      existingProduct.setDescription(product.getDescription());
      existingProduct.setCategory(product.getCategory());
      existingProduct.setPrice(product.getPrice());
      existingProduct.setStock(product.getStock());
      existingProduct.setImage(product.getImage());
      existingProduct.setUpdatedAt(new Date().toString());
      collections.save(existingProduct);
      return true;
    } else {
      return false;
    }
  }

  public @ResponseBody boolean deleteProduct(String id) {
    Product existingProduct = collections.findById(id).orElse(null);
    if (existingProduct != null) {
      collections.delete(existingProduct);
      return true;
    } else {
      return false;
    }
  }

  public @ResponseBody List<Product> getAllProducts() {
    return collections.findAll();
  }

}
