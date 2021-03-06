package com.example.lookgood;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.example.lookgood.adapters.ShopsAdapter;
import com.example.lookgood.databinding.FragmentWomenBinding;
import com.example.lookgood.models.Product;
import com.example.lookgood.viewmodels.ShopViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class WomenFragment extends Fragment implements ShopsAdapter.ShopInterface {

    FragmentWomenBinding fragmentShopBinding;
    private ShopsAdapter shopsAdapter;
    private ShopViewModel shopViewModel;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentShopBinding = FragmentWomenBinding.inflate(inflater,container,false);
        return fragmentShopBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        shopsAdapter = new ShopsAdapter(this);
        fragmentShopBinding.WomenRecyclerView.setAdapter(shopsAdapter);
        fragmentShopBinding.WomenRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        fragmentShopBinding.WomenRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL));

        shopViewModel = new ViewModelProvider(requireActivity()).get(ShopViewModel.class);
        shopViewModel.getWomenProducts().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                shopsAdapter.submitList(products);
            }
        });

        navController = Navigation.findNavController(view);
    }

    @Override
    public void addItem(Product product) {
        boolean isAdded = shopViewModel.addItemToCart(product);
        if (isAdded) {
            Snackbar.make(requireView(), product.getName() + " added to cart.", Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(requireView(), "Already have the max quantity in cart.", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(Product product) {
        shopViewModel.setProduct(product);
        navController.navigate(R.id.action_shopFragment_to_productDetailFragment);
    }
}