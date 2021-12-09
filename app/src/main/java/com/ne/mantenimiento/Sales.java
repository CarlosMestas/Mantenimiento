package com.ne.mantenimiento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ne.mantenimiento.Models.category;
import com.ne.mantenimiento.Models.customer;
import com.ne.mantenimiento.Models.product;

import java.util.ArrayList;

public class Sales extends AppCompatActivity {

    private EditText editTextQuantity, editTextPriceU, editTextPriceT;
    private Spinner spinnerProducts, spinnerCustomers;
    private Button buttonAdd, buttonCancel;

    private ArrayList<String> titles01, titles02;
    private ArrayList<product> products;
    private ArrayList<customer> customers;

    private ArrayAdapter arrayAdapter01, arrayAdapter02;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        editTextQuantity = findViewById(R.id.salesEditTextQuantity);
        editTextPriceU = findViewById(R.id.salesEditTextPriceU);
        editTextPriceT = findViewById(R.id.salesEditTextPriceT);

        spinnerProducts = findViewById(R.id.salesSpinnerProducts);
        spinnerCustomers = findViewById(R.id.salesSpinnerCustomers);

        buttonAdd = findViewById(R.id.salesButtonAdd);
        buttonCancel = findViewById(R.id.salesButtonCancel);

        titles01 = new ArrayList<String>();
        titles02 = new ArrayList<String>();

        products = new ArrayList<>();
        customers = new ArrayList<>();


        SQLiteDatabase db = openOrCreateDatabase("ne", Context.MODE_PRIVATE, null);
        final Cursor cursor = db.rawQuery("SELECT * FROM product WHERE proEst = 'A'",null);

        int id = cursor.getColumnIndex("id");
        int name = cursor.getColumnIndex("proNam");
        int description = cursor.getColumnIndex("proDes");
        int category = cursor.getColumnIndex("proCat");
        int quantity = cursor.getColumnIndex("proQua");
        int price = cursor.getColumnIndex("proPri");
        int status = cursor.getColumnIndex("proEst");

        titles01.clear();
        arrayAdapter01 = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, titles01);
        spinnerProducts.setAdapter(arrayAdapter01);

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

                titles01.add(cursor.getString(name));
            }
            while(cursor.moveToNext());

            arrayAdapter01.notifyDataSetChanged();
        }

        SQLiteDatabase db2 = openOrCreateDatabase("ne", Context.MODE_PRIVATE, null);
        final Cursor cursor2 = db2.rawQuery("SELECT * FROM customer WHERE cusEst = 'A'",null);

        int idC = cursor2.getColumnIndex("id");
        int nameC = cursor2.getColumnIndex("cusNam");
        int dniC = cursor2.getColumnIndex("cusDni");
        int statusC = cursor2.getColumnIndex("cusEst");

        titles02.clear();
        arrayAdapter02 = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, titles02);
        spinnerCustomers.setAdapter(arrayAdapter02);

        if(cursor2.moveToFirst()){
            do{
                customer tmp = new customer();
                tmp.id = cursor2.getString(idC);
                tmp.name = cursor2.getString(nameC);
                tmp.dni = cursor2.getString(dniC);
                tmp.status = cursor2.getString(statusC);

                customers.add(tmp);

                titles02.add(cursor2.getString(nameC));
            }
            while(cursor2.moveToNext());

            arrayAdapter02.notifyDataSetChanged();
        }

        spinnerProducts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String priceUn = (String) products.get(i).price.toString();
                editTextPriceU.setText(priceUn);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(),"No hay selección",Toast.LENGTH_LONG).show();
            }
        });

        editTextQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(!editTextQuantity.getText().equals("")){
                    Double a, b;
                    a = Double.parseDouble(editTextQuantity.getText().toString());
                    b = Double.parseDouble(editTextPriceU.getText().toString());
                    Double t = a * b;
                    editTextPriceT.setText(t.toString());
                }
                else{
                    editTextPriceT.setText("0.0");
                }

            }
        });


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertSale();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sales.this, Menu.class);
                startActivity(intent);
            }
        });

    }

    private void insertSale() {
        try {
            String customer, product, quantity, priceU, priceT;

            customer = spinnerCustomers.getSelectedItem().toString();
            product = spinnerProducts.getSelectedItem().toString();

            quantity = editTextQuantity.getText().toString();
            priceU = editTextPriceU.getText().toString();
            priceT = editTextPriceT.getText().toString();


            SQLiteDatabase db = openOrCreateDatabase("ne", Context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS sale(id INTEGER PRIMARY KEY AUTOINCREMENT, salCus VARCHAR, salPro VARCHAR, salQua VARCHAR, salPriU VARCHAR, salPriT VARCHAR, salEst VARCHAR)");

            String sql = "insert into sale (salCus, salPro, salQua, salPriU, salPriT, salEst) values (?, ?, ?, ?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1, customer);
            statement.bindString(2, product);
            statement.bindString(3, quantity);
            statement.bindString(4, priceU);
            statement.bindString(5, priceT);
            statement.bindString(6, "A");
            statement.execute();
            Toast.makeText(this, "Venta agregada", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(Sales.this, Sales.class);
            startActivity(intent);

        }
        catch (Exception e){
            Toast.makeText(this, "Venta no agregado" + e.toString(), Toast.LENGTH_SHORT).show();

        }

    }
}