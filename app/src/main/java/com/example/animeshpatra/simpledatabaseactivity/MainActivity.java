package com.example.animeshpatra.simpledatabaseactivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.os.Build.ID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_name, et_address;
    private Button btnAdd, btnShow;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CreateDatabase();
        et_name = (EditText) findViewById(R.id.et_name);
        et_address = (EditText) findViewById(R.id.et_address);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnShow = (Button) findViewById(R.id.btnShow);
        btnAdd.setOnClickListener(this);
        btnShow.setOnClickListener(this);
        
    }

    private void CreateDatabase() {
        db = openOrCreateDatabase("Student", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS student(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, Name VERCHAR, Address VERCHAR);");
    }

    @Override
    public void onClick(View v) {
        if (v == btnAdd){
            InsertIntoDB();
        }
        if (v == btnShow){
            Intent intent = new Intent(MainActivity.this, viewdata.class);
            startActivity(intent);
        }
    }

    private void InsertIntoDB() {
        String name = et_name.getText().toString().trim();
        String address = et_address.getText().toString().trim();
        if (name.equals("") || address.equals("")){
            Toast.makeText(MainActivity.this, "Please enter valid values.", Toast.LENGTH_LONG).show();
        }
        db.execSQL("INSERT INTO student (name, Address) VALUES ('"+name+"', '"+address+"');");
        Toast.makeText(MainActivity.this, "Successfully Entered", Toast.LENGTH_SHORT).show();
    }
}
