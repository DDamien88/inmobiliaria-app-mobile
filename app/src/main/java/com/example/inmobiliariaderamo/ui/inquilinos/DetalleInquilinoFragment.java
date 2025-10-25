package com.example.inmobiliariaderamo.ui.inquilinos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmobiliariaderamo.databinding.FragmentDetalleInquilinoBinding;

public class DetalleInquilinoFragment extends Fragment {

    private FragmentDetalleInquilinoBinding binding;
    private DetalleInquilinoViewModel vm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetalleInquilinoBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(DetalleInquilinoViewModel.class);
        View view = binding.getRoot();

        Bundle bundle = getArguments();
            int idInmueble = bundle.getInt("idInmueble");
            vm.cargarContrato(idInmueble);



        vm.getMInquilino().observe(getViewLifecycleOwner(), inquilino -> {
                binding.tvNombre.setText(inquilino.getNombre() + " " + inquilino.getApellido());
                binding.tvDni.setText("DNI: " + inquilino.getDni());
                binding.tvTelefono.setText("Tel: " + inquilino.getTelefono());
                binding.tvEmail.setText("Email: " + inquilino.getEmail());
                binding.tvGarante.setText("Garante: " + inquilino.getGarante());
                binding.tvTelGarante.setText("Teléfono garante:  " + inquilino.getTelGarante());
        });

        return view;
    }
}
