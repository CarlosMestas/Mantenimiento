package com.ne.mantenimiento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Customers extends AppCompatActivity {

    private EditText editTextName, editTextDni;
    private Button buttonAdd, buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);

        editTextName = findViewById(R.id.customersEditTextName);
        editTextDni = findViewById(R.id.customersEditTextDni);

        buttonAdd = findViewById(R.id.customersButtonAdd);
        buttonCancel = findViewById(R.id.customersButtonCancel);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertCustomer();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Customers.this, Menu.class);
                startActivity(intent);
            }
        });

    }

    private void insertCustomer(){
        try {
            String name, dni;
            name = editTextName.getText().toString();
            dni = editTextDni.getText().toString();

            SQLiteDatabase db = openOrCreateDatabase("ne", Context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS customer(id INTEGER PRIMARY KEY AUTOINCREMENT, cusNam VARCHAR, cusDni VARCHAR, cusEst VARCHAR)");

            String sql = "insert into customer (cusNam, cusDni, cusEst) values (?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1, name);
            statement.bindString(2, dni);
            statement.bindString(3, "A");
            statement.execute();
            Toast.makeText(this, "Cliente agregado", Toast.LENGTH_SHORT).show();
            editTextName.setText("");
            editTextDni.setText("");
            editTextName.requestFocus();


        }
        catch (Exception e){
            Toast.makeText(this, "Cliente no agregada", Toast.LENGTH_SHORT).show();
        }
    }
}