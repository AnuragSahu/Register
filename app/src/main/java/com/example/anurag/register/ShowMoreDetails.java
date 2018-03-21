package com.example.anurag.register;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ShowMoreDetails extends AppCompatActivity {

    private RegisterTable registerTable;
    private DBAdapter dbAdapter;
    private TextView tvName, tvNickName, tvMobileNo, tvCollage;
    private Button bCall, bMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_more_details);

         String nkName = getIntent().getStringExtra("nkName");


        tvName = (TextView) findViewById(R.id.tvSMDName);
        tvNickName = (TextView) findViewById(R.id.tvSMDNickName);
        tvMobileNo = (TextView) findViewById(R.id.tvSMDMobileNo);
        tvCollage = (TextView) findViewById(R.id.tvSMDCollage);

        dbAdapter = DBAdapter.getInstance(this);
        registerTable = dbAdapter.getDetOf(nkName);
        tvName.setText(registerTable.getName());
        tvNickName.setText(registerTable.getNickName());
        tvMobileNo.setText(registerTable.getMobileno());
        tvCollage.setText(registerTable.getCollage());

        bCall = (Button) findViewById(R.id.bCall);
        bMessage = (Button) findViewById(R.id.bMessage);

        bCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + registerTable.getMobileno()));
                if (ActivityCompat.checkSelfPermission(ShowMoreDetails.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);
            }
        });

        bMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowMoreDetails.this,MessageToQuery.class);
                intent.putExtra("PhoneNumber",registerTable.getMobileno());
                intent.putExtra("NickName",registerTable.getNickName());
                startActivity(intent);
            }
        });


    }
}
