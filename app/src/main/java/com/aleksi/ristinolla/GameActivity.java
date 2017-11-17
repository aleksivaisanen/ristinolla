package com.aleksi.ristinolla;

import android.app.Activity;
import android.content.Intent;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class GameActivity extends Activity {
    Button takaisinnappula;
    Button uusipelinappula;
    TextView pelivuoro;
    TextView view;
    boolean vuoro = false;
    int pelaaja2voitot;
    int pelaaja1voitot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        uusipelinappula = (Button) findViewById(R.id.uusi_peli_nappula);
        pelivuoro = (TextView) findViewById(R.id.peli_vuoro);
        takaisinnappula = (Button) findViewById(R.id.takaisin_nappula);
        pelivuoro.setText(getResources().getString(R.string.peli_vuoro_1));
        pelaaja1voitot=0;
        pelaaja2voitot=0;
        view = (TextView) findViewById(R.id.otsikko);
        view.setText(getResources().getString(R.string.pelaaja_1)+" "+Integer.toString(pelaaja1voitot)+"   "+getResources().getString(R.string.pelaaja_2)+" "+Integer.toString(pelaaja2voitot));

        //puhdistaa ruudukon pelin alussa
        TableLayout tablelayout = (TableLayout) findViewById(R.id.tableLayout);
        for(int i = 0; i < tablelayout.getChildCount(); i++){
            TableRow tablerow = (TableRow) tablelayout.getChildAt(i);
            for(int j = 0; j < tablerow.getChildCount(); j++){
                Button pelinappula = (Button) tablerow.getChildAt(j);
                pelinappula.setText(getResources().getString(R.string.alkuarvo));
                pelinappula.getBackground().clearColorFilter();


            }

        }

    }

    public void uusiPeli(View v){

        TableLayout tablelayout = (TableLayout) findViewById(R.id.tableLayout);
        for(int i = 0; i < tablelayout.getChildCount(); i++){
            TableRow tablerow = (TableRow) tablelayout.getChildAt(i);
            for(int j = 0; j < tablerow.getChildCount(); j++){
                Button pelinappula = (Button) tablerow.getChildAt(j);
                pelinappula.setText(getResources().getString(R.string.alkuarvo));
                pelinappula.getBackground().clearColorFilter();


            }

        }
        nappulatPaallePois(true);
        Button uusinappi = (Button)v;
        uusinappi.setVisibility(View.INVISIBLE);
        takaisinnappula.setVisibility(View.INVISIBLE);

        pelivuoro.setText(getResources().getString(R.string.peli_vuoro_1));

        vuoro = false;
    }
    public void takaisinValikkoon(View v){
        pelaaja1voitot=0;
        pelaaja2voitot=0;
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();

    }
    //annetaan false jos halutaan nappulat pois päältä ja true jos ne halutaan päälle
    public void nappulatPaallePois(boolean arvo){
        TableLayout tablelayout = (TableLayout) findViewById(R.id.tableLayout);
        for(int i = 0; i < tablelayout.getChildCount(); i++){
            TableRow tablerow = (TableRow) tablelayout.getChildAt(i);
            for(int j = 0; j < tablerow.getChildCount(); j++){
                Button pelinappula = (Button) tablerow.getChildAt(j);
                pelinappula.setEnabled(arvo);


            }
        }
    }

    public void asetaPelimerkki(View v){
        //painettava nappula pelilaudalla
        Button nappula = (Button)v;


        if(nappula.getText().toString().equals(getResources().getString(R.string.alkuarvo))&&!voitonTarkistus()&&!tasapeli()){
            if(!vuoro){
                nappula.setText(getResources().getString(R.string.risti));
                nappula.setEnabled(false);
                vuoro = true;

                if(voitonTarkistus()){
                    pelivuoro.setText(getResources().getString(R.string.pelaaja_1_voitto));
                    nappulatPaallePois(false);
                    uusipelinappula.setVisibility(View.VISIBLE);
                    takaisinnappula.setVisibility(View.VISIBLE);
                    pelaaja1voitot++;

                }
                else if(tasapeli()){
                    pelivuoro.setText(getResources().getString(R.string.tasapeli));
                    uusipelinappula.setVisibility(View.VISIBLE);
                    takaisinnappula.setVisibility(View.VISIBLE);
                }
                else {
                    pelivuoro.setText(getResources().getString(R.string.peli_vuoro_2));
                }
            }
            else if(vuoro){

                nappula.setText(getResources().getString(R.string.nolla));
                nappula.setEnabled(false);
                vuoro = false;

                if(voitonTarkistus()){
                    pelivuoro.setText(getResources().getString(R.string.pelaaja_2_voitto));
                    nappulatPaallePois(false);
                    uusipelinappula.setVisibility(View.VISIBLE);
                    takaisinnappula.setVisibility(View.VISIBLE);
                    pelaaja2voitot++;
                }
                else if(tasapeli()){
                    pelivuoro.setText(getResources().getString(R.string.tasapeli));
                    uusipelinappula.setVisibility(View.VISIBLE);
                    takaisinnappula.setVisibility(View.VISIBLE);
                }
                else{
                    pelivuoro.setText(getResources().getString(R.string.peli_vuoro_1));
                }
            }
        }

        view.setText(getResources().getString(R.string.pelaaja_1)+" "+Integer.toString(pelaaja1voitot)+"   "+getResources().getString(R.string.pelaaja_2)+" "+Integer.toString(pelaaja2voitot));
    }
    public boolean tasapeli(){
        TableLayout layout =(TableLayout)findViewById(R.id.tableLayout);
        boolean tasa = true;
        for(int i = 0; i < layout.getChildCount(); i++){
            TableRow row = (TableRow) layout.getChildAt(i);
            for(int j = 0; j < 3; j++){
                    if(((Button)row.getChildAt(j)).getText().equals(getResources().getString(R.string.alkuarvo))){
                        tasa = false;
                    }
                }
            }

        return tasa;

    }
    public boolean voitonTarkistus(){
        TableLayout layout =(TableLayout)findViewById(R.id.tableLayout);


        for (int i = 0; i < layout.getChildCount(); i++) {

            TableRow row = (TableRow)layout.getChildAt(i);
            if(((Button)row.getChildAt(0)).getText().toString().equals(((Button)row.getChildAt(1)).getText().toString())&&((Button)row.getChildAt(0)).getText().toString().equals(((Button)row.getChildAt(2)).getText().toString())&&!((Button)row.getChildAt(0)).getText().toString().equals(getResources().getString(R.string.alkuarvo))){

                for(int j=0; j<row.getChildCount();j++){
                    ((Button)row.getChildAt(j)).getBackground().setColorFilter(new LightingColorFilter(0xFF0000, 0xFF0000));
                }
                return true;

            }
            else if(((Button)((TableRow)layout.getChildAt(0)).getChildAt(i)).getText().toString().equals(((Button)((TableRow)layout.getChildAt(1)).getChildAt(i)).getText().toString())&&((Button)((TableRow)layout.getChildAt(0)).getChildAt(i)).getText().toString().equals(((Button)((TableRow)layout.getChildAt(2)).getChildAt(i)).getText().toString())&&!((Button)((TableRow)layout.getChildAt(0)).getChildAt(i)).getText().toString().equals(getResources().getString(R.string.alkuarvo))){
                for(int j=0; j<row.getChildCount();j++){

                    ((Button)((TableRow)layout.getChildAt(j)).getChildAt(i)).getBackground().setColorFilter(new LightingColorFilter(0xFF0000, 0xFF0000));
                }
                return true;
            }
        }

        if(((Button)((TableRow)layout.getChildAt(0)).getChildAt(0)).getText().toString().equals(((Button)((TableRow)layout.getChildAt(1)).getChildAt(1)).getText().toString())&&((Button)((TableRow)layout.getChildAt(0)).getChildAt(0)).getText().toString().equals(((Button)((TableRow)layout.getChildAt(2)).getChildAt(2)).getText().toString())&&!((Button)((TableRow)layout.getChildAt(0)).getChildAt(0)).getText().toString().equals(getResources().getString(R.string.alkuarvo))){

                ((Button)((TableRow)layout.getChildAt(0)).getChildAt(0)).getBackground().setColorFilter(new LightingColorFilter(0xFF0000, 0xFF0000));
                ((Button)((TableRow)layout.getChildAt(1)).getChildAt(1)).getBackground().setColorFilter(new LightingColorFilter(0xFF0000, 0xFF0000));
                ((Button)((TableRow)layout.getChildAt(2)).getChildAt(2)).getBackground().setColorFilter(new LightingColorFilter(0xFF0000, 0xFF0000));
            return true;
        }


        if(((Button)((TableRow)layout.getChildAt(0)).getChildAt(2)).getText().toString().equals(((Button)((TableRow)layout.getChildAt(1)).getChildAt(1)).getText().toString())&&((Button)((TableRow)layout.getChildAt(0)).getChildAt(2)).getText().toString().equals(((Button)((TableRow)layout.getChildAt(2)).getChildAt(0)).getText().toString())&&!((Button)((TableRow)layout.getChildAt(0)).getChildAt(2)).getText().toString().equals(getResources().getString(R.string.alkuarvo))){
            ((Button)((TableRow)layout.getChildAt(0)).getChildAt(2)).getBackground().setColorFilter(new LightingColorFilter(0xFF0000, 0xFF0000));
            ((Button)((TableRow)layout.getChildAt(1)).getChildAt(1)).getBackground().setColorFilter(new LightingColorFilter(0xFF0000, 0xFF0000));
            ((Button)((TableRow)layout.getChildAt(2)).getChildAt(0)).getBackground().setColorFilter(new LightingColorFilter(0xFF0000, 0xFF0000));
            return true;
        }
        return false;
    }

    }


