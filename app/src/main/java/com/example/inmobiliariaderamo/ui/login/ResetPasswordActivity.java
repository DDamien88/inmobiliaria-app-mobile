package com.example.inmobiliariaderamo.ui.login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmobiliariaderamo.R;
import com.example.inmobiliariaderamo.databinding.ActivityResetPasswordBinding;
import com.example.inmobiliariaderamo.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends AppCompatActivity {
    private ActivityResetPasswordBinding binding;
    private ResetPasswordActivityViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ResetPasswordActivityViewModel vm = new ViewModelProvider(this).get(ResetPasswordActivityViewModel.class);

        vm.getMensaje().observe(this, mensaje -> {
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        });

        binding.btnEnviarReset.setOnClickListener(v -> {
            String email = binding.etEmailReset.getText().toString().trim();
            vm.enviarSolicitudReset(email);
        });



        binding.btnEnviarReset.setOnClickListener(v -> {
            String email = binding.etEmailReset.getText().toString().trim();

            viewModel.enviarSolicitudReset(email);
        });
    }

}