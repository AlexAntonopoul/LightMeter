package com.meter.alex.lightmeter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StartActivity extends Activity {
    TextView luxnow, luxsaved,luxmax,luxmin;
    SensorManager sMgr;
    Sensor light;
    float currentLux = 0;
    float maxLux=0;
    float minLux=300000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_layout);

        SharedPreferences mPrefs = getSharedPreferences("label", 0);
        boolean tutorial =mPrefs.getBoolean("tag2",false);

        luxnow = (TextView) findViewById(R.id.luxnow);
        luxsaved = (TextView) findViewById(R.id.luxsaved);
        luxmax = (TextView) findViewById(R.id.luxmax);
        luxmin = (TextView) findViewById(R.id.luxmin);

        if(!tutorial){
            Intent tut = new Intent(this,Tutorial.class);
            tut.putExtra("page","one");
            startActivityForResult(tut,2);
        }

        //light sensor service
        sMgr = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        light = sMgr.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (light == null){
            callNoSensrorDialog();
        }
        sMgr.registerListener(lightSensorEventListener,light,SensorManager.SENSOR_DELAY_NORMAL);
        }

    public void callNoSensrorDialog() {
        final Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.dialog);
        dialog.show();
        dialog.setCancelable(false);

        Button okb =(Button) dialog.findViewById(R.id.dialog_ok);

        okb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
                finish();
            }
        });
    }

    SensorEventListener lightSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if( sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT)
            {
                currentLux = sensorEvent.values[0];
                if (currentLux > maxLux)
                    maxLux = currentLux;
                if (currentLux < minLux)
                    minLux = currentLux;
            }
            luxmax.setText("Max Lux: \n"+maxLux);
            luxmin.setText("Min Lux: \n"+minLux);
            luxnow.setText("Current Lux Value: "+currentLux);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {


        }
    };


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_DOWN) {
                    luxsaved.setText("Saved Lux Value: \n" +currentLux);
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    luxsaved.setText("Saved Lux Value: \n" +currentLux);
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }


    public void exitApp(View view) {
        final Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.exit_dialog);
        dialog.show();
        dialog.setCancelable(true);

        Button okb =(Button) dialog.findViewById(R.id.dialog_ok);
        Button can =(Button) dialog.findViewById(R.id.dialog_cancel);

        okb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
                finish();
            }
        });

        can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
            }
        });

    }

    public void reset(View view) {

        maxLux=0;
        minLux=300000;

        luxsaved.setText("Saved Lux Value: \n0.0" );
        Toast.makeText(this, "Values Reseted", Toast.LENGTH_SHORT).show();

    }

    public void helptut(View view) {
        Intent tut = new Intent(this,Tutorial.class);
        tut.putExtra("page","one");
        startActivity(tut);
    }

    @Override
    public void onBackPressed() {
        final Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.exit_dialog);
        dialog.show();
        dialog.setCancelable(true);

        Button okb =(Button) dialog.findViewById(R.id.dialog_ok);
        Button can =(Button) dialog.findViewById(R.id.dialog_cancel);

        okb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
                finish();
            }
        });

        can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
            }
        });

    }
}
