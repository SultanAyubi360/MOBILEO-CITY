package com.sultan.mobileocity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SimActivity extends AppCompatActivity {

    private static final int READ_PHONE_STATE_CODE =1;
    private String titles[];
    private String descriptions[];
    ListView mListView;
    TelephonyManager tm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sim);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("SIM");
            actionBar.setSubtitle("SIM 1");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        mListView = findViewById(R.id.SimListView);

        tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED){
                String[] permissions = {Manifest.permission.READ_PHONE_STATE};
                requestPermissions(permissions, READ_PHONE_STATE_CODE);
            }else{
                getPhoneInfo();
            }
        }else{
            getPhoneInfo();
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case READ_PHONE_STATE_CODE:
            {
                if(grantResults.length >=0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    getPhoneInfo();

                }
                else{
                    Toast.makeText(this,"Enable READ_PHONE_STATE Permissions from Settings",Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void getPhoneInfo(){
        int ss = tm.getCallState();
        String simState = "";
        switch (ss){
            case TelephonyManager.SIM_STATE_ABSENT:
                simState ="Absent";
                break;
            case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                simState ="Network Locked";
                break;
            case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                simState = "PIN Required";
                break;
            case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                simState = "PUK Required";
                break;
            case TelephonyManager.SIM_STATE_READY:
                simState ="Ready";
                break;
            case TelephonyManager.SIM_STATE_UNKNOWN:
                simState ="Unknown";
                break;
            case TelephonyManager.SIM_STATE_CARD_IO_ERROR:
                simState = "Card IO Error";
                break;
            case TelephonyManager.SIM_STATE_CARD_RESTRICTED:
                simState = "Card Restricted";
                break;
            case TelephonyManager.SIM_STATE_PERM_DISABLED:
                simState = "Prem Disabled";
                break;
        }

        String serviceProvider = tm.getSimOperatorName();
        String mobOprName = tm.getNetworkOperatorName();
        String simID = tm.getSimSerialNumber();
        String imei = tm.getDeviceId();
        String tmSunscriberId = tm.getSubscriberId();
        @SuppressLint("MissingPermission") String softVersion = tm.getDeviceSoftwareVersion();
        String country = tm.getNetworkCountryIso();
        String mcc_mnc = tm.getSimOperator();
        @SuppressLint("MissingPermission") String voiceMailTag = tm.getVoiceMailAlphaTag();
        boolean roamingStatus = tm.isNetworkRoaming();


        titles = new String[]{
                "SIM State",
                "Service Provider",
                "Mobile Operator Name",
                "Integrated Circuit Card Indentifier(ICCID)",
                "Unique Device ID(IMEI)",
                "International Mobile Subscriber ID(IMSI)",
                "Device Software Version",
                "Mobile Country Code(MCC)",
                "Mobile Country Code(MCC) + Mobile Network Code(MCN)",
                "VoiceMail",
                "Roaming"
        };

        descriptions = new String[]{
                ""+simState,
                ""+serviceProvider,
                ""+mobOprName,
                ""+simID,
                ""+imei,
                ""+tmSunscriberId,
                ""+softVersion,
                ""+country,
                ""+mcc_mnc,
                ""+voiceMailTag,
                ""+roamingStatus
        };

        mListView.setAdapter(new MyAdapter(this,titles,descriptions));

    }



    public class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String myTitles[];
        String myDescriptions[];

        MyAdapter(Context c, String[] titles, String[] descriptions) {
            super(c, R.layout.tworow, R.id.title, titles);
            this.context = c;
            this.myTitles = titles;
            this.myDescriptions = descriptions;
        }

        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater= (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view= layoutInflater.inflate(R.layout.tworow,parent,false);
            TextView myTitleTv = view.findViewById(R.id.titleTv);
            TextView myDescrTv = view.findViewById(R.id.descTv);
            myTitleTv.setText(titles[position]);
            myDescrTv.setText(descriptions[position]);
            return view;
        }

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}