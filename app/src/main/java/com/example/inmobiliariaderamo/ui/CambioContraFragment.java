package com.example.inmobiliariaderamo.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.inmobiliariaderamo.databinding.FragmentCambioContraBinding;
import com.example.inmobiliariaderamo.request.ApiClient;

public class CambioContraFragment extends Fragment {

    private CambioContraViewModel vm;
    private FragmentCambioContraBinding binding;
    private ApiClient api;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentCambioContraBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(CambioContraViewModel.class);

        // Observar mensajes del ViewModel
        vm.getMensaje().observe(getViewLifecycleOwner(), mensaje -> {
            Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();
        });

        binding.btnGuardarContraNueva.setOnClickListener(v -> {
            String vieja = binding.etContraVieja.getText().toString().trim();
            String nueva = binding.etContraNueva.getText().toString().trim();

            String token = ApiClient.leerToken(getContext());
            vm.cambiarPassword(vieja, nueva, token);
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
