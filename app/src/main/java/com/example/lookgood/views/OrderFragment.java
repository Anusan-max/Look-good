package com.example.lookgood.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lookgood.R;
import com.example.lookgood.databinding.FragmentOrderBinding;
import com.example.lookgood.models.CartItem;
import com.example.lookgood.repositories.OrderRepo;
import com.example.lookgood.viewmodels.ShopViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import static com.example.lookgood.BR.cartItem;

public class OrderFragment extends Fragment {


    NavController navController;
    FragmentOrderBinding fragmentOrderBinding;
    ShopViewModel shopViewModel;
    FirebaseAuth fAuth;
    DatabaseReference reference;


    public OrderFragment() {
        fAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_order, container, false);
        fragmentOrderBinding = FragmentOrderBinding.inflate(inflater, container, false);
        return fragmentOrderBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        shopViewModel = new ViewModelProvider(requireActivity()).get(ShopViewModel.class);

        fragmentOrderBinding.continueShoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrder();
                shopViewModel.resetCart();
                navController.navigate(R.id.action_orderFragment_to_shopFragment);

            }
        });
    }

    private void placeOrder() {
        shopViewModel.getCart().observe(getViewLifecycleOwner(), new Observer<List<CartItem>>() {
            @Override
            public void onChanged(List<CartItem> cartItems) {
                if(!cartItems.isEmpty()) {
                    OrderRepo orderRepo = new OrderRepo();
                    orderRepo.setOrderItems(cartItems);
                    reference.child("orders").child(orderRepo.getOrderId()).setValue(orderRepo);
                }
            }
        });
    }
}