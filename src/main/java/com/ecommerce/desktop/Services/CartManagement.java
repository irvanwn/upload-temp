package com.ecommerce.desktop.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecommerce.desktop.DTO.ProductList;
import com.ecommerce.desktop.Model.Cart;
import com.ecommerce.desktop.Model.Product;
import com.ecommerce.desktop.Repository.CartRepository;
import com.ecommerce.desktop.Repository.ProductRepository;

@Service
public class CartManagement {

  @Autowired
  private CartRepository cartRepository;

  @Autowired
  private ProductRepository productRepository;

  public @ResponseBody boolean makeCart(String userId) {
    Cart isExist = cartRepository.findByUserId(userId);
    if (isExist == null) {
      Cart newCart = new Cart();
      newCart.setId("cart-" + userId);
      newCart.setUserId(userId);
      cartRepository.save(newCart);
      return true;
    }
    return false;
  }

  public @ResponseBody boolean addProductToCart(String userId, String productId, int quantity) {
    Cart existingCart = cartRepository.findByUserId(userId);
    if (existingCart != null) {
      Product product = productRepository.findById(productId).orElse(null);
      if (product != null) {
        ProductList addList = new ProductList();
        addList.setId(productId);
        addList.setQuantity(quantity);
        existingCart.getProducts().add(addList);
        // calculate total price
        double totalPrice = 0;
        for (ProductList productItem : existingCart.getProducts()) {
          Product productData = productRepository.findById(productItem.getId()).orElse(null);
          if (productData != null) {
            totalPrice += productData.getPrice() * productItem.getQuantity();
          }
        }
        existingCart.setTotalPrice(totalPrice);
        cartRepository.save(existingCart);
        return true;
      }
    }
    return false;
  }

  public @ResponseBody boolean removeProductFromCart(String userId, String productId) {
    Cart existingCart = cartRepository.findByUserId(userId);
    if (existingCart != null) {
      existingCart.getProducts().removeIf(product -> product.getId().equals(productId));
      cartRepository.save(existingCart);
      return true;
    }
    return false;
  }

  public @ResponseBody boolean updateProductQuantity(String userId, String productId, int quantity) {
    Cart existingCart = cartRepository.findByUserId(userId);
    if (existingCart != null) {
      existingCart.getProducts().forEach(product -> {
        if (product.getId().equals(productId)) {
          product.setQuantity(quantity);
          // calculate total price
          double totalPrice = 0;
          for (ProductList productItem : existingCart.getProducts()) {
            Product productData = productRepository.findById(productItem.getId()).orElse(null);
            if (productData != null) {
              totalPrice += productData.getPrice() * productItem.getQuantity();
            }
          }
          existingCart.setTotalPrice(totalPrice);
        }
      });
      cartRepository.save(existingCart);
      return true;
    }
    return false;
  }

  public @ResponseBody boolean clearCart(String userId) {
    Cart existingCart = cartRepository.findByUserId(userId);
    if (existingCart != null) {
      existingCart.getProducts().clear();
      cartRepository.save(existingCart);
      return true;
    }
    return false;
  }

  public @ResponseBody Cart getCart(String userId) {
    Cart existingCart = cartRepository.findByUserId(userId);
    return existingCart;
  }

}
