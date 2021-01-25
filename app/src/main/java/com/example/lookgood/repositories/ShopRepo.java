package com.example.lookgood.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lookgood.models.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShopRepo {
    private MutableLiveData<List<Product>> mutableProductList;
    private MutableLiveData<List<Product>> mutableKidsProductList;
    private MutableLiveData<List<Product>> mutableMenProductList;
    private MutableLiveData<List<Product>> mutableWomenProductList;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    List<Product> productList = new ArrayList<>();

    public LiveData<List<Product>> getProducts() {
        if (mutableProductList == null) {
            mutableProductList = new MutableLiveData<>();
            loadProducts();
        }
        return mutableProductList;
    }

    public LiveData<List<Product>> getKidsProducts() {
        if(mutableKidsProductList == null) {
            mutableKidsProductList = new MutableLiveData<>();
            mutableKidsProductList.setValue(loadProductsFor("Kids"));
        }
        return mutableKidsProductList;
    }

    public LiveData<List<Product>> getMenProducts() {
        if(mutableMenProductList == null) {
            mutableMenProductList = new MutableLiveData<>();
            mutableMenProductList.setValue(loadProductsFor("Men"));
        }
        return mutableMenProductList;
    }

    public LiveData<List<Product>> getWomenProducts() {
        if(mutableWomenProductList == null) {
            mutableWomenProductList = new MutableLiveData<>();
            mutableWomenProductList.setValue(loadProductsFor("Women"));
        }
        return mutableWomenProductList;
    }





    private List<Product> loadProductsFor(String type) {
        List<Product> productList = new ArrayList<>();
        if (this.productList != null) {
            for(Product product: this.productList){
                if(product.getCategory().equals(type)) {
                    productList.add(product);
                }
            }
        }
        return productList;
    }



    private void loadProducts() {
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("products");


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                productList.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Product product = postSnapshot.getValue(Product.class);
                    productList.add(product);
                    mutableProductList.setValue(productList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }
}
