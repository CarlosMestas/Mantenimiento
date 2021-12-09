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

public class CategoriasEditDelete extends AppCompatActivity {

    private EditText editTextId, editTextName, editTextDescription, editTextStatus;
    private Button buttonEdit, buttonDelete, buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias_edit_delete);

        editTextId = findViewById(R.id.categoriasEDId);
        editTextName = findViewById(R.id.categoriasEDname);
        editTextDescription = findViewById(R.id.categoriasEDDescription);
        editTextStatus = findViewById(R.id.categoriasEDStatus);

        buttonEdit = findViewById(R.id.categoriasEDButtonEdit);
        buttonDelete = findViewById(R.id.categoriasEDButtonDelete);
        buttonCancel = findViewById(R.id.categoriasEDButtonCancel);

        Intent intent = getIntent();

        String id  = intent.getStringExtra("id").toString();
        String nam = intent.getStringExtra("nam").toString();
        String des = intent.getStringExtra("des").toString();
        String est = intent.getStringExtra("est").toString();

        editTextId.setText(id);
        editTextName.setText(nam);
        editTextDescription.setText(des);
        editTextStatus.setText(est);


        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editCategory();
            }
        });


    }

    public void editCategory(){
        try {
            String idE, nameE, descriptionE, statusE;

            idE = editTextId.getText().toString();
            nameE = editTextName.getText().toString();
            descriptionE = editTextDescription.getText().toString();
            statusE = editTextStatus.getText().toString();

            SQLiteDatabase db = openOrCreateDatabase("ne", Context.MODE_PRIVATE, null);

            String sql = "update category set catNam = ?, catDes = ?, catEst = ? where id = ?";

            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1, nameE);
            statement.bindString(2, descriptionE);
            statement.bindString(3, statusE);
            statement.bindString(4, idE);

            statement.execute();
            Toast.makeText(this, "Categoría modificada", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), Menu.class);
            startActivity(intent);

        }
        catch (Exception e){
            Toast.makeText(this, "Categoria no modificada", Toast.LENGTH_SHORT).show();
        }

    }
}