package com.sultan.mobileocity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.text.NumberFormat;

public class MemoryActivity extends AppCompatActivity {

    TextView mTvTotalRam, mTvUsedRam, mTvFreeRam;
    TextView mTvTotalRom, mTvUsedRom, mTvFreeRom;
    TextView mTvTotalHeap;
    TextView mTvPercRam,mTvPercRom;
    ProgressBar mPBRAM,mPBROM;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Memory");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        mTvFreeRam = findViewById(R.id.freeRam);
        mTvUsedRam = findViewById(R.id.usedRam);
        mTvTotalRam = findViewById(R.id.TotalRam);
        mTvUsedRom = findViewById(R.id.usedRom);
        mTvFreeRom = findViewById(R.id.freeRom);
        mTvTotalRom = findViewById(R.id.TotalRom);
        mTvTotalHeap = findViewById(R.id.totalHeap);

        mPBROM = findViewById(R.id.pbRom);
        mPBRAM = findViewById(R.id.pbRam);
        mTvPercRam = findViewById(R.id.tv_perc_ram);
        mTvPercRom = findViewById(R.id.tv_perc_rom);

        ActivityManager activityManager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        float totalMem = memoryInfo.totalMem/(1024*1024);
        float freeMem = memoryInfo.availMem/(1024*1024);
        float usedMem = totalMem - freeMem;
        float freeMemPerc = freeMem/totalMem*100;
        float usedMemPerc = usedMem/totalMem*100;

        NumberFormat numFormFreePerc = NumberFormat.getNumberInstance();
        numFormFreePerc.setMinimumFractionDigits(1);
        numFormFreePerc.setMaximumFractionDigits(1);
        String mFreeMemPerc = numFormFreePerc.format(freeMemPerc);

        NumberFormat numFormUsedPerc = NumberFormat.getNumberInstance();
        numFormUsedPerc.setMinimumFractionDigits(1);
        numFormUsedPerc.setMaximumFractionDigits(1);
        String mUsedMemPerc = numFormFreePerc.format(usedMemPerc);

        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        float blocksize = stat.getBlockSize();
        float totalBlocks = stat.getBlockCount();
        float availableBlocks = stat.getAvailableBlocks();
        float totalROM = (totalBlocks*blocksize)/(1024*1024);
        float freeROM = (availableBlocks*blocksize)/(1024*1024);
        float usedROM = totalROM - freeROM;

        float freeRomPerc = (freeROM/totalROM)*100;
        float usedRomPerc = (usedROM/totalROM)*100;

        NumberFormat numFormatTotalRom = NumberFormat.getNumberInstance();
        numFormatTotalRom.setMinimumFractionDigits(1);
        numFormatTotalRom.setMaximumFractionDigits(1);
        String mTotalROM = numFormFreePerc.format(totalROM);

        NumberFormat numFormatFreeRom = NumberFormat.getNumberInstance();
        numFormatFreeRom.setMinimumFractionDigits(1);
        numFormatFreeRom.setMaximumFractionDigits(1);
        String mFreeROM = numFormFreePerc.format(freeROM);

        NumberFormat numFormatUsedRom = NumberFormat.getNumberInstance();
        numFormatUsedRom.setMinimumFractionDigits(1);
        numFormatUsedRom.setMaximumFractionDigits(1);
        String mUsedROM = numFormFreePerc.format(usedROM);

        NumberFormat numFormatFreeRomPerc = NumberFormat.getNumberInstance();
        numFormatFreeRomPerc.setMinimumFractionDigits(1);
        numFormatFreeRomPerc.setMaximumFractionDigits(1);
        String mFreeRomPerc = numFormFreePerc.format(freeRomPerc);

        NumberFormat numFormatUsedRomPerc = NumberFormat.getNumberInstance();
        numFormatUsedRomPerc.setMinimumFractionDigits(1);
        numFormatUsedRomPerc.setMaximumFractionDigits(1);
        String mUsedRomPerc = numFormFreePerc.format(usedRomPerc);

        mTvTotalRam.setText(" "+ totalMem+ "MB");
        mTvFreeRam.setText(" "+ freeMem+ "MB" +"("+ mFreeMemPerc +"%)");
        mTvUsedRam.setText(" "+ usedMem+ "MB" +"("+ mUsedMemPerc +"%)");

        mTvTotalRom.setText(" "+ mTotalROM+ "MB");
        mTvFreeRom.setText(" "+ mFreeROM+ "MB" +"("+ mFreeRomPerc +"%)");
        mTvUsedRom.setText(" "+ mUsedROM+ "MB" +"("+ mUsedRomPerc +"%)");

        Runtime rt = Runtime.getRuntime();
        long maxMemory = rt.maxMemory();

        mTvTotalHeap.setText(" " +maxMemory/(1024*1024)+ "MB");

        mTvPercRam.setText(mUsedMemPerc+"% Used");
        mPBRAM.setProgress((int)usedMemPerc);

        mTvPercRom.setText(mUsedRomPerc+"% Used");
        mPBROM.setProgress((int)((usedROM/totalROM)*100));
        
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}