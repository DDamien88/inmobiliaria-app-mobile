package com.example.inmobiliariaderamo.ui.perfil;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariaderamo.modelo.Propietario;
import com.example.inmobiliariaderamo.request.ApiClient;
import com.google.android.gms.common.api.Api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {

    private MutableLiveData<Propietario> propietario;
    private MutableLiveData<Boolean> mEstado = new MutableLiveData<>();
    private MutableLiveData<String> mNombre = new MutableLiveData<>();

    public PerfilViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Propietario> getPropietario() {
        if (propietario == null) {
            propietario = new MutableLiveData<>();
        }
        return propietario;
    }

    public LiveData<Boolean> getMEstado() {
        return mEstado;
    }

    public LiveData<String> getMNombre() {
        return mNombre;
    }


    public void cambioBoton(String nombreBoton, String nombre, String apellido, String dni, String email, String telefono ) {
        if (nombreBoton.equalsIgnoreCase("editar")) {
            mEstado.setValue(true);
            mNombre.setValue("Guardar");
        }else {
            mEstado.setValue(false);
            mNombre.setValue("Editar");
            Propietario nuevo = new Propietario();
            nuevo.setIdPropietario(propietario.getValue().getIdPropietario());
            nuevo.setNombre(nombre);
            nuevo.setApellido(apellido);
            nuevo.setDni(dni);
            nuevo.setEmail(email);
            nuevo.setTelefono(telefono);
            String token = ApiClient.leerToken(getApplication());
            ApiClient.InmoServicio api = ApiClient.getInmoServicio();
            Call<Propietario> call = api.actualizarProp("Bearer " + token, nuevo);
            call.enqueue(new Callback<Propietario>() {
                @Override
                public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(getApplication(), "Datos guardados con éxito", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplication(), "Error al actualizar", Toast.LENGTH_SHORT).show();
                        //Log.d
                    }
                }

                @Override
                public void onFailure(Call<Propietario> call, Throwable throwable) {
                    Toast.makeText(getApplication(), "No se puede acceder a la API", Toast.LENGTH_SHORT).show();
                    //Log.e
                }
            });
        }
    }

    public void obtenerPerfil() {
        //SharedPreferences sp = getApplication().getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        String token = ApiClient.leerToken(getApplication());
                //sp.getString("token", "token");

        ApiClient.InmoServicio api = ApiClient.getInmoServicio();
        Call<Propietario> call = api.obtenerPerfil("Bearer " + token);

        call.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful()) {
                    propietario.postValue(response.body());
                } else {
                    Toast.makeText(getApplication(), "perfil cargado correctamente", Toast.LENGTH_SHORT).show();
                    Log.d("PerfilVM", "Error en respuesta: " + response.code());

                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Toast.makeText(getApplication(), "Error de API", Toast.LENGTH_SHORT).show();
                Log.e("PerfilVM", "Error API: " + t.getMessage());

            }
        });
    }


}
