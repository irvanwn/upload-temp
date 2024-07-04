package com.ecommerce.desktop.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecommerce.desktop.DTO.Person;
import com.ecommerce.desktop.DTO.ProductList;
import com.ecommerce.desktop.Model.Cart;
import com.ecommerce.desktop.Model.Shipping;
import com.ecommerce.desktop.Model.Store;
import com.ecommerce.desktop.Model.Transaction;
import com.ecommerce.desktop.Repository.ShippingRepository;
import com.ecommerce.desktop.Repository.StoreRepository;
import com.ecommerce.desktop.Repository.TransactionRepository;
import com.ecommerce.desktop.Util.Randomizer;

@Service
public class ShippingManagement {

  @Autowired
  private ShippingRepository shippingRepository;

  @Autowired
  private StoreRepository storeRepository;

  @Autowired
  private TransactionRepository transactionRepository;

  public @ResponseBody boolean generateShippingData(Cart cart) {
    if (cart == null || cart.getProducts().isEmpty()) {
      return false;
    }

    Shipping shipping = new Shipping();
    ProductList product = cart.getProducts().get(0);
    Optional<Store> optionalStore = storeRepository.findById(product.getStoreId());

    if (!optionalStore.isPresent()) {
      return false;
    }

    Store store = optionalStore.get();

    shipping.setId("shp-" + cart.getUserId() + "-" + Randomizer.generateRandomNumber(5));
    shipping.setSender(new Person(store.getStoreName(), store.getTelpNumber(), store.getLocation()));
    shipping.setReceiver(new Person("Dummy", "08123456789", "Jakarta"));
    shipping.setShippingType("Standard");

    double shippingCost = calculateShippingCost(shipping.getShippingType(), product.getQuantity());
    if (shippingCost == -1) {
      return false;
    }

    shipping.setReceipt(generateReceipt(shipping.getShippingType()));
    shipping.setShippingCost(shippingCost);

    LocalDateTime now = LocalDateTime.now();
    shipping.setShippingDate(now.plusDays(1).toString());
    shipping.setExpectedArrival(now.plusDays(3).toString());
    shipping.setShippingStatus("Pending");
    shipping.setProducts(cart.getProducts());

    shippingRepository.save(shipping);
    return true;
  }

  // public @ResponseBody boolean shippingData(String TransactionId) {
  // Transaction transaction =
  // transactionRepository.findById(TransactionId).orElse(null);
  // if (transaction != null) {
  // Shipping shipping = new Shipping();
  // ProductList products = transaction.getProducts().get(0);
  // Optional<Store> optionalStore =
  // storeRepository.findById(products.getStoreId());
  // shipping.setId("shp-" + Randomizer.generateRandomNumber(10));

  // if (!optionalStore.isPresent()) {
  // return false;
  // }

  // Store store = optionalStore.get();
  // shipping.setSender(new Person(store.getStoreName(), store.getTelpNumber(),
  // store.getLocation()));
  // shipping.setReceiver(new Person("Dummy", "08123456789", "Jakarta"));
  // shipping.setShippingType("Standard");

  // double shippingCost = calculateShippingCost(shipping.getShippingType(),
  // products.getQuantity());
  // if (shippingCost == -1) {
  // return false;
  // }

  // shipping.setReceipt(generateReceipt(shipping.getShippingType()));
  // shipping.setShippingCost(shippingCost);

  // LocalDateTime now = LocalDateTime.now();
  // shipping.setShippingDate(now.plusDays(1).toString());
  // shipping.setExpectedArrival(now.plusDays(7).toString());
  // shipping.setShippingStatus("Pending");
  // shipping.setProducts(transaction.getProducts());
  // shippingRepository.save(shipping);
  // return true;
  // }
  // return false;
  // }

  public @ResponseBody boolean shippingData(String TransactionId) {
    Transaction transaction = transactionRepository.findById(TransactionId).orElse(null);
    if (transaction != null) {
      Shipping shipping = new Shipping();

      List<ProductList> products = transaction.getProducts();
      if (products.isEmpty()) {
        return false;
      }

      double totalShippingCost = 0;
      Optional<Store> optionalStore = Optional.empty();
      for (ProductList product : products) {
        optionalStore = storeRepository.findById(product.getStoreId());
        if (!optionalStore.isPresent()) {
          return false;
        }

        double shippingCost = calculateShippingCost("Standard", product.getQuantity());
        if (shippingCost == -1) {
          return false;
        }

        totalShippingCost += shippingCost;
      }

      Store store = optionalStore.get();
      shipping.setSender(new Person(store.getStoreName(), store.getTelpNumber(), store.getLocation()));
      shipping.setReceiver(new Person("Dummy", "08123456789", "Jakarta"));
      shipping.setShippingType("Standard");

      shipping.setId("shp-" + Randomizer.generateRandomNumber(10));
      shipping.setReceipt(generateReceipt(shipping.getShippingType()));
      shipping.setShippingCost(totalShippingCost);

      LocalDateTime now = LocalDateTime.now();
      shipping.setShippingDate(now.plusDays(1).toString());
      shipping.setExpectedArrival(now.plusDays(7).toString());
      shipping.setShippingStatus("Pending");
      shipping.setProducts(products); // Menambahkan daftar produk ke dalam objek Shipping
      shippingRepository.save(shipping);
      return true;
    }
    return false;
  }

  public @ResponseBody List<Shipping> getAllShippingData() {
    return shippingRepository.findAll();
  }

  private double calculateShippingCost(String shippingType, int quantity) {
    double baseCost;

    switch (shippingType) {
      case "Standard":
        baseCost = 10000.0;
        break;
      case "Express":
        baseCost = 15000.0;
        break;
      case "Same Day":
        baseCost = 20000.0;
        break;
      default:
        return -1;
    }

    return baseCost * quantity;
  }

  private String generateReceipt(String shippingType) {
    switch (shippingType) {
      case "Standard":
        return "ST-" + Randomizer.generateRandomNumber(10);
      case "Express":
        return "EX-" + Randomizer.generateRandomNumber(10);
      case "Same Day":
        return "SD-" + Randomizer.generateRandomNumber(10);
      default:
        throw new IllegalArgumentException("Invalid shipping type: " + shippingType);
    }
  }
}
