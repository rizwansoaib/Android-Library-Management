package com.nautanki.loginregapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class ScannedBarcodeActivty extends AppCompatActivity {


    SurfaceView surfaceView;
    TextView txtBarcodeValue;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    Button btnAction;
    String intentData = "";
    String btntype;
    boolean isEmail = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_scanned_barcode_activty);
        User user=new User(this);
        Intent intent=getIntent();
        btntype=intent.getStringExtra("btnvalue");

        initViews();
    }

    private void initViews() {
        txtBarcodeValue = findViewById(R.id.txtBarcodeValue);
        surfaceView = findViewById(R.id.surfaceView);
       // btnAction = findViewById(R.id.btnAction);


        /*btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (intentData.length() > 0) {
                    if (isEmail)
                        startActivity(new Intent(ScannedBarcodeActivity.this, EmailActivity.class).putExtra("email_address", intentData));
                    else {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(intentData)));
                    }
                }


            }
        });*/
    }

    private void initialiseDetectorsAndSources() {

        Toast.makeText(getApplicationContext(), "Barcode scanner started for "+btntype, Toast.LENGTH_LONG).show();

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(ScannedBarcodeActivty.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(ScannedBarcodeActivty.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {


            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {


                    txtBarcodeValue.post(new Runnable() {

                        @Override
                        public void run() {
/*
                            if (barcodes.valueAt(0).email != null) {
                                txtBarcodeValue.removeCallbacks(null);
                                intentData = barcodes.valueAt(0).email.address;
                                txtBarcodeValue.setText(intentData);
                                isEmail = true;
                                btnAction.setText("ADD CONTENT TO THE MAIL");
                            } else {*/
                            //isEmail = false;
                            // btnAction.setText("LAUNCH URL");
                            intentData = barcodes.valueAt(0).displayValue;


                            //}
                            if(btntype.equals("issue_book1")){

                                txtBarcodeValue.setText(intentData);
                                User user=new User(ScannedBarcodeActivty.this);
                                user.setBookId1(intentData);}

                            if(btntype.equals("return_book2")){

                                txtBarcodeValue.setText(intentData);
                                User user=new User(ScannedBarcodeActivty.this);
                                user.setBookId2(intentData);}

                            if(btntype.equals("issue_book3")){

                                txtBarcodeValue.setText(intentData);
                                User user=new User(ScannedBarcodeActivty.this);
                                user.setBookId3(intentData);}

                            if(btntype.equals("issue_book4")){

                                txtBarcodeValue.setText(intentData);
                                User user=new User(ScannedBarcodeActivty.this);
                                user.setBookId4(intentData);}



                            if(btntype.equals("issue_student")) {

                                txtBarcodeValue.setText(intentData);
                                User sharedPrefForIDs=new User(ScannedBarcodeActivty.this);
                                sharedPrefForIDs.setStudentId(intentData);
                            }


                            if(btntype.equals("return_student")) {

                                txtBarcodeValue.setText(intentData);
                                User sharedPrefForIDs=new User(ScannedBarcodeActivty.this);
                                sharedPrefForIDs.setStudentId(intentData);
                            }
                            //Toast.makeText(ScannedBarcodeActivity.this, intentData, Toast.LENGTH_SHORT).show();


                            finish();
                        }
                    });

                }
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.release();
        if(btntype.equals("issue_book1")){

            startActivity(new Intent(this,IssuePage.class));
            finish();
        }

        if(btntype.equals("issue_book2")){

            startActivity(new Intent(this,IssuePage.class));
            finish();
        }

        if(btntype.equals("issue_book3")){

            startActivity(new Intent(this,IssuePage.class));
            finish();
        }

        if(btntype.equals("issue_book4")){

            startActivity(new Intent(this,IssuePage.class));
            finish();
        }

        if(btntype.equals("issue_student")){

            startActivity(new Intent(this,IssuePage.class));
            finish();
        }

        if(btntype.equals("issue_book1")){

            startActivity(new Intent(this,IssuePage.class));
            finish();
        }

        if(btntype.equals("return_student")){

            startActivity(new Intent(this,LoginSuccess.class));
            finish();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        initialiseDetectorsAndSources();


    }

}
