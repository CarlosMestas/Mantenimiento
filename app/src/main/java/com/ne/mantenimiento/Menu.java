package com.ne.mantenimiento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {

    private Button buttonCategoriasAdd, buttonCategoriasList;
    private Button buttonCustomersAdd, buttonCustomersList;
    private Button buttonProductsAdd, buttonProductsList;
    private Button buttonSalesAdd, buttonSalesList;


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

        buttonCustomersAdd = findViewById(R.id.menuButtonCustomersAdd);
        buttonCustomersList = findViewById(R.id.menuButtonCustomersList);

        buttonCustomersAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, Customers.class);
                startActivity(intent);
            }
        });

        buttonCustomersList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, CustomersList.class);
                startActivity(intent);
            }
        });

        buttonProductsAdd = findViewById(R.id.menuButtonProductsAdd);
        buttonProductsList = findViewById(R.id.menuButtonProductsList);

        buttonProductsAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, Products.class);
                startActivity(intent);
            }
        });

        buttonProductsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, ProductsList.class);
                startActivity(intent);
            }
        });


        buttonSalesAdd = findViewById(R.id.menuButtonSalesAdd);
        buttonSalesList = findViewById(R.id.menuButtonSalesList);

        buttonSalesAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, Sales.class);
                startActivity(intent);
            }
        });

        buttonSalesList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, SalesList.class);
                startActivity(intent);
            }
        });







    }
}