package org.aplas.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class BerandaActivity extends AppCompatActivity {
    ImageButton btnincome, btnexpense, btncashflow, btnsetting;
    TextView totalincome, totalexpense;
    SqliteHelper DB = new SqliteHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);

        btnincome = findViewById(R.id.imgbtnincome);
        btnexpense = findViewById(R.id.imgbtnexpense);
        btncashflow = findViewById(R.id.imgbtncashflow);
        btnsetting = findViewById(R.id.imgbtnsetting);
        totalincome = findViewById(R.id.jumlahPemasukan);
        totalexpense = findViewById(R.id.jumlahPengeluaran);

        getTotalIncome();
        getTotalExpense();

        btnincome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BerandaActivity.this, PemasukanActivity.class);
                startActivity(i);
            }
        });

        btnexpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BerandaActivity.this, PengeluaranActivity.class);
                startActivity(i);
            }
        });

        btncashflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BerandaActivity.this, CashflowActivity.class);
                startActivity(i);
            }
        });

        btnsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BerandaActivity.this, SettingActivity.class);
                startActivity(i);
            }
        });
    }

    private void getTotalIncome() {
        SqliteHelper db = SqliteHelper.getInstance(BerandaActivity.this);
        Cursor data = db.total("jumlah", "tb_trans", "flow = 'income'");

        if(data.getCount() == 0){
            totalincome.setText("Rp. 0.-");
        } else {
            while(data.moveToNext()){
                if(data.getString(0) != null) {
                    totalincome.setText("Rp. " + data.getString(0) + ".-");
                } else {
                    totalincome.setText("Rp. 0.-");
                }
            }
        }
    }

    private void getTotalExpense() {
        SqliteHelper db = SqliteHelper.getInstance(BerandaActivity.this);
        Cursor data = db.total("jumlah", "tb_trans", "flow = 'outcome'");

        if(data.getCount() == 0){
            totalincome.setText("Rp. 0.-");
        } else {
            while(data.moveToNext()){
                if(data.getString(0) != null) {
                    totalexpense.setText("Rp. " + data.getString(0) + ".-");
                } else {
                    totalexpense.setText("Rp. 0.-");
                }
            }
        }
    }
}
