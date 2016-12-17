package com.example.alex.photometer;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private static final int REQUEST_CAMERA_RESULT = 1;
   TextView luxnow, luxpressed;
    SensorManager sMgr;
    Sensor light;
    float currentLux = 0;
    float maxLux;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        luxnow = (TextView) findViewById(R.id.luxnow);
        luxpressed = (TextView) findViewById(R.id.luxpressed);


        sMgr = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

        light = sMgr.getDefaultSensor(Sensor.TYPE_LIGHT);





        sMgr.registerListener(lightSensorEventListener,light,SensorManager.SENSOR_DELAY_NORMAL);
        }

    SensorEventListener lightSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if( sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT)
            {
                currentLux = sensorEvent.values[0];
                if (currentLux > maxLux)
                    maxLux = currentLux;
            }
            luxnow.setText(currentLux+" Lux");
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
                    luxpressed.setText(currentLux+" Lux");
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    luxpressed.setText(currentLux+" Lux");
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }


}
