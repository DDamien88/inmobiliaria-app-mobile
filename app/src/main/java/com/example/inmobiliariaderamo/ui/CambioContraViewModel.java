package com.example.inmobiliariaderamo.ui;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.inmobiliariaderamo.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CambioContraViewModel extends AndroidViewModel {

    public CambioContraViewModel(@NonNull Application application) {
        super(application);
    }

    public void cambiarContraseña(String actual, String nueva) {
        if (actual.isEmpty() || nueva.isEmpty()) {
            Toast.makeText(getApplication(), "Complete ambos campos", Toast.LENGTH_SHORT).show();
            return;
        }

        String token = ApiClient.leerToken(getApplication());

        ApiClient.InmoServicio api = ApiClient.getInmoServicio();
        Call<Void> call = api.cambiarPassword("Bearer " + token, actual, nueva);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplication(), "Contraseña actualizada correctamente", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {
                    Toast.makeText(getApplication(), "Contraseña actual incorrecta", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplication(), "Error al actualizar (" + response.code() + ")", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplication(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
