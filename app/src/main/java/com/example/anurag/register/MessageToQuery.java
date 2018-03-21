package com.example.anurag.register;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

public class MessageToQuery extends AppCompatActivity {

    private EditText etMTQMobileNo, etMTQMessage;
    private Button bMTQAdd, bMTQSend;
    private String MobileNo,NiName;
    private TextView tvMTQSendingTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_to_query);

        etMTQMessage = (EditText) findViewById(R.id.etMTQMessage);
        etMTQMobileNo = (EditText) findViewById(R.id.etMTQMobileNo);
        bMTQAdd = (Button) findViewById(R.id.bMTQAdd);
        bMTQSend = (Button) findViewById(R.id.bMTQSend);
        tvMTQSendingTo = (TextView) findViewById(R.id.tvMTQSendingTo);

        MobileNo = getIntent().getStringExtra("PhoneNumber");
        etMTQMobileNo.setText(MobileNo);
        NiName = getIntent().getStringExtra("NickName");
        tvMTQSendingTo.setText(NiName);

        bMTQAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MessageToQuery.this,AddMorePeopleToMessage.class);
                intent.putExtra("NickName",NiName);
                intent.putExtra("PhoneNumber",MobileNo);
                startActivity(intent);
            }
        });

        bMTQSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String mobileNo = etMTQMobileNo.getText().toString().trim();
                final String message = etMTQMessage.getText().toString().trim();

                if (mobileNo.isEmpty())
                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MessageToQuery.this);
                    alertDialogBuilder.setTitle("No Mobile Number");
                    alertDialogBuilder.setMessage("For your information no mobile number is set unable to send SMS pls go back and select mobile Number or set it Manually.");
                    alertDialogBuilder.setIcon(R.drawable.tacreg);
                    alertDialogBuilder.setCancelable(false);

                    alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                else if(message.isEmpty())
                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MessageToQuery.this);
                    alertDialogBuilder.setTitle("Blank Message");
                    alertDialogBuilder.setMessage("For your information no message is set are you sure want to send blank message.");
                    alertDialogBuilder.setIcon(R.drawable.tacreg);
                    alertDialogBuilder.setCancelable(false);

                    alertDialogBuilder.setPositiveButton("Send Empty Message", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            int i = 0;
                            while(mobileNo.length()>=i)
                            {
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(mobileNo.substring(i,i+10), null, message, null, null);
                                Toast.makeText(MessageToQuery.this, "SMS Sent to : "+mobileNo.substring(i,i+10), Toast.LENGTH_SHORT).show();
                                i+=12;
                            }
                        }
                    });

                    alertDialogBuilder.setNegativeButton("Write Message", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                else {

                    int i = 0;
                    while(mobileNo.length()>=i)
                    {
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(mobileNo.substring(i,i+10), null, message, null, null);
                        Toast.makeText(MessageToQuery.this, "SMS Sent to : "+mobileNo.substring(i,i+10), Toast.LENGTH_SHORT).show();
                        i+=12;
                    }
                    finish();
                }
            }
        });



    }
}
