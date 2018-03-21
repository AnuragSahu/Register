package com.example.anurag.register;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class ShowDetails extends AppCompatActivity {

    private List<String> list;
    private ArrayAdapter<String> adapter;
    private ListView lvList;
    private DBAdapter dbAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        lvList = (ListView) findViewById(R.id.lvList);
        dbAdapter=DBAdapter.getInstance(this);

        list = dbAdapter.getListOf("NikNme");
        adapter = new ArrayAdapter<String>(ShowDetails.this, R.layout.list_item, R.id.tvNkName, list);
        lvList.setAdapter(adapter);
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String nkName=adapter.getItem(position);
                Intent intent = new Intent(ShowDetails.this,ShowMoreDetails.class);
                intent.putExtra("nkName",nkName);
                startActivity(intent);
            }
        });



    }
}
