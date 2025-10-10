package com.example.inmobiliariaderamo.ui.login;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariaderamo.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivityViewModel extends AndroidViewModel {

    private final MutableLiveData<String> mensaje = new MutableLiveData<>();

    public ResetPasswordActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getMensaje() {
        return mensaje;
    }

    public void enviarSolicitudReset(String email) {
        if (email.isEmpty()) {
            mensaje.setValue("Ingrese su correo electrónico");
            return;
        }

        ApiClient.InmoServicio api = ApiClient.getInmoServicio();
        Call<Void> call = api.resetPassword(email);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    mensaje.setValue("Revise su correo electrónico");
                } else {
                    mensaje.setValue("No se encontró el usuario");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                mensaje.setValue("Error al conectar con el servidor");
            }
        });
    }
}
