package com.example.anurag.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class AddMorePeopleToMessage extends AppCompatActivity {

    private List<String> list;
    private ArrayAdapter<String> adapter;
    private ListView lvList;
    private DBAdapter dbAdapter;
    private String pNumbers;
    private String nickNames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_more_people_to_message);

        pNumbers=getIntent().getStringExtra("PhoneNumber");
        nickNames=getIntent().getStringExtra("NickName");

        lvList = (ListView) findViewById(R.id.lvList);
        dbAdapter=DBAdapter.getInstance(this);

        list = dbAdapter.getListOf("NikNme");
        adapter = new ArrayAdapter<String>(AddMorePeopleToMessage.this, R.layout.list_item, R.id.tvNkName, list);
        lvList.setAdapter(adapter);
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String nkName=adapter.getItem(position);
                String pnumber = dbAdapter.getDetOf(nkName).getMobileno();
                //Toast.makeText(AddMorePeopleToMessage.this, pnumber+nkName+"Cool Baby..", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddMorePeopleToMessage.this,MessageToQuery.class);
                intent.putExtra("PhoneNumber",pNumbers+", "+pnumber);
                intent.putExtra("NickName",nickNames+", "+nkName);
                startActivity(intent);
            }
        });
    }
}
