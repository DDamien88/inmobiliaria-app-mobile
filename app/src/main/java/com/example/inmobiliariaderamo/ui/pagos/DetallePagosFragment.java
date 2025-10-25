package com.example.inmobiliariaderamo.ui.pagos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.inmobiliariaderamo.databinding.FragmentDetallePagosBinding;
import com.example.inmobiliariaderamo.modelo.Contrato;

public class DetallePagosFragment extends Fragment {

    private DetallePagosViewModel vm;
    private FragmentDetallePagosBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetallePagosBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(DetallePagosViewModel.class);

        View view = binding.getRoot();
        binding.recyclerPagos.setLayoutManager(new LinearLayoutManager(getContext()));

        vm.getPagosLiveData().observe(getViewLifecycleOwner(), pagos -> {
            PagosAdapter adapter = new PagosAdapter(pagos);
            binding.recyclerPagos.setAdapter(adapter);

        });


        Contrato contrato = (Contrato) getArguments().getSerializable("contrato");
        vm.cargarPagos(contrato.getIdContrato());


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
