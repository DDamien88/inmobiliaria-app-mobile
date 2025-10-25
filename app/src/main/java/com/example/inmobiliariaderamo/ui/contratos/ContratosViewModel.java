package com.example.inmobiliariaderamo.ui.contratos;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariaderamo.modelo.Contrato;
import com.example.inmobiliariaderamo.modelo.Inmueble;
import com.example.inmobiliariaderamo.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratosViewModel extends AndroidViewModel {

    private MutableLiveData<List<Inmueble>> inmueblesConContrato = new MutableLiveData<>();

    public ContratosViewModel(@NonNull Application application) {
        super(application);
        cargarInmueblesConContrato();
    }

    public LiveData<List<Inmueble>> getInmueblesConContrato() {
        return inmueblesConContrato;
    }

    private void cargarInmueblesConContrato() {
        ApiClient.InmoServicio api = ApiClient.getInmoServicio();
        String token = ApiClient.leerToken(getApplication());

        Call<List<Inmueble>> call = api.obtenerInmuebleConContrato("Bearer " + token);
        call.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    inmueblesConContrato.postValue(response.body());
                } else {
                    Log.e("ContratosViewModel", "Error al cargar contratos: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                Log.e("ContratosViewModel", "Fallo de conexión: " + t.getMessage());
            }
        });
    }
}
