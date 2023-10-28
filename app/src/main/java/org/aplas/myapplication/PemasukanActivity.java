package org.aplas.myapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import android.app.DatePickerDialog;
import java.text.SimpleDateFormat;
import java.util.Locale;


public class PemasukanActivity extends AppCompatActivity {

    EditText editdate, editnom, editket;
    Button save, back;
    SqliteHelper DB = new SqliteHelper(this);
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter;

    private void showDateDialog(){
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                editdate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemasukan);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        editdate = findViewById(R.id.editTanggal);
        editnom = findViewById(R.id.editNominal);
        editket = findViewById(R.id.editKeterangan);
        save = findViewById(R.id.btnSave);
        back = findViewById(R.id.btnKembali);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editdate.getText().toString().equals("") || editnom.getText().toString().equals("") || editket.getText().toString().equals("")) {
                    Toast.makeText(PemasukanActivity.this, "All fields Required", Toast.LENGTH_SHORT).show();
                } else {
                    Integer jml = Integer.valueOf(editnom.getText().toString());
                    SqliteHelper dbacess = SqliteHelper.getInstance(PemasukanActivity.this);

                    boolean added = dbacess.insertTrans(editdate.getText().toString(), jml, "income", editket.getText().toString());

                    if (added) {
                        Toast.makeText(getApplicationContext(), "Successfully added", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(PemasukanActivity.this, BerandaActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed to add", Toast.LENGTH_LONG).show();
                    }
                }
            }
//                db.execSQL("insert into tbl_trans(id_trans, tglmasuk, nommasuk, ketmasuk) values('" +
//                        editdate.getText().toString() + "','" +
//                        editnom.getText().toString() + "','" +
//                        editket.getText().toString() + "')");
//                finish();

//                String nom = editnom.getText().toString();
//                String ket = editket.getText().toString();
//                String date = editdate.getText().toString();

//                if (TextUtils.isEmpty(date) || TextUtils.isEmpty(nom))
//                    Toast.makeText(PemasukanActivity.this, "All fields Required", Toast.LENGTH_SHORT).show();
//                else {
//                      Boolean insert = DB.insertDataTransIn(date,nom,ket);
//                            if (insert==true){
//                                Toast.makeText(PemasukanActivity.this, "Input Success", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(PemasukanActivity.this, BerandaActivity.class);
//                                startActivity(intent);
//                            }else{
//                                Toast.makeText(PemasukanActivity.this, "Input Failed", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
        });

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        editdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PemasukanActivity.this, BerandaActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
