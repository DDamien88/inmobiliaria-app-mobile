package com.example.inmobiliariaderamo.ui.inicio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmobiliariaderamo.R;
import com.example.inmobiliariaderamo.databinding.FragmentInicioBinding;
import com.google.android.gms.maps.SupportMapFragment;

public class InicioFragment extends Fragment {

    private FragmentInicioBinding binding;
    private InicioViewModel vm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentInicioBinding.inflate(inflater, container, false);

        vm = new ViewModelProvider(this).get(InicioViewModel.class);

        vm.getMutableMapaActual().observe(getViewLifecycleOwner(), mapaActual -> {
            SupportMapFragment mapFragment =
                    (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            if (mapFragment != null) {
                mapFragment.getMapAsync(mapaActual);
            }
        });

        vm.obtenerMapa();

        return binding.getRoot();
    }
}
