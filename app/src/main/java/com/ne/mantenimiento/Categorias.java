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

public class Categorias extends AppCompatActivity {

    private EditText editTextName, editTextDescription;
    private Button buttonAdd, buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);

        editTextName = findViewById(R.id.categoriasEditTextName);
        editTextDescription = findViewById(R.id.categoriasEditTextDescription);

        buttonAdd = findViewById(R.id.categoriasButtonAdd);
        buttonCancel = findViewById(R.id.categoriasButtonCancel);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertCategory();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Categorias.this, Menu.class);
                startActivity(intent);
            }
        });

    }


    private void insertCategory(){
        try {
            String name, description;
            name = editTextName.getText().toString();
            description = editTextDescription.getText().toString();

            SQLiteDatabase db = openOrCreateDatabase("ne", Context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS category(id INTEGER PRIMARY KEY AUTOINCREMENT, catNam VARCHAR, catDes VARCHAR, catEst VARCHAR)");

            String sql = "insert into category (catNam, catDes, catEst) values (?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1, name);
            statement.bindString(2, description);
            statement.bindString(3, "A");
            statement.execute();
            Toast.makeText(this, "Categoria agregada", Toast.LENGTH_SHORT).show();
            editTextName.setText("");
            editTextDescription.setText("");
            editTextName.requestFocus();


        }
        catch (Exception e){
            Toast.makeText(this, "Categoria no agregada", Toast.LENGTH_SHORT).show();
        }

    }

}