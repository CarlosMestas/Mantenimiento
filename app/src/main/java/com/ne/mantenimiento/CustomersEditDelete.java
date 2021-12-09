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

public class CustomersEditDelete extends AppCompatActivity {

    private EditText editTextId, editTextName, editTextDni, editTextStatus;
    private Button buttonEdit, buttonDelete, buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers_edit_delete);

        editTextId = findViewById(R.id.customersEDId);
        editTextName = findViewById(R.id.customersEDname);
        editTextDni = findViewById(R.id.customersEDDni);
        editTextStatus = findViewById(R.id.customersEDStatus);

        buttonEdit = findViewById(R.id.customersEDButtonEdit);
        buttonDelete = findViewById(R.id.customersEDButtonDelete);
        buttonCancel = findViewById(R.id.customersEDButtonCancel);

        Intent intent = getIntent();

        String id  = intent.getStringExtra("id").toString();
        String nam = intent.getStringExtra("nam").toString();
        String dni = intent.getStringExtra("dni").toString();
        String est = intent.getStringExtra("est").toString();

        editTextId.setText(id);
        editTextName.setText(nam);
        editTextDni.setText(dni);
        editTextStatus.setText(est);

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editCustomer();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCustomer();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomersEditDelete.this, Menu.class);
                startActivity(intent);
            }
        });

    }

    public void editCustomer(){
        try {
            String idE, nameE, dniE, statusE;

            idE = editTextId.getText().toString();
            nameE = editTextName.getText().toString();
            dniE = editTextDni.getText().toString();
            statusE = editTextStatus.getText().toString();

            SQLiteDatabase db = openOrCreateDatabase("ne", Context.MODE_PRIVATE, null);

            String sql = "update customer set cusNam = ?, cusDni = ?, cusEst = ? where id = ?";

            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1, nameE);
            statement.bindString(2, dniE);
            statement.bindString(3, statusE);
            statement.bindString(4, idE);

            statement.execute();
            Toast.makeText(this, "Cliente modificado", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), Menu.class);
            startActivity(intent);

        }
        catch (Exception e){
            Toast.makeText(this, "Cliente no modificado", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteCustomer(){
        try {
            String idE, nameE, dniE, statusE;

            idE = editTextId.getText().toString();
            nameE = editTextName.getText().toString();
            dniE = editTextDni.getText().toString();
            statusE = editTextStatus.getText().toString();

            SQLiteDatabase db = openOrCreateDatabase("ne", Context.MODE_PRIVATE, null);

            String sql = "update customer set cusNam = ?, cusDni = ?, cusEst = ? where id = ?";

            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1, nameE);
            statement.bindString(2, dniE);
            statement.bindString(3, "*");
            statement.bindString(4, idE);

            statement.execute();
            Toast.makeText(this, "Cliente modificado", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), Menu.class);
            startActivity(intent);

        }
        catch (Exception e){
            Toast.makeText(this, "Cliente no modificado", Toast.LENGTH_SHORT).show();
        }
    }

}