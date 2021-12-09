package com.ne.mantenimiento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {

    private Button buttonCategoriasAdd, buttonCategoriasList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        buttonCategoriasAdd = findViewById(R.id.menuButtonCategoriasAdd);
        buttonCategoriasList = findViewById(R.id.menuButtonCategoriasList);

        buttonCategoriasAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, Categorias.class);
                startActivity(intent);
            }
        });

        buttonCategoriasList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, CategoriasList.class);
                startActivity(intent);
            }
        });



    }
}