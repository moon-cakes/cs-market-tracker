package com.example.xiaohui.cs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SkinActivity extends AppCompatActivity {

    ListView itemType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin);

        this.itemType = (ListView) findViewById(R.id.listView);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        //receive intent to match next screen view
        Intent i = getIntent();
        final String weaponName = i.getStringExtra("com.example.xiaohui.cs");

        List<String> quotes = databaseAccess.getQuotes("SELECT skin_name FROM skin WHERE weapon_name='" + weaponName + "'");
        databaseAccess.close();

        itemType.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String skin = ((TextView)view).getText().toString();
                Intent i = new Intent(getApplicationContext(), SkinInfoActivity.class);
                i.putExtra("skin", skin);
                i.putExtra("weaponName", weaponName);
                startActivity(i);
                Toast.makeText(getBaseContext(), skin, Toast.LENGTH_LONG).show();
            }
        });

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, quotes);
        this.itemType.setAdapter(adapter);
    }
}
