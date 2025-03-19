package com.sultan.mobileocity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static android.os.SystemClock.uptimeMillis;
import static java.lang.System.getProperty;

public class GeneralActivity extends AppCompatActivity {

    private String titles[];
    private String descriptions[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("General Info");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        long miliSec = uptimeMillis();
                String upTime = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(miliSec),
                TimeUnit.MILLISECONDS.toHours(miliSec),
                TimeUnit.MILLISECONDS.toMinutes(miliSec)-
                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(miliSec)),
                TimeUnit.MILLISECONDS.toSeconds(miliSec)-
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(miliSec)));

               titles = new String[]{"Model", "Manufacturer", "Device", "Board", "Hardware", "Brand", "Android Version", "OS Name", "API Level", "Bootloader", "Build Number", "Radio Version", "Kernal", "Android Runtime", "UP Time"};
               descriptions = new String[]{Build.MODEL, Build.MANUFACTURER, Build.DEVICE, Build.BOARD, Build.HARDWARE, Build.BRAND, Build.VERSION.RELEASE, Build.VERSION_CODES.class.getFields()[Build.VERSION.SDK_INT].getName(), Build.VERSION.SDK_INT+"", Build.BOOTLOADER, Build.FINGERPRINT, Build.getRadioVersion(), getProperty("os.arch"), "ART"+getProperty("java.vm.version"), upTime};

               ListView list = findViewById(R.id.generalList);

               MyAdapter adapter = new MyAdapter(this,titles,descriptions);
               list.setAdapter(adapter);
    }

    public class MyAdapter extends ArrayAdapter<String>{
        Context context;
        String myTitles[];
        String myDescriptions[];

        MyAdapter(Context c, String[] titles, String[] descriptions){
            super(c, R.layout.tworow, R.id.title, titles);
            this.context =c;
            this.myTitles=titles;
            this.myDescriptions=descriptions;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater= (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row= layoutInflater.inflate(R.layout.tworow,parent,false);
            TextView myTitle = row.findViewById(R.id.titleTv);
            TextView myDescription = row.findViewById(R.id.descTv);
            myTitle.setText(titles[position]);
            myDescription.setText(descriptions[position]);
            return row;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        menu.findItem(R.id.action_search).setVisible(false);
        return true;
    }
}