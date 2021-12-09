package com.ne.mantenimiento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// LoginActivity
public class MainActivity extends AppCompatActivity {

    // Declaraciones de variables
    private EditText editTextUser, editTextPassword;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializaciones
        editTextUser = findViewById(R.id.loginEditTextUser);
        editTextPassword = findViewById(R.id.loginEditTextPassword);
        buttonLogin = findViewById(R.id.loginButtonLogin);

        // Acción del botón Login
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        //
        editTextUser.setText("admin");
        editTextPassword.setText("admin");

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    // Función para realizar el login
    private void login(){
        String user, password;
        user = editTextUser.getText().toString();
        password = editTextPassword.getText().toString();

        if(user.equals("") || password.equals("")){
            Toast.makeText(this, "Usuario o contraseña vacíos", Toast.LENGTH_SHORT).show();
        }
        else{
            if(user.equals("admin") && user.equals("admin")){
                Intent intent = new Intent(MainActivity.this, Menu.class);
                startActivity(intent);
            }

        }


    }
}