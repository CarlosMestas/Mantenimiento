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

import com.ne.mantenimiento.Models.category;
import com.ne.mantenimiento.Models.customer;

import java.util.ArrayList;

public class CustomersList extends AppCompatActivity {

    private ListView customersListView;
    private ArrayList<String> titles;
    private ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers_list);

        customersListView = findViewById(R.id.customersListView);
        titles = new ArrayList<String>();

        SQLiteDatabase db = openOrCreateDatabase("ne", Context.MODE_PRIVATE, null);
        final Cursor cursor = db.rawQuery("SELECT * FROM customer",null);

        int id = cursor.getColumnIndex("id");
        int category = cursor.getColumnIndex("cusNam");
        int dni = cursor.getColumnIndex("cusDni");
        int status = cursor.getColumnIndex("cusEst");

        titles.clear();
        arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, titles);
        customersListView.setAdapter(arrayAdapter);

        final ArrayList<customer> customers = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                customer tmp = new customer();
                tmp.id = cursor.getString(id);
                tmp.name = cursor.getString(category);
                tmp.dni = cursor.getString(dni);
                tmp.status = cursor.getString(status);

                customers.add(tmp);

                titles.add(cursor.getString(id) + "\t" + cursor.getString(category) + "\t" + cursor.getString(dni) + "\t" + cursor.getString(status));
            }
            while(cursor.moveToNext());

            arrayAdapter.notifyDataSetChanged();
            customersListView.invalidateViews();
        }

        customersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String pos = titles.get(i).toString();
                customer tmp = customers.get(i);
                Intent intent = new Intent(getApplicationContext(), CustomersEditDelete.class);
                intent.putExtra("id",  tmp.id);
                intent.putExtra("nam", tmp.name);
                intent.putExtra("dni", tmp.dni);
                intent.putExtra("est", tmp.status);
                startActivity(intent);
            }
        });

    }
}