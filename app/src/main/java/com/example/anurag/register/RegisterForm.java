package com.example.anurag.register;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class RegisterForm extends AppCompatActivity {

    private EditText etName, etNickName, etMobileNo;
    private Button bRegister, bShow;
    private DBAdapter dbAdapter;
    private AutoCompleteTextView actvCollageName;
    private List<String> list;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);

        dbAdapter = DBAdapter.getInstance(this);

        etName = (EditText) findViewById(R.id.etName);
        etNickName = (EditText) findViewById(R.id.etNickName);
        etMobileNo = (EditText) findViewById(R.id.etMobileNo);
        actvCollageName = (AutoCompleteTextView) findViewById(R.id.actvCollageName);

        list = dbAdapter.getListOf("CollageName");
        adapter = new ArrayAdapter<String>(RegisterForm.this, android.R.layout.select_dialog_item, list);

        actvCollageName.setThreshold(3);
        actvCollageName.setAdapter(adapter);

        bRegister = (Button) findViewById(R.id.bRegister);
        bShow = (Button) findViewById(R.id.bShow);
        int regCount = dbAdapter.countRegDetails();
        bShow.setText("Show All Records("+regCount+")");

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!(dbAdapter.checkMobileNoExist(etMobileNo.getText().toString().trim()))) {
                    long rowInserted = dbAdapter.insertIntoRegistrationDetails(etName.getText().toString().trim(), etNickName.getText().toString().trim(), etMobileNo.getText().toString().trim(), actvCollageName.getText().toString().trim());
                    if (rowInserted != -1) {
                        Toast.makeText(RegisterForm.this, "Inserted Successfully..", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(RegisterForm.this, "Something Wrong..", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                    intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE, etMobileNo.getText().toString().trim());
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK);
                    intent.putExtra(ContactsContract.Intents.Insert.NAME, etNickName.getText().toString().trim());
                    intent.putExtra(ContactsContract.Intents.Insert.COMPANY, actvCollageName.getText().toString().trim());
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(RegisterForm.this, "This Phone Number already exists in the database", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterForm.this,ShowDetails.class);
                startActivity(intent);
            }
        });
    }
}