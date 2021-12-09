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

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CategoriasList extends AppCompatActivity {

    private ListView categoriasListView;
    private ArrayList<String> titles;
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias_list);

        categoriasListView = findViewById(R.id.categoriasListView);
        titles = new ArrayList<String>();

        SQLiteDatabase db = openOrCreateDatabase("ne", Context.MODE_PRIVATE, null);
        final Cursor cursor = db.rawQuery("SELECT * FROM category",null);

        int id = cursor.getColumnIndex("id");
        int category = cursor.getColumnIndex("catNam");
        int description = cursor.getColumnIndex("catDes");
        int status = cursor.getColumnIndex("catEst");

        titles.clear();
        arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, titles);
        categoriasListView.setAdapter(arrayAdapter);

        final ArrayList<category> categories = new ArrayList<category>();
        if(cursor.moveToFirst()){
            do{
                category tmp = new category();
                tmp.id = cursor.getString(id);
                tmp.name = cursor.getString(category);
                tmp.description = cursor.getString(description);
                tmp.status = cursor.getString(status);

                categories.add(tmp);

                titles.add(cursor.getString(id) + "\t" + cursor.getString(category) + "\t" + cursor.getString(description) + "\t" + cursor.getString(status));
            }
            while(cursor.moveToNext());

            arrayAdapter.notifyDataSetChanged();
            categoriasListView.invalidateViews();
        }

        categoriasListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String pos = titles.get(i).toString();
                category tmp = categories.get(i);
                Intent intent = new Intent(getApplicationContext(), CategoriasEditDelete.class);
                intent.putExtra("id",  tmp.id);
                intent.putExtra("nam", tmp.name);
                intent.putExtra("des", tmp.description);
                intent.putExtra("est", tmp.status);
                startActivity(intent);

            }
        });


    }
}