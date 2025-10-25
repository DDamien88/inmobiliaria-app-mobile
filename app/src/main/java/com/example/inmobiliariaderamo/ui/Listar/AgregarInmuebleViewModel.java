package com.example.inmobiliariaderamo.ui.Listar;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariaderamo.modelo.Inmueble;
import com.example.inmobiliariaderamo.request.ApiClient;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.graphics.Bitmap;

public class AgregarInmuebleViewModel extends AndroidViewModel {

    private MutableLiveData<Inmueble> mInmueble = new MutableLiveData<>();
    private MutableLiveData<Uri> uriMutableLiveData = new MutableLiveData<>();

    private static Inmueble inmueblelleno;

    public AgregarInmuebleViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Inmueble> getMInmueble() {
        return mInmueble;
    }

    public LiveData<Uri> getMUri() {
        return uriMutableLiveData;
    }

    public void recibirFoto(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            Uri uri = data.getData();
            Log.d("salada", uri.toString());
            uriMutableLiveData.setValue(uri);
        }
    }

    // Guardar nuevo inmueble
    public void guardarInmueble(String direccion,
                                String uso,
                                String tipo,
                                String latitud,
                                String longitud,
                                String superficie,
                                String precio,
                                String ambientes,
                                boolean disponible) {
        try {
            int amb = Integer.parseInt(ambientes);
            double prec = Double.parseDouble(precio);
            double lat = Double.parseDouble(latitud);
            double longi = Double.parseDouble(longitud);
            int supe = Integer.parseInt(superficie);

            Inmueble inmueble = new Inmueble();
            inmueble.setDireccion(direccion);
            inmueble.setUso(uso);
            inmueble.setTipo(tipo);
            inmueble.setLatitud(lat);
            inmueble.setLongitud(longi);
            inmueble.setSuperficie(supe);
            inmueble.setValor(prec);
            inmueble.setAmbientes(amb);
            inmueble.setDisponible(disponible);

            //convertir la img en bits
            byte[] imagen = transformarImagen();
            if (imagen.length == 0) {
                Toast.makeText(getApplication(), "Debe seleccionar foto", Toast.LENGTH_SHORT).show();
                return;
            }

            String inmuebleJson = new Gson().toJson(inmueble);
            RequestBody inmuebleBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), inmuebleJson);
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imagen);
            MultipartBody.Part imagenPart = MultipartBody.Part.createFormData("imagen", "imagen.jpg", requestFile);

            String token = ApiClient.leerToken(getApplication());
            ApiClient.InmoServicio api = ApiClient.getInmoServicio();
            Call<Inmueble> llamada = api.cargarInmueble("Bearer " + token, imagenPart, inmuebleBody);
            llamada.enqueue(new Callback<Inmueble>() {
                @Override
                public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplication(), "Inmueble guardado correctamente", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Inmueble> call, Throwable throwable) {
                    Toast.makeText(getApplication(), "Error al guardar inmueble", Toast.LENGTH_SHORT).show();
                }
            });


        } catch (NumberFormatException e) {
            Toast.makeText(getApplication(), "Ingrese números válidos", Toast.LENGTH_SHORT).show();
        }catch (NullPointerException ex){
            Toast.makeText(getApplication(), "Ingrese números válidos", Toast.LENGTH_SHORT).show();
        }

    }

    private byte[] transformarImagen() {
        Uri uri = uriMutableLiveData.getValue();
        InputStream inputStream = null;
        try {
            inputStream = getApplication().getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (FileNotFoundException e) {
            Toast.makeText(getApplication(), "Corrobore", Toast.LENGTH_SHORT).show();
            return new byte[]{};
        }

    }


}
