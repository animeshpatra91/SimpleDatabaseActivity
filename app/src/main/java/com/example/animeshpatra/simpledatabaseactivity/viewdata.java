package com.example.animeshpatra.simpledatabaseactivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class viewdata extends AppCompatActivity implements View.OnClickListener{
    private EditText et_name, et_address;
    private TextView tv_ID;
    private Button btnPrevious, btnUpdate, btnDelete, btnNext;
    SQLiteDatabase db;
    Cursor cr;
    private static final String QUERY = "SELECT * FROM Student";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdata);
        OpenDatabase();
        et_name = (EditText) findViewById(R.id.et_name);
        et_address = (EditText) findViewById(R.id.et_address);
        tv_ID = (TextView) findViewById(R.id.tv_ID);
        btnPrevious = (Button) findViewById(R.id.btnPrevious);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnPrevious.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        cr = db.rawQuery(QUERY, null);
        cr.moveToFirst();
        ShowRecords();
    }

    private void ShowRecords() {
        String ID = cr.getString(0);
        String name = cr.getString(1);
        String address = cr.getString(2);
        tv_ID.setText(ID);
        et_name.setText(name);
        et_address.setText(address);
    }

    private void OpenDatabase() {
        db =openOrCreateDatabase("Student", MODE_PRIVATE, null);
    }

    @Override
    public void onClick(View v) {
        if (v == btnPrevious){
            if (!cr.isFirst()){
                cr.moveToPrevious();
                ShowRecords();
            }
            Toast.makeText(viewdata.this, "Reached the first", Toast.LENGTH_LONG).show();
        }
        if (v == btnUpdate){
            String ID = tv_ID.getText().toString().trim();
            String name = et_name.getText().toString().trim();
            String address = et_address.getText().toString().trim();
            String SQL = "UPDATE Student SET Name='"+name+"', Address ='"+address+"' WHERE ID="+ID+";";
            if (name.equals("") || address.equals("")){
                Toast.makeText(viewdata.this, "Enter not null values", Toast.LENGTH_LONG).show();
            }
            db.execSQL(SQL);
            Toast.makeText(viewdata.this, "Value Entered successfully", Toast.LENGTH_LONG).show();
            cr.moveToPosition(Integer.parseInt(ID));
        }
        if (v == btnDelete){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setMessage("Delete the  data?");
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String ID = tv_ID.getText().toString().trim();
                    String SQL = "DELETE FROM Student WHERE ID ="+ID+";";
                    db.execSQL(SQL);
                    Toast.makeText(viewdata.this, "Deleted", Toast.LENGTH_LONG).show();
                    cr = db.rawQuery(QUERY, null);
                }
            });
            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog alert = alertDialog.create();
            alert.show();
        }
        if (v == btnNext){
            if (!cr.isLast()){
                cr.moveToNext();
            }
            ShowRecords();
        }
    }
}
