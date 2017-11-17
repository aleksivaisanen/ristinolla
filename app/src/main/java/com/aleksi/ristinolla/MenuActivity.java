package com.aleksi.ristinolla;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_menu_activity);
    }
    public void startGame(View v){
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
    public void startCpuGame(View v){
        Intent intent = new Intent(this, CpuActivity.class);
        startActivity(intent);
    }

}
