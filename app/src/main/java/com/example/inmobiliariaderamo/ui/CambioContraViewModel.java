package com.example.inmobiliariaderamo.ui;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariaderamo.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CambioContraViewModel extends AndroidViewModel {
    private MutableLiveData<String> mensaje = new MutableLiveData<>();

    public CambioContraViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<String> getMensaje(){
        return mensaje;
    }
    String token = ApiClient.leerToken(getApplication());
    public void cambiarPassword(String currentPassword, String newPassword, String token) {
        if (currentPassword.isEmpty() || newPassword.isEmpty()) {
            mensaje.setValue("Complete ambos campos");
            return;
        }

        ApiClient.InmoServicio api = ApiClient.getInmoServicio();
        Call<Void> call = api.changePassword("Bearer " + token, currentPassword, newPassword);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    mensaje.setValue("Contraseña cambiada correctamente");
                } else {
                    mensaje.setValue("Error al cambiar la contraseña");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                mensaje.setValue("Error de conexión con el servidor");
            }
        });
    }

}
