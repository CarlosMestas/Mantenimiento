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

public class ProductsEditDelete extends AppCompatActivity {

    private EditText editTextId, editTextName, editTextDescription, editTextQuantity, editTextPrice, editTextStatus;
    private Spinner spinnerCategories;
    private Button buttonEdit, buttonDelete, buttonCancel;

    private ArrayList<String> titles01;
    private ArrayAdapter arrayAdapter01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_edit_delete);

        editTextId = findViewById(R.id.productsEDId);
        editTextName = findViewById(R.id.productsEDEditTextName);
        editTextDescription = findViewById(R.id.productsEDEditTextDescription);
        editTextQuantity = findViewById(R.id.productsEDEditTextQuantity);
        editTextPrice = findViewById(R.id.productsEDEditTextPrice);
        editTextStatus = findViewById(R.id.productsEDEditTextStatus);

        spinnerCategories = findViewById(R.id.productsEDSpinnerCategories);

        buttonEdit = findViewById(R.id.productsEDButtonEdit);
        buttonDelete = findViewById(R.id.productsEDButtonDelete);
        buttonCancel = findViewById(R.id.productsEDButtonCancel);

        titles01 = new ArrayList<String>();

        Intent intent = getIntent();

        String id  = intent.getStringExtra("id").toString();
        String nam = intent.getStringExtra("nam").toString();
        String des = intent.getStringExtra("des").toString();
        String cat = intent.getStringExtra("cat").toString();
        String qua = intent.getStringExtra("qua").toString();
        String pri = intent.getStringExtra("pri").toString();
        String est = intent.getStringExtra("est").toString();

        int pos = 0;
        int p = 0;

        SQLiteDatabase db = openOrCreateDatabase("ne", Context.MODE_PRIVATE, null);
        final Cursor cursor = db.rawQuery("SELECT * FROM category WHERE catEst = 'A'",null);

        int category = cursor.getColumnIndex("catNam");

        titles01.clear();
        arrayAdapter01 = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, titles01);
        spinnerCategories.setAdapter(arrayAdapter01);

        final ArrayList<com.ne.mantenimiento.Models.category> categories = new ArrayList<com.ne.mantenimiento.Models.category>();
        if(cursor.moveToFirst()){
            do{
                category tmp = new category();
                tmp.name = cursor.getString(category);

                categories.add(tmp);

                if (cat.equals(cursor.getString(category)))
                    pos = p;

                titles01.add(cursor.getString(category));
                p++;
            }
            while(cursor.moveToNext());

            arrayAdapter01.notifyDataSetChanged();
        }


        editTextId.setText(id);
        editTextName.setText(nam);
        editTextDescription.setText(des);
        spinnerCategories.setSelection(pos);

        editTextQuantity.setText(qua);
        editTextPrice.setText(pri);
        editTextStatus.setText(est);



        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProduct();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProduct();
            }
        });


        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductsEditDelete.this, Menu.class);
                startActivity(intent);
            }
        });


    }


    private void editProduct() {
        try {
            String idE, nameE, descriptionE, categoryE, quantityE, priceE, statusE;

            idE = editTextId.getText().toString();
            nameE = editTextName.getText().toString();
            descriptionE = editTextDescription.getText().toString();
            categoryE = spinnerCategories.getSelectedItem().toString();
            quantityE = editTextQuantity.getText().toString();
            priceE = editTextPrice.getText().toString();
            statusE = editTextStatus.getText().toString();

            SQLiteDatabase db = openOrCreateDatabase("ne", Context.MODE_PRIVATE, null);

            String sql = "update product set proNam = ?, proDes = ?, proCat = ?, proQua = ?, proPri = ?, proEst = ? where id = ?";


            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1, nameE);
            statement.bindString(2, descriptionE);
            statement.bindString(3, categoryE);
            statement.bindString(4, quantityE);
            statement.bindString(5, priceE);
            statement.bindString(6, statusE);
            statement.bindString(7, idE);

            statement.execute();
            Toast.makeText(this, "Producto modificado", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), Menu.class);
            startActivity(intent);

        }
        catch (Exception e){
            Toast.makeText(this, "Producto no modificado", Toast.LENGTH_SHORT).show();
        }


    }

    private void deleteProduct() {
        try {
            String idE, nameE, descriptionE, categoryE, quantityE, priceE, statusE;

            idE = editTextId.getText().toString();
            nameE = editTextName.getText().toString();
            descriptionE = editTextDescription.getText().toString();
            categoryE = spinnerCategories.getSelectedItem().toString();
            quantityE = editTextQuantity.getText().toString();
            priceE = editTextPrice.getText().toString();
            statusE = editTextStatus.getText().toString();

            SQLiteDatabase db = openOrCreateDatabase("ne", Context.MODE_PRIVATE, null);

            String sql = "update product set proNam = ?, proDes = ?, proCat = ?, proQua = ?, proPri = ?, proEst = ? where id = ?";


            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1, nameE);
            statement.bindString(2, descriptionE);
            statement.bindString(3, categoryE);
            statement.bindString(4, quantityE);
            statement.bindString(5, priceE);
            statement.bindString(6, "*");
            statement.bindString(7, idE);

            statement.execute();
            Toast.makeText(this, "Producto eliminado", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), Menu.class);
            startActivity(intent);

        }
        catch (Exception e){
            Toast.makeText(this, "Producto no eliminado", Toast.LENGTH_SHORT).show();
        }
    }

}