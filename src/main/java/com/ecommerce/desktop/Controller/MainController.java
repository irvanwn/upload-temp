package com.ecommerce.desktop.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecommerce.desktop.DTO.ProductDTO;
import com.ecommerce.desktop.DTO.ProductFetchDTO;
import com.ecommerce.desktop.DTO.ProductResponse;
import com.ecommerce.desktop.DTO.ResponseTemplate;
import com.ecommerce.desktop.DTO.StoreDTO;
import com.ecommerce.desktop.Model.Cart;
import com.ecommerce.desktop.Model.Product;
import com.ecommerce.desktop.Model.Shipping;
import com.ecommerce.desktop.Model.Store;
import com.ecommerce.desktop.Model.Transaction;
import com.ecommerce.desktop.Services.CartManagement;
import com.ecommerce.desktop.Services.ProductManagement;
import com.ecommerce.desktop.Services.ShippingManagement;
import com.ecommerce.desktop.Services.StoreManagement;
import com.ecommerce.desktop.Services.TransactionManagement;

@Controller
@RequestMapping("/api")
public class MainController {

  @Autowired
  private ProductManagement productManagement;

  @Autowired
  private StoreManagement storeManagement;

  @Autowired
  private CartManagement cartManagement;

  @Autowired
  private ShippingManagement shippingManagement;

  @Autowired
  private TransactionManagement transactionManagement;

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

  @PostMapping("/products/batch")
  public @ResponseBody ProductResponse addProductBatch(@RequestBody Product[] products) {
    for (Product product : products) {
      productManagement.addProduct(product);
    }
    return new ProductResponse(200, "Products added successfully", null);
  }

  @GetMapping("/products")
  public @ResponseBody ProductFetchDTO getAllProducts() {
    return new ProductFetchDTO(200, "Products fetched successfully", productManagement.getAllProducts());
  }

  @GetMapping("/products/{id}")
  public @ResponseBody ProductResponse getProduct(@PathVariable("id") String id) {
    Product product = productManagement.getProduct(id);
    if (product != null) {
      return new ProductResponse(200, "Product fetched successfully",
          new ProductDTO(product.getName(), product.getDescription(), product.getCategory(), product.getPrice(),
              product.getStock(), product.getImage()));
    } else {
      return new ProductResponse(500, "Failed to fetch product", null);
    }
  }

  @PutMapping("/products/{id}")
  public @ResponseBody ProductResponse updateProduct(@RequestBody Product product, @PathVariable("id") String id) {
    ProductDTO productDTO = new ProductDTO(product.getName(), product.getDescription(), product.getCategory(),
        product.getPrice(), product.getStock(), product.getImage());
    if (productManagement.updateProduct(product, id)) {
      return new ProductResponse(200, "Product updated successfully", productDTO);
    } else {
      return new ProductResponse(500, "Failed to update product", null);
    }
  }

  @DeleteMapping("/products/{id}")
  public @ResponseBody ProductResponse deleteProduct(@PathVariable("id") String id) {
    if (productManagement.deleteProduct(id)) {
      return new ProductResponse(200, "Product deleted successfully", null);
    } else {
      return new ProductResponse(500, "Failed to delete product", null);
    }
  }

  /// Store Management

  @PostMapping("/stores")
  public @ResponseBody StoreDTO addStore(@RequestBody Store newStore) {
    if (storeManagement.addStore(newStore)) {
      return new StoreDTO(200, "Store added successfully", newStore);
    } else {
      return new StoreDTO(500, "Failed to add store", null);
    }
  }

  @GetMapping("/stores/{id}")
  public @ResponseBody StoreDTO getStore(@PathVariable("id") String id) {
    Store store = storeManagement.getStore(id);
    if (store != null) {
      return new StoreDTO(200, "Store fetched successfully", store);
    } else {
      return new StoreDTO(500, "Failed to fetch store", null);
    }
  }

  @PutMapping("/stores/{id}")
  public @ResponseBody StoreDTO updateStore(@RequestBody Store updatedStore, @PathVariable("id") String id) {
    if (storeManagement.updateStore(updatedStore, id)) {
      return new StoreDTO(200, "Store updated successfully", updatedStore);
    } else {
      return new StoreDTO(500, "Failed to update store", null);
    }
  }

  @DeleteMapping("/stores/{id}")
  public @ResponseBody StoreDTO deleteStore(@PathVariable("id") String id) {
    if (storeManagement.deleteStore(id)) {
      return new StoreDTO(200, "Store deleted successfully", null);
    } else {
      return new StoreDTO(500, "Failed to delete store", null);
    }
  }

