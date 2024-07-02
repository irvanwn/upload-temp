package com.ecommerce.desktop.Services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecommerce.desktop.Model.Store;
import com.ecommerce.desktop.Repository.ProductRepository;
import com.ecommerce.desktop.Repository.StoreRepository;
import com.ecommerce.desktop.Util.Randomizer;

@Service
public class StoreManagement {

  @Autowired
  private StoreRepository storeCollections;

  @Autowired
  private ProductRepository productCollections;

  public @ResponseBody boolean addStore(Store newstore) {
    String prefix = "store-";
    newstore.setId(prefix + Randomizer.generateRandomNumber(10));
    newstore.setRating(0);
    newstore.setCreatedAt(new Date().toString());
    newstore.setUpdatedAt(new Date().toString());
    storeCollections.save(newstore);
    return true;
  }

  public @ResponseBody boolean updateStore(Store updatedStore, String id) {
    Store existingStore = storeCollections.findById(id).orElse(null);
    if (existingStore != null) {
      existingStore.setStoreName(updatedStore.getStoreName());
      existingStore.setLocation(updatedStore.getLocation());
      existingStore.setRating(updatedStore.getRating());
      existingStore.setUpdatedAt(new Date().toString());
      storeCollections.save(existingStore);
      return true;
    } else {
      return false;
    }
  }

  public @ResponseBody Store getStore(String id) {
    Store existingStore = storeCollections.findById(id).orElse(null);
    existingStore.setProductsData(productCollections.findByStoreId(id));
    storeCollections.save(existingStore);
    return existingStore;
  }

  public @ResponseBody boolean deleteStore(String id) {
    Store existingStore = storeCollections.findById(id).orElse(null);
    if (existingStore != null) {
      storeCollections.delete(existingStore);
      return true;
    } else {
      return false;
    }
  }

  public @ResponseBody boolean deleteProductById(String id, String productId) {
    Store existingStore = storeCollections.findById(id).orElse(null);
    if (existingStore != null) {
      existingStore.getProductsData().removeIf(product -> product.getId().equals(productId));
      storeCollections.save(existingStore);
      return true;
    } else {
      return false;
    }
  }

}
