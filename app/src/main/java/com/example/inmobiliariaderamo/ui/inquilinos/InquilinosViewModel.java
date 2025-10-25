package com.example.inmobiliariaderamo.ui.inquilinos;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariaderamo.modelo.Inmueble;
import com.example.inmobiliariaderamo.request.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquilinosViewModel extends AndroidViewModel {

    private MutableLiveData<List<Inmueble>> inmueblesVigentes = new MutableLiveData<>();

    public InquilinosViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Inmueble>> getInmueblesVigentes() {
        return inmueblesVigentes;
    }

    public void cargarInmueblesConContratoVigente() {
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmoServicio api = ApiClient.getInmoServicio();

        Call<List<Inmueble>> call = api.obtenerInmuebleConContrato("Bearer " + token);
        call.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Inmueble> listaFiltrada = new ArrayList<>();
                    for (Inmueble i : response.body()) {
                        if (i.isTieneContratoVigente()) {
                            listaFiltrada.add(i);
                        }
                    }
                    inmueblesVigentes.setValue(listaFiltrada);
                } else {
                    Log.d("InquilinosVM", "Error al obtener inmuebles: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                Log.e("InquilinosVM", "Error de conexión: " + t.getMessage());
            }
        });
    }
}
