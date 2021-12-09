package com.ne.mantenimiento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ne.mantenimiento.Models.customer;
import com.ne.mantenimiento.Models.product;

import java.util.ArrayList;

public class ProductsList extends AppCompatActivity {

    private ListView productsListView;
    private ArrayList<String> titles;
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);

        productsListView = findViewById(R.id.productsListView);
        titles = new ArrayList<String>();

        SQLiteDatabase db = openOrCreateDatabase("ne", Context.MODE_PRIVATE, null);
        final Cursor cursor = db.rawQuery("SELECT * FROM product",null);

        int id = cursor.getColumnIndex("id");
        int name = cursor.getColumnIndex("proNam");
        int description = cursor.getColumnIndex("proDes");
        int category = cursor.getColumnIndex("proCat");
        int quantity = cursor.getColumnIndex("proQua");
        int price = cursor.getColumnIndex("proPri");
        int status = cursor.getColumnIndex("proEst");

        titles.clear();
        arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, titles);
        productsListView.setAdapter(arrayAdapter);

        final ArrayList<product> products = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                product tmp = new product();
                tmp.id = cursor.getString(id);
                tmp.name = cursor.getString(name);
                tmp.description = cursor.getString(description);
                tmp.category = cursor.getString(category);
                tmp.quantity = cursor.getString(quantity);
                tmp.price = cursor.getString(price);
                tmp.status = cursor.getString(status);

                products.add(tmp);

                titles.add(cursor.getString(id) + "\t" +
                        cursor.getString(name) + "\t" +
                        cursor.getString(description) + "\t" +
                        cursor.getString(category) + "\t" +
                        cursor.getString(quantity) + "\t" +
                        cursor.getString(price) + "\t" +
                        cursor.getString(status) + "\t");
            }
            while(cursor.moveToNext());

            arrayAdapter.notifyDataSetChanged();
            productsListView.invalidateViews();
        }

        productsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String pos = titles.get(i).toString();
                product tmp = products.get(i);
                Intent intent = new Intent(getApplicationContext(), ProductsEditDelete.class);
                intent.putExtra("id",  tmp.id);
                intent.putExtra("nam", tmp.name);
                intent.putExtra("des", tmp.description);
                intent.putExtra("cat", tmp.category);
                intent.putExtra("qua", tmp.quantity);
                intent.putExtra("pri", tmp.price);
                intent.putExtra("est", tmp.status);
                startActivity(intent);
            }
        });


    }
}