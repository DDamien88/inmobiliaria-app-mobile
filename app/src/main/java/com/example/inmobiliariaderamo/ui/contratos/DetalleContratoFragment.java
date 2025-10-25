package com.example.inmobiliariaderamo.ui.contratos;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.inmobiliariaderamo.R;
import com.example.inmobiliariaderamo.databinding.FragmentDetalleContratoBinding;
import com.example.inmobiliariaderamo.modelo.Contrato;
import com.example.inmobiliariaderamo.modelo.Inmueble;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DetalleContratoFragment extends Fragment {

    private DetalleContratoViewModel vm;
    private FragmentDetalleContratoBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetalleContratoBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(DetalleContratoViewModel.class);
        View view = binding.getRoot();


        vm.recuperarInmueble(getArguments());


        vm.getMInmueble().observe(getViewLifecycleOwner(), inmueble -> {
            vm.cargarContrato(inmueble.getIdInmueble());
        });

        vm.getContratoLiveData().observe(getViewLifecycleOwner(), contrato -> {

            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

            String fechaInicio = (contrato.getFechaInicio() != null)
                    ? formato.format(contrato.getFechaInicio()) : "No hay datos";

            String fechaFin = (contrato.getFechaFin() != null)
                    ? formato.format(contrato.getFechaFin()) : "No hay datos";

            binding.tvDireccionDetalle.setText("Dirección: " + contrato.getInmueble().getDireccion());
            binding.tvInquilinoNombre.setText("Inquilino: " +
                    contrato.getInquilino().getNombre() + " " + contrato.getInquilino().getApellido());
            binding.tvFechaInicio.setText("Inicio: " + fechaInicio);
            binding.tvFechaFin.setText("Fin: " + fechaFin);
            binding.tvMontoAlquiler.setText("Monto: $" + contrato.getMonto());
            binding.tvDetalleContrato.setText("Contrato n°: " + contrato.getIdContrato());

        });

        binding.btnPagos.setOnClickListener(v -> {
            Contrato contratoActual = vm.getContratoLiveData().getValue();
                Bundle bundle = new Bundle();
                bundle.putSerializable("contrato", contratoActual);
                Navigation.findNavController(v).navigate(R.id.detallePagosFragment, bundle);
        });



        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
