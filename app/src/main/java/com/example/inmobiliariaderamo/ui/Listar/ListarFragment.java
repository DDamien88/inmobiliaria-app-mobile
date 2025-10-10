package com.example.inmobiliariaderamo.ui.Listar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inmobiliariaderamo.databinding.FragmentListarBinding;
import com.example.inmobiliariaderamo.modelo.Inmueble;
import java.util.ArrayList;
import java.util.List;

public class ListarFragment extends Fragment {

    private FragmentListarBinding binding;
    private ListarFragmentViewModel mv;
    private InmuebleAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentListarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mv = new ViewModelProvider(this).get(ListarFragmentViewModel.class);

        // Configurar RecyclerView
        GridLayoutManager glm = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
        binding.listaInmuebles.setLayoutManager(glm);

        adapter = new InmuebleAdapter(new ArrayList<>(), getContext(), getLayoutInflater());
        binding.listaInmuebles.setAdapter(adapter);

        // Observar cambios en la lista
        mv.getLista().observe(getViewLifecycleOwner(), lista -> {
            adapter.setLista(lista);
        });

        mv.cargarLista();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
