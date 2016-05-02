package com.example.xiaohui.cs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class WeaponActivity extends AppCompatActivity {

    ListView itemType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weapon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //receive intent to get the message about category/type of item chosen
        String type = getIntent().getStringExtra("com.example.xiaohui.cs");
        setTitle(type);

        this.itemType = (ListView) findViewById(R.id.listView);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();


        List<String> quotes = databaseAccess.getQuotes("SELECT weapon_name FROM weapon WHERE type='" + type + "'");
        databaseAccess.close();
        itemType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = ((TextView)view).getText().toString();
                Intent i = new Intent(getApplicationContext(), SkinActivity.class);
                i.putExtra("com.example.xiaohui.cs", item);
                startActivity(i);
                Toast.makeText(getBaseContext(), item, Toast.LENGTH_SHORT).show();
            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, quotes);
        this.itemType.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
        }
        return true;
    }

}
