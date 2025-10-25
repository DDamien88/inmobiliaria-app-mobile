package com.example.inmobiliariaderamo.ui.Listar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmobiliariaderamo.databinding.FragmentAgregarInmuebleBinding;
import com.example.inmobiliariaderamo.modelo.Inmueble;

import java.io.IOException;

public class AgregarInmuebleFragment extends Fragment {

    private FragmentAgregarInmuebleBinding binding;
    private AgregarInmuebleViewModel mv;
    private Intent intent;
    private ActivityResultLauncher<Intent> arl;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAgregarInmuebleBinding.inflate(inflater, container, false);

        mv = new ViewModelProvider(this).get(AgregarInmuebleViewModel.class);

        abrirGaleria();
        binding.btnSeleccionarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arl.launch(intent);
            }
        });
        binding.btnGuardarInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarInmueble();

            }
        });

        mv.getMUri().observe(getViewLifecycleOwner(), new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                binding.ivInmueble.setImageURI(uri);
            }
        });

        mv = new ViewModelProvider(this).get(AgregarInmuebleViewModel.class);
        //View view = binding.getRoot();
        return binding.getRoot();
    }


    private void cargarInmueble() {
        String direccion = binding.etDireccion.getText().toString();
        String uso = binding.etUso.getText().toString();
        String tipo = binding.etTipo.getText().toString();
        String latitud = binding.etLatitud.getText().toString();
        String longitud = binding.etLongitud.getText().toString();
        String superficie = binding.etSuperficie.getText().toString();
        String precio = binding.etPrecio.getText().toString();
        String ambientes = binding.etAmbientes.getText().toString();
        boolean disponible = binding.cbDisponible.isChecked();

        if (direccion.isEmpty() || uso.isEmpty() || tipo.isEmpty() || latitud.isEmpty()
                || longitud.isEmpty() || superficie.isEmpty() ||
        precio.isEmpty() || ambientes.isEmpty()){
            Toast.makeText(getContext(), "No pude haber campos vacíos", Toast.LENGTH_SHORT).show();
        }
        mv.guardarInmueble(direccion,uso , tipo,latitud,longitud, superficie,precio,ambientes,disponible);
    }

    private void abrirGaleria() {
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                mv.recibirFoto(result);
                binding.btnGuardarInmueble.setEnabled(true);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
