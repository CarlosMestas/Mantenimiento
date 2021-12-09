package com.ne.mantenimiento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ne.mantenimiento.Models.category;

import java.util.ArrayList;

public class Products extends AppCompatActivity {

    private EditText editTextName, editTextDescription, editTextQuantity, editTextPrice;
    private Spinner spinnerCategories;
    private Button buttonAdd, buttonCancel;

    private ArrayList<String> titles01;
    private ArrayAdapter arrayAdapter01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        editTextName = findViewById(R.id.productsEditTextName);
        editTextDescription = findViewById(R.id.productsEditTextDescription);
        editTextQuantity = findViewById(R.id.productsEditTextQuantity);
        editTextPrice = findViewById(R.id.productsEditTextPrice);

        spinnerCategories = findViewById(R.id.productsSpinnerCategories);

        buttonAdd = findViewById(R.id.productsButtonAdd);
        buttonCancel = findViewById(R.id.productsButtonCancel);

        titles01 = new ArrayList<String>();

        SQLiteDatabase db = openOrCreateDatabase("ne", Context.MODE_PRIVATE, null);
        final Cursor cursor = db.rawQuery("SELECT * FROM category WHERE catEst = 'A'",null);

        int category = cursor.getColumnIndex("catNam");

        titles01.clear();
        arrayAdapter01 = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, titles01);
        spinnerCategories.setAdapter(arrayAdapter01);

        final ArrayList<com.ne.mantenimiento.Models.category> categories = new ArrayList<category>();
        if(cursor.moveToFirst()){
            do{
                category tmp = new category();
                tmp.name = cursor.getString(category);

                categories.add(tmp);

                titles01.add(cursor.getString(category));
            }
            while(cursor.moveToNext());

            arrayAdapter01.notifyDataSetChanged();
        }

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertProduct();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Products.this, Menu.class);
                startActivity(intent);
            }
        });


    }

    private void insertProduct(){
        try {
            String name, description, category, quantity, price;
            name = editTextName.getText().toString();
            description = editTextDescription.getText().toString();
            category = spinnerCategories.getSelectedItem().toString();
            quantity = editTextQuantity.getText().toString();
            price = editTextPrice.getText().toString();

            SQLiteDatabase db = openOrCreateDatabase("ne", Context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS product(id INTEGER PRIMARY KEY AUTOINCREMENT, proNam VARCHAR, proDes VARCHAR, proCat VARCHAR, proQua VARCHAR, proPri VARCHAR, proEst VARCHAR)");

            String sql = "insert into product (proNam, proDes, proCat, proQua, proPri, proEst) values (?, ?, ?, ?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1, name);
            statement.bindString(2, description);
            statement.bindString(3, category);
            statement.bindString(4, quantity);
            statement.bindString(5, price);
            statement.bindString(6, "A");
            statement.execute();
            Toast.makeText(this, "Producto agregado", Toast.LENGTH_SHORT).show();

            editTextName.setText("");
            editTextDescription.setText("");
            editTextQuantity.setText("");
            editTextPrice.setText("");
            editTextName.requestFocus();


        }
        catch (Exception e){
            Toast.makeText(this, "Producto no agregado" + e.toString(), Toast.LENGTH_SHORT).show();

        }

    }

}