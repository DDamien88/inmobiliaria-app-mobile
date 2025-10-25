package com.example.inmobiliariaderamo.ui.contratos;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariaderamo.modelo.Contrato;
import com.example.inmobiliariaderamo.modelo.Inmueble;
import com.example.inmobiliariaderamo.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleContratoViewModel extends AndroidViewModel {

    private MutableLiveData<Contrato> contratoLiveData = new MutableLiveData<>();
    private MutableLiveData<Inmueble> mInmueble = new MutableLiveData<>();

    public DetalleContratoViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Contrato> getContratoLiveData() {
        return contratoLiveData;
    }

    public LiveData<Inmueble> getMInmueble(){
        return mInmueble;
    }

    public void recuperarInmueble(Bundle bundle){
        Inmueble inmueble = (Inmueble) bundle.getSerializable("inmueble");
        if (inmueble != null) {
            mInmueble.setValue(inmueble);
        }
    }
    public void cargarContrato(int idInmueble) {
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmoServicio api = ApiClient.getInmoServicio();

        Call<Contrato> call = api.obtenerContratoPorInmueble("Bearer " + token, idInmueble);
        call.enqueue(new Callback<Contrato>() {
            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                if (response.isSuccessful() && response.body() != null) {
                    contratoLiveData.postValue(response.body());
                    Toast.makeText(getApplication(), "Datos cargados correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("DetalleContratoVM", "Error en respuesta: " + response.code());
                    Toast.makeText(getApplication(), "Error de servidor", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {
                Log.e("DetalleContratoVM", "Fallo de conexión: " + t.getMessage());
                Toast.makeText(getApplication(), "Error de servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
