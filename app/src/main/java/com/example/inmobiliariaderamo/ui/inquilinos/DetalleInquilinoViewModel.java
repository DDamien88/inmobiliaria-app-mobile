package com.example.inmobiliariaderamo.ui.inquilinos;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliariaderamo.modelo.Contrato;
import com.example.inmobiliariaderamo.modelo.Inmueble;
import com.example.inmobiliariaderamo.modelo.Inquilino;
import com.example.inmobiliariaderamo.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleInquilinoViewModel extends AndroidViewModel {

    private MutableLiveData<Inquilino> mInquilino = new MutableLiveData<>();
    private MutableLiveData<Inmueble> mInmueble = new MutableLiveData<>();

    public DetalleInquilinoViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Inquilino> getMInquilino() {
        return mInquilino;
    }

    public LiveData<Inmueble> getMInmueble(){
        return mInmueble;
    }

    public void recuperarInmueble(Bundle bundle) {
        Inmueble inmueble = (Inmueble) bundle.getSerializable("idInmueble");
        if (inmueble != null) {
            mInmueble.setValue(inmueble);
        }
    }

    public void cargarContrato(int idInmueble) {
        ApiClient.InmoServicio api = ApiClient.getInmoServicio();
        String token = ApiClient.leerToken(getApplication());

        Call<Contrato> call = api.obtenerContratoPorInmueble("Bearer " + token, idInmueble);
        call.enqueue(new Callback<Contrato>() {
            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mInquilino.setValue(response.body().getInquilino());
                    Toast.makeText(getApplication(), "Lista cargada correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("DetalleInquilinoVM", "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {
                Log.e("DetalleInquilinoVM", "Fallo: " + t.getMessage());
                Toast.makeText(getApplication(), "Error del servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
