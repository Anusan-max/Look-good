package com.example.lookgood.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lookgood.databinding.ShopRowBinding;
import com.example.lookgood.models.Product;

public class ShopsAdapter extends ListAdapter<Product, ShopsAdapter.ShopViewHolder> {

    ShopInterface shopInterface;
    public ShopsAdapter(ShopInterface shopInterface) {
        super(Product.itemCallback);
        this.shopInterface = shopInterface;
    }

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ShopRowBinding shopRowBinding = ShopRowBinding.inflate(layoutInflater,parent,false);
        shopRowBinding.setShopInterface(shopInterface);
        return new ShopViewHolder(shopRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder holder, int position) {
        Product item = getItem(position);
        holder.shopRowBinding.setProduct(item);
    }

    class ShopViewHolder extends RecyclerView.ViewHolder {

        ShopRowBinding shopRowBinding;
        public ShopViewHolder(ShopRowBinding shopRowBinding) {
            super(shopRowBinding.getRoot());
            this.shopRowBinding = shopRowBinding;
        }
    }

    public interface ShopInterface {
        void addItem(Product product);
        void onItemClick(Product product);
    }
}

