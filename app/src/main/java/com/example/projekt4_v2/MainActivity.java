package com.example.projekt4_v2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    PaintView paintView;
    int kolor;
    Button redButton,orangeButton,blueButton,greenButton,saveButton,showButton;
    ImageButton cancelButton;

    private ArrayList<File> images;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        redButton= findViewById(R.id.redButton);
        greenButton=findViewById(R.id.greenButton);
        blueButton=findViewById(R.id.blueButton);
        orangeButton=findViewById(R.id.orangeButton);
        cancelButton=findViewById(R.id.deleteButton);
        saveButton=findViewById(R.id.saveButton);
        showButton=findViewById(R.id.showButton);

        paintView = findViewById(R.id.paintView);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        paintView.CreateCanvas(displayMetrics);

        redButton.setOnClickListener(view -> {
            kolor = Color.RED;
            paintView.setKolor(kolor);
        });

        orangeButton.setOnClickListener(view -> {
            kolor = Color.rgb(255,165,0);
            paintView.setKolor(kolor);
        });

        blueButton.setOnClickListener(view -> {
            kolor = Color.BLUE;
            paintView.setKolor(kolor);
        });

        greenButton.setOnClickListener(view -> {
            kolor = Color.GREEN;
            paintView.setKolor(kolor);
        });

        cancelButton.setOnClickListener(view -> {
            paintView.clear();
        });

        saveButton.setOnClickListener(view -> {
            if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestStoragePermission();
            }
            paintView.save();
        });

        showButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,AdditionalActivity.class);
            startActivity(intent);
        });


    }

    private void requestStoragePermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
        {
            new AlertDialog.Builder(this).setTitle("Udziel zgody").setMessage("Udziel zgody na użycie pamięci wewnętrznej").setPositiveButton("Zgoda", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }
            }).setNegativeButton("Nie udzielam zgody", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).create().show();
        }else{
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Udzielono dostępu", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, "Zabroniono dostępu", Toast.LENGTH_LONG).show();
            }
        }
    }

}