  @DeleteMapping("/stores/{id}/products/{productId}")
  public @ResponseBody StoreDTO deleteProductById(@PathVariable("id") String id,
      @PathVariable("productId") String productId) {
    if (storeManagement.deleteProductById(id, productId)) {
      return new StoreDTO(200, "Product deleted successfully", null);
    } else {
      return new StoreDTO(500, "Failed to delete product", null);
    }
  }

  /// Cart Management

  @PostMapping("/cart/{UserId}")
  public @ResponseBody ResponseTemplate makeCart(@PathVariable("UserId") String userId) {
    if (cartManagement.makeCart(userId)) {
      return new ResponseTemplate(200, "Cart created successfully", null);
    } else {
      return new ResponseTemplate(500, "Failed to create cart", null);
    }
  }

  @GetMapping("/cart/{UserId}")
  public @ResponseBody ResponseTemplate getCart(@PathVariable("UserId") String userId) {
    Cart userCart = cartManagement.getCart(userId);
    if (userCart != null) {
      return new ResponseTemplate(200, "Cart fetched successfully", userCart);
    } else {
      return new ResponseTemplate(500, "Failed to fetch cart", null);
    }
  }

  @PostMapping("/cart/{UserId}/products/{ProductId}/{Quantity}")
  public @ResponseBody ResponseTemplate addProductToCart(@PathVariable("UserId") String userId,
      @PathVariable("ProductId") String productId, @PathVariable("Quantity") int quantity) {
    if (cartManagement.addProductToCart(userId, productId, quantity)) {
      return new ResponseTemplate(200, "Product added to cart successfully", null);
    } else {
      return new ResponseTemplate(500, "Failed to add product to cart", null);
    }
  }

  @DeleteMapping("/cart/{UserId}/products/{ProductId}")
  public @ResponseBody ResponseTemplate removeProductFromCart(@PathVariable("UserId") String userId,
      @PathVariable("ProductId") String productId) {
    if (cartManagement.removeProductFromCart(userId, productId)) {
      return new ResponseTemplate(200, "Product removed from cart successfully", null);
    } else {
      return new ResponseTemplate(500, "Failed to remove product from cart", null);
    }
  }

  @PutMapping("/cart/{UserId}/products/{ProductId}/{Quantity}")
  public @ResponseBody ResponseTemplate updateProductQuantity(@PathVariable("UserId") String userId,
      @PathVariable("ProductId") String productId, @PathVariable("Quantity") int quantity) {
    if (cartManagement.updateProductQuantity(userId, productId, quantity)) {
      return new ResponseTemplate(200, "Product quantity updated successfully", null);
    } else {
      return new ResponseTemplate(500, "Failed to update product quantity", null);
    }
  }

  @PostMapping("/cart/{userId}/checkout")
  public @ResponseBody ResponseTemplate checkoutCart(@PathVariable("userId") String userId) {
    if (cartManagement.Checkout(userId)) {
      return new ResponseTemplate(200, "Cart checked out successfully", null);
    } else {
      return new ResponseTemplate(500, "Failed to checkout cart", null);
    }
  }

  @PostMapping("/cart/{userId}/checkout2")
  public @ResponseBody ResponseTemplate checkoutCart2(@PathVariable("userId") String userId) {
    if (cartManagement.Record(userId)) {
      return new ResponseTemplate(200, "Cart checked out successfully", null);
    } else {
      return new ResponseTemplate(500, "Failed to checkout cart", null);
    }
  }

  @PostMapping("/transaction/{transactionId}/paid/{paymentMethod}")
  public @ResponseBody ResponseTemplate transactionPaid(@PathVariable("transactionId") String transactionId,
      @PathVariable("paymentMethod") String paymentMethod) {
    if (transactionManagement.paytransaction(transactionId, paymentMethod)) {
      return new ResponseTemplate(200, "Transaction paid successfully", null);
    } else {
      return new ResponseTemplate(500, "Failed to pay transaction", null);
    }
  }

  /// Shipping Management
  @GetMapping("/shipping")
  public @ResponseBody ResponseTemplate getAllShippingData() {
    List<Shipping> shippingData = shippingManagement.getAllShippingData();
    if (shippingData != null) {
      return new ResponseTemplate(200, "Shipping data fetched successfully", shippingData);
    } else {
      return new ResponseTemplate(500, "Failed to fetch shipping data", null);
    }
  }

  @PostMapping("/shipping/{userId}")
  public @ResponseBody ResponseTemplate generateShippingData(@PathVariable("userId") String userId) {
    Cart userCart = cartManagement.getCart(userId);
    if (userCart != null) {
      if (shippingManagement.generateShippingData(userCart)) {
        return new ResponseTemplate(200, "Shipping data generated successfully", null);
      } else {
        return new ResponseTemplate(500, "Failed to generate shipping data", userCart);
      }
    } else {
      return new ResponseTemplate(500, "Failed to fetch cart", null);
    }
  }

}
