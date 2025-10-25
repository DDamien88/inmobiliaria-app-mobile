package com.example.inmobiliariaderamo.ui.Listar;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import com.example.inmobiliariaderamo.databinding.FragmentDetalleInmuebleBinding;
import com.example.inmobiliariaderamo.modelo.Inmueble;
import com.example.inmobiliariaderamo.request.ApiClient;


public class DetalleInmuebleFragment extends Fragment {

    private DetalleInmuebleViewModel mv;
    private FragmentDetalleInmuebleBinding binding;
    private Inmueble inmueble;

    public static DetalleInmuebleFragment newInstance() {
        return new DetalleInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inmueble = new Inmueble();
        mv = new ViewModelProvider(this).get(DetalleInmuebleViewModel.class);
        binding = FragmentDetalleInmuebleBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        mv.getMInmueble().observe(getViewLifecycleOwner(), new Observer<Inmueble>() {
            @Override
            public void onChanged(Inmueble inmueble) {
                binding.etCodigo.setText(String.valueOf(inmueble.getIdInmueble()));
                binding.etDireccion.setText(inmueble.getDireccion());
                binding.etUso.setText(inmueble.getUso());
                binding.etAmbientes.setText(String.valueOf(inmueble.getAmbientes()));
                binding.etPrecio.setText(String.valueOf(inmueble.getValor()));
                binding.etTipo.setText(inmueble.getTipo());
                binding.cbDisponible.setChecked(inmueble.isDisponible());
                Glide.with(getContext())
                        .load(ApiClient.BASE_URL + inmueble.getImagen())
                        .placeholder(null)
                        .error("null")
                        .into(binding.ivInmueble);
            }
        });



        binding.btEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inmueble.setDisponible(binding.cbDisponible.isChecked());
                mv.actualizarInmueble(inmueble);
            }
        });


        mv.recuperarInmueble(getArguments());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}