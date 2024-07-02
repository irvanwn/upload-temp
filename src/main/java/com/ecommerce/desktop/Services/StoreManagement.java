package com.ecommerce.desktop.Services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecommerce.desktop.Model.Store;
import com.ecommerce.desktop.Repository.StoreRepository;
import com.ecommerce.desktop.Util.Randomizer;

@Service
public class StoreManagement {

  @Autowired
  private StoreRepository storeCollections;

  public @ResponseBody boolean addStore(Store newstore) {
    String prefix = "store-";
    newstore.setId(prefix + Randomizer.generateRandomNumber(10));
    newstore.setRating(0);
    newstore.setCreatedAt(new Date().toString());
    newstore.setUpdatedAt(new Date().toString());
    storeCollections.save(newstore);
    return true;
  }

}
