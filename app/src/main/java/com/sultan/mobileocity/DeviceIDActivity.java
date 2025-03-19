package com.sultan.mobileocity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class DeviceIDActivity extends AppCompatActivity {

    private String titles[];
    private String descriptions[];

    private static final int READ_PHONE_SATE_PERMISSION = 1;
    private TelephonyManager tm;
    private String imei, SimCardSerial, SimSubscriberID;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_id);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Mobile ID");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        String deviceid = Settings.Secure.getString(this.getContentResolver() , Settings.Secure.ANDROID_ID);
        tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED){
                String[] permissions = {Manifest.permission.READ_PHONE_STATE};
                requestPermissions(permissions, READ_PHONE_SATE_PERMISSION);
            }else{
                imei = tm.getDeviceId();
                SimCardSerial= tm.getSimSerialNumber();
                SimSubscriberID = tm.getSubscriberId();
            }
        }else{
            imei = tm.getDeviceId();
            SimCardSerial= tm.getSimSerialNumber();
            SimSubscriberID = tm.getSubscriberId();
        }

        String IPAddress = "No Internet Connection";
        ConnectivityManager connectivityManager =(ConnectivityManager)this.getSystemService(CONNECTIVITY_SERVICE);
        boolean is3GEnabled = false;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

            Network[] networks = connectivityManager.getAllNetworks();
            for(Network network : networks){
                NetworkInfo info = connectivityManager.getNetworkInfo(network);
                if(info != null){
                    if(info.getType() == connectivityManager.TYPE_MOBILE){
                        IPAddress = getMobileIPAddress();

                    }
                }
            }
        }else{
            NetworkInfo mMobile =connectivityManager.getNetworkInfo(connectivityManager.TYPE_MOBILE);
            if(mMobile != null){
                IPAddress = is3GEnabled + "";
            }
        }

        String wifiAddress = "No Wifi Connection";
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

            Network[] networks = connectivityManager.getAllNetworks();
            for(Network network : networks){
                NetworkInfo info = connectivityManager.getNetworkInfo(network);
                if(info != null){
                    if(info.getType() == connectivityManager.TYPE_WIFI){
                        wifiAddress = getWifiIPAddress();

                    }
                }
            }
        }else{
            NetworkInfo mMobile =connectivityManager.getNetworkInfo(connectivityManager.TYPE_WIFI);
            if(mMobile != null){
                wifiAddress = is3GEnabled + "";
            }
        }

        String bt = android.provider.Settings.Secure.getString(this.getContentResolver(),"bluetooth Address");

        titles = new String[]{"Android Device ID", "IMEI, MEID or ESN", "Hardware Serial Number", "SIM Card Serial No", "SIM Subscriber ID", "IP Address", "Wi-Fi Mac Address", "Bluetooth Mac Address", "Build FingerPrint"};
        descriptions = new String[]{deviceid, imei, Build.SERIAL, SimCardSerial, SimSubscriberID, IPAddress, wifiAddress, bt, Build.FINGERPRINT};

        ListView listView = findViewById(R.id.devIdList);

        MyAdapter adapter = new MyAdapter(this,titles,descriptions);
        listView.setAdapter(adapter);

    }

    private String getWifiIPAddress() {
        WifiManager wifimgr =(WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifimgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();

        return Formatter.formatIpAddress(ip);
    }

    private static String getMobileIPAddress() {
        try{

            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for(NetworkInterface intf:interfaces){
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for(InetAddress addr:addrs){
                    if(!addr.isLoopbackAddress()){
                        return addr.getHostAddress();
                    }

                }
            }
        }catch(Exception ex){

        }
        return "";
    }


    public class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String myTitles[];
        String myDescriptions[];

        MyAdapter(Context c, String[] titles, String[] descriptions) {
            super(c, R.layout.tworow, R.id.titleTv, titles);
            this.context = c;
            this.myTitles = titles;
            this.myDescriptions = descriptions;
        }

        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater= (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row= layoutInflater.inflate(R.layout.tworow,parent,false);
            TextView myTitle = row.findViewById(R.id.titleTv);
            TextView myDescr = row.findViewById(R.id.descTv);
            myTitle.setText(titles[position]);
            myDescr.setText(descriptions[position]);
            return row;
        }

    }

        @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case READ_PHONE_SATE_PERMISSION:
            {
                if(grantResults.length >=0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    recreate();
                    imei = tm.getDeviceId();
                    SimCardSerial= tm.getSimSerialNumber();
                    SimSubscriberID = tm.getSubscriberId();
                }
                else{
                    Toast.makeText(this,"Enable READ_PHONE_STATE Permissions from Settings",Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        menu.findItem(R.id.action_search).setVisible(false);
        return true;
    }
}