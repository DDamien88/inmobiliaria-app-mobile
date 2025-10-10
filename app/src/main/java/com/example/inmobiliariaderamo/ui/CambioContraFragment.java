package com.example.inmobiliariaderamo.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmobiliariaderamo.databinding.FragmentCambioContraBinding;

public class CambioContraFragment extends Fragment {

    private CambioContraViewModel vm;
    private FragmentCambioContraBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentCambioContraBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(CambioContraViewModel.class);

        binding.btnGuardarContraNueva.setOnClickListener(v -> {
            String vieja = binding.etContraVieja.getText().toString();
            String nueva = binding.etContraNueva.getText().toString();
            vm.cambiarContraseña(vieja, nueva);
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
