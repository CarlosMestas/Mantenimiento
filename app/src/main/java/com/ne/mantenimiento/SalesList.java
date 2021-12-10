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

import com.ne.mantenimiento.Models.product;
import com.ne.mantenimiento.Models.sale;

import java.util.ArrayList;

public class SalesList extends AppCompatActivity {

    private ListView salesListView;
    private ArrayList<String> titles;
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_list);

        salesListView = findViewById(R.id.salesListView);
        titles = new ArrayList<String>();

        SQLiteDatabase db = openOrCreateDatabase("ne", Context.MODE_PRIVATE, null);
        final Cursor cursor = db.rawQuery("SELECT * FROM sale",null);

        int id = cursor.getColumnIndex("id");
        int customer = cursor.getColumnIndex("salCus");
        int product = cursor.getColumnIndex("salPro");
        int quantity = cursor.getColumnIndex("salQua");
        int priceU = cursor.getColumnIndex("salPriU");
        int priceT = cursor.getColumnIndex("salPriT");
        int status = cursor.getColumnIndex("salEst");

        titles.clear();
        arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, titles);
        salesListView.setAdapter(arrayAdapter);

        final ArrayList<sale> sales = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                sale tmp = new sale();
                tmp.id = cursor.getString(id);
                tmp.customer = cursor.getString(customer);
                tmp.product = cursor.getString(product);
                tmp.quantity = cursor.getString(quantity);
                tmp.priceU = cursor.getString(priceU);
                tmp.priceT = cursor.getString(priceT);
                tmp.status = cursor.getString(status);

                sales.add(tmp);

                titles.add(cursor.getString(id) + "\t" +
                        cursor.getString(customer) + "\t" +
                        cursor.getString(product) + "\t" +
                        cursor.getString(quantity) + "\t" +
                        cursor.getString(priceT) + "\t" +
                        cursor.getString(status) + "\t");
            }
            while(cursor.moveToNext());

            arrayAdapter.notifyDataSetChanged();
            salesListView.invalidateViews();
        }


        salesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String pos = titles.get(i).toString();
                sale tmp = sales.get(i);
                Intent intent = new Intent(getApplicationContext(), SalesEditDelete.class);
                intent.putExtra("id",  tmp.id);
                intent.putExtra("cus", tmp.customer);
                intent.putExtra("pro", tmp.product);
                intent.putExtra("qua", tmp.quantity);
                intent.putExtra("priU", tmp.priceU);
                intent.putExtra("priT", tmp.priceT);
                intent.putExtra("est", tmp.status);
                startActivity(intent);
            }
        });

    }
}