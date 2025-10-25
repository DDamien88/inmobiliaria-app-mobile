package com.example.inmobiliariaderamo.ui.contratos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inmobiliariaderamo.R;
import com.example.inmobiliariaderamo.modelo.Inmueble;

import java.util.List;

public class ContratosFragment extends Fragment {

    private ContratosViewModel viewModel;
    private RecyclerView recyclerView;
    private ContratoAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_contratos, container, false);

        recyclerView = root.findViewById(R.id.recyclerContratos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel = new ViewModelProvider(this).get(ContratosViewModel.class);
        viewModel.getInmueblesConContrato().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                adapter = new ContratoAdapter(getContext(), inmuebles, inflater);
                recyclerView.setAdapter(adapter);
            }
        });

        return root;
    }
}
