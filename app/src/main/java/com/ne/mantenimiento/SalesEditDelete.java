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

import com.ne.mantenimiento.Models.customer;
import com.ne.mantenimiento.Models.product;

import java.util.ArrayList;

public class SalesEditDelete extends AppCompatActivity {

    private EditText editTextId, editTextQuantity, editTextPriceU, editTextPriceT, editTextStatus;
    private Spinner spinnerProducts, spinnerCustomers;
    private Button buttonEdit, buttonDelete, buttonCancel;

    private ArrayList<String> titles01, titles02;
    private ArrayList<product> products;
    private ArrayList<customer> customers;

    private ArrayAdapter arrayAdapter01, arrayAdapter02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_edit_delete);

        editTextId = findViewById(R.id.salesEDEditTextId);
        editTextQuantity = findViewById(R.id.salesEDEditTextQuantity);
        editTextPriceU = findViewById(R.id.salesEDEditTextPriceU);
        editTextPriceT = findViewById(R.id.salesEDEditTextPriceT);
        editTextStatus = findViewById(R.id.salesEDEditTextStatus);

        spinnerProducts = findViewById(R.id.salesEDSpinnerProducts);
        spinnerCustomers = findViewById(R.id.salesEDSpinnerCustomers);

        buttonEdit = findViewById(R.id.salesEDButtonEdit);
        buttonDelete = findViewById(R.id.salesEDButtonDelete);
        buttonCancel = findViewById(R.id.salesEDButtonCancel);

        titles01 = new ArrayList<String>();
        titles02 = new ArrayList<String>();

        products = new ArrayList<>();
        customers = new ArrayList<>();

        Intent intent = getIntent();

        String idI  = intent.getStringExtra("id").toString();
        String cusI = intent.getStringExtra("cus").toString();
        String proI = intent.getStringExtra("pro").toString();
        String quaI = intent.getStringExtra("qua").toString();
        String priUI = intent.getStringExtra("priU").toString();
        String priTI = intent.getStringExtra("priT").toString();
        String estI = intent.getStringExtra("est").toString();

        int posA = 0;
        int pA = 0;

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

                if(proI.equals(cursor.getString(name))){
                    posA = pA;
                }

                titles01.add(cursor.getString(name));
                pA++;
            }
            while(cursor.moveToNext());

            arrayAdapter01.notifyDataSetChanged();
        }

        int posB = 0;
        int pB = 0;

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

                if(cusI.equals(cursor2.getString(nameC))){
                    posB = pB;
                }

                titles02.add(cursor2.getString(nameC));
                pB++;
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


        editTextId.setText(idI);
        editTextQuantity.setText(quaI);
        editTextPriceU.setText(priUI);
        editTextPriceT.setText(priTI);
        editTextStatus.setText(estI);

        spinnerProducts.setSelection(posA);
        spinnerCustomers.setSelection(posB);

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

        editTextPriceU.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(!editTextPriceU.getText().equals("")){
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

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editSale();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSale();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SalesEditDelete.this, Menu.class);
                startActivity(intent);
            }
        });
    }

    private void editSale() {
        try {
            String idE, productE, customerE, quantityE, priceUE, priceTE, statusE;

            idE = editTextId.getText().toString();
            productE = spinnerProducts.getSelectedItem().toString();
            customerE = spinnerCustomers.getSelectedItem().toString();
            quantityE = editTextQuantity.getText().toString();
            priceUE = editTextPriceU.getText().toString();
            priceTE = editTextPriceT.getText().toString();
            statusE = editTextStatus.getText().toString();

            SQLiteDatabase db = openOrCreateDatabase("ne", Context.MODE_PRIVATE, null);

            String sql = "update sale set salCus = ?, salPro = ?, salQua = ?, salPriU = ?, salPriT = ?, salEst = ? where id = ?";

            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1, customerE);
            statement.bindString(2, productE);
            statement.bindString(3, quantityE);
            statement.bindString(4, priceUE);
            statement.bindString(5, priceTE);
            statement.bindString(6, statusE);
            statement.bindString(7, idE);

            statement.execute();
            Toast.makeText(this, "Venta modificado", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), Menu.class);
            startActivity(intent);

        }
        catch (Exception e){
            Toast.makeText(this, "Venta no modificada", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteSale() {
        try {
            String idE, productE, customerE, quantityE, priceUE, priceTE, statusE;

            idE = editTextId.getText().toString();
            productE = spinnerProducts.getSelectedItem().toString();
            customerE = spinnerCustomers.getSelectedItem().toString();
            quantityE = editTextQuantity.toString();
            priceUE = editTextPriceU.getText().toString();
            priceTE = editTextPriceT.getText().toString();
            statusE = editTextStatus.getText().toString();

            SQLiteDatabase db = openOrCreateDatabase("ne", Context.MODE_PRIVATE, null);

            String sql = "update sale set salCus = ?, salPro = ?, salQua = ?, salPriU = ?, salPriT = ?, salEst = ? where id = ?";

            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1, customerE);
            statement.bindString(2, productE);
            statement.bindString(3, quantityE);
            statement.bindString(4, priceUE);
            statement.bindString(5, priceTE);
            statement.bindString(6, "*");
            statement.bindString(7, idE);

            statement.execute();
            Toast.makeText(this, "Venta eliminada", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), Menu.class);
            startActivity(intent);

        }
        catch (Exception e){
            Toast.makeText(this, "Venta no eliminada", Toast.LENGTH_SHORT).show();
        }
    }

}