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

            String nombre = validarTexto(inquilino.getNombre());
            String apellido = validarTexto(inquilino.getApellido());
            String dni = validarTexto(inquilino.getDni());
            String telefono = validarTexto(inquilino.getTelefono());
            String email = validarTexto(inquilino.getEmail());
            String garante = validarTexto(inquilino.getGarante());
            String telGarante = validarTexto(inquilino.getTelGarante());

            binding.tvNombre.setText(nombre + " " + apellido);
            binding.tvDni.setText("DNI: " + dni);
            binding.tvTelefono.setText("Tel: " + telefono);
            binding.tvEmail.setText("Email: " + email);
            binding.tvGarante.setText("Garante: " + garante);
            binding.tvTelGarante.setText("Teléfono garante: " + telGarante);

        });

        return view;
    }

    private String validarTexto(String valor) {
        if (valor == null || valor.trim().isEmpty() || valor.equals("null")) {
            return "No hay datos";
        }
        return valor;
    }


}
