package com.aleksi.ristinolla;

import android.app.Activity;
import android.content.Intent;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Random;

public class CpuActivity extends Activity {

        Button takaisinnappula;
        Button uusipelinappula;
        TextView pelivuoro;
        int sinavoitot;
        int tietokonevoitot;
        boolean vuoro = false;
    TextView view;
        Thread thread;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_game);

            uusipelinappula = (Button) findViewById(R.id.uusi_peli_nappula);
            pelivuoro = (TextView) findViewById(R.id.peli_vuoro);
            takaisinnappula = (Button) findViewById(R.id.takaisin_nappula);
            pelivuoro.setText(getResources().getString(R.string.sina_vuoro));
            sinavoitot = 0;
            tietokonevoitot=0;
            view = (TextView) findViewById(R.id.otsikko);
            view.setText(getResources().getString(R.string.sina)+" "+Integer.toString(sinavoitot)+"   "+getResources().getString(R.string.tietokone)+" "+Integer.toString(tietokonevoitot));
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
            pelivuoro.setText(getResources().getString(R.string.sina_vuoro));

            vuoro = false;
        }
        public void takaisinValikkoon(View v){

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


                    if(voitonTarkistus()){
                        pelivuoro.setText(getResources().getString(R.string.sina_voitto));
                        nappulatPaallePois(false);
                        uusipelinappula.setVisibility(View.VISIBLE);
                        takaisinnappula.setVisibility(View.VISIBLE);
                        sinavoitot++;
                    }
                    else if(tasapeli()){
                        pelivuoro.setText(getResources().getString(R.string.tasapeli));
                        uusipelinappula.setVisibility(View.VISIBLE);
                        takaisinnappula.setVisibility(View.VISIBLE);
                    }
                    else {
                        pelivuoro.setText(getResources().getString(R.string.sina_vuoro));

                        vuoro = true;
                    }
                }
                if(vuoro){
                    cpuSiirto();

                    if(voitonTarkistus()){
                        pelivuoro.setText(getResources().getString(R.string.tietokone_voitto));
                        nappulatPaallePois(false);
                        uusipelinappula.setVisibility(View.VISIBLE);
                        takaisinnappula.setVisibility(View.VISIBLE);
                        tietokonevoitot++;
                    }
                    else if(tasapeli()){
                        pelivuoro.setText(getResources().getString(R.string.tasapeli));
                        uusipelinappula.setVisibility(View.VISIBLE);
                        takaisinnappula.setVisibility(View.VISIBLE);
                    }
                    else{
                        pelivuoro.setText(getResources().getString(R.string.sina_vuoro));
                        vuoro = false;
                    }
                }
            }
            view.setText(getResources().getString(R.string.sina)+" "+Integer.toString(sinavoitot)+"   "+getResources().getString(R.string.tietokone)+" "+Integer.toString(tietokonevoitot));

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
        public void cpuSiirto() {

            TableLayout layout = (TableLayout) findViewById(R.id.tableLayout);
            //katsotaan onko pelaaja voittamassa, jos luku on 2 pelaajalla on 2 merkkiä samalla rivillä
            boolean siirtotehty = false;

            //x-suuntaiset tarkistukset
            for (int kerta = 0; kerta < 2; kerta++) {
                int lukuvastus = 0;
                int lukuoma = 0;
                for (int i = 0; i < layout.getChildCount(); i++) {
                    for (int j = 0; j < 3; j++) {
                        if (((Button) ((TableRow) layout.getChildAt(i)).getChildAt(j)).getText().equals(getResources().getString(R.string.risti))) {
                            lukuvastus++;
                        }
                        if (((Button) ((TableRow) layout.getChildAt(i)).getChildAt(j)).getText().equals(getResources().getString(R.string.nolla))) {
                            lukuoma++;
                        }
                        if (lukuoma >= 2 && !siirtotehty) {
                            for (int k = 0; k < 3; k++) {
                                if (((Button) ((TableRow) layout.getChildAt(i)).getChildAt(k)).getText().equals(getResources().getString(R.string.alkuarvo)) && !siirtotehty) {
                                    ((Button) ((TableRow) layout.getChildAt(i)).getChildAt(k)).setText(getResources().getString(R.string.nolla));
                                    ((Button) ((TableRow) layout.getChildAt(i)).getChildAt(k)).setEnabled(false);
                                    siirtotehty = true;


                                    break;
                                }
                            }
                            break;
                        } else if (lukuvastus >= 2 && !siirtotehty &&kerta==1) {
                            for (int k = 0; k < 3; k++) {
                                if (((Button) ((TableRow) layout.getChildAt(i)).getChildAt(k)).getText().equals(getResources().getString(R.string.alkuarvo)) && !siirtotehty) {
                                    ((Button) ((TableRow) layout.getChildAt(i)).getChildAt(k)).setText(getResources().getString(R.string.nolla));
                                    ((Button) ((TableRow) layout.getChildAt(i)).getChildAt(k)).setEnabled(false);
                                    siirtotehty = true;


                                    break;
                                }
                            }
                            break;
                        }

                    }
                    lukuoma = 0;
                    lukuvastus = 0;
                }

                lukuoma = 0;
                lukuvastus = 0;
                //y-suuntaiset tarkistukset
                for (int i = 0; i < layout.getChildCount(); i++) {
                    for (int j = 0; j < 3; j++) {
                        if (((Button) ((TableRow) layout.getChildAt(j)).getChildAt(i)).getText().equals(getResources().getString(R.string.risti))) {
                            lukuvastus++;
                        }
                        if (((Button) ((TableRow) layout.getChildAt(j)).getChildAt(i)).getText().equals(getResources().getString(R.string.nolla))) {
                            lukuoma++;
                        }
                        if (lukuoma >= 2 && !siirtotehty) {
                            for (int k = 0; k < 3; k++) {
                                if (((Button) ((TableRow) layout.getChildAt(k)).getChildAt(i)).getText().equals(getResources().getString(R.string.alkuarvo)) && !siirtotehty) {
                                    ((Button) ((TableRow) layout.getChildAt(k)).getChildAt(i)).setText(getResources().getString(R.string.nolla));
                                    ((Button) ((TableRow) layout.getChildAt(k)).getChildAt(i)).setEnabled(false);
                                    siirtotehty = true;

                                    break;
                                }
                            }
                            break;
                        } else if (lukuvastus >= 2 && !siirtotehty &&kerta==1) {
                            for (int k = 0; k < 3; k++) {
                                if (((Button) ((TableRow) layout.getChildAt(k)).getChildAt(i)).getText().equals(getResources().getString(R.string.alkuarvo)) && !siirtotehty) {
                                    ((Button) ((TableRow) layout.getChildAt(k)).getChildAt(i)).setText(getResources().getString(R.string.nolla));
                                    ((Button) ((TableRow) layout.getChildAt(k)).getChildAt(i)).setEnabled(false);
                                    siirtotehty = true;

                                    break;
                                }
                            }
                            break;
                        }

                    }
                    lukuoma = 0;
                    lukuvastus = 0;

                }

                //vasemmasta yläkulmasta oikeaan alakulmaan
                lukuoma = 0;
                lukuvastus = 0;
                for (int i = 0; i < layout.getChildCount(); i++) {
                    for (int j = 0; j < 3; j++) {

                        if (j == i && ((Button) ((TableRow) layout.getChildAt(i)).getChildAt(j)).getText().equals(getResources().getString(R.string.risti))) {
                            lukuvastus++;
                        }
                        if (j == i && ((Button) ((TableRow) layout.getChildAt(i)).getChildAt(j)).getText().equals(getResources().getString(R.string.nolla))) {
                            lukuoma++;
                        }
                        if (lukuoma >= 2 && !siirtotehty) {
                            for(int l=0; l<3;l++) {
                            for (int k = 0; k < 3; k++) {
                                if (((Button) ((TableRow) layout.getChildAt(l)).getChildAt(k)).getText().equals(getResources().getString(R.string.alkuarvo)) && !siirtotehty && l==k) {
                                    ((Button) ((TableRow) layout.getChildAt(l)).getChildAt(k)).setText(getResources().getString(R.string.nolla));
                                    ((Button) ((TableRow) layout.getChildAt(l)).getChildAt(k)).setEnabled(false);
                                    siirtotehty = true;
                                    lukuoma = 0;
                                    lukuvastus = 0;
                                    break;
                                }
                            break;
                        }
                        }
                        }
                        else if (lukuvastus >= 2 && !siirtotehty &&kerta==1) {
                            for(int l=0; l<3;l++) {
                                for (int k = 0; k < 3; k++) {
                                    if (((Button) ((TableRow) layout.getChildAt(l)).getChildAt(k)).getText().equals(getResources().getString(R.string.alkuarvo)) && !siirtotehty && l == k) {
                                        ((Button) ((TableRow) layout.getChildAt(l)).getChildAt(k)).setText(getResources().getString(R.string.nolla));
                                        ((Button) ((TableRow) layout.getChildAt(l)).getChildAt(k)).setEnabled(false);
                                        siirtotehty = true;
                                        lukuoma = 0;
                                        lukuvastus = 0;
                                        break;
                                    }
                                }
                            }
                            break;
                        }

                    }
                }

                //oikeasta yläkulmasta vasempaan alakulmaan
                lukuoma = 0;
                lukuvastus = 0;
                for (int i = 0; i < layout.getChildCount(); i++) {
                    for (int j = 0; j < 3; j++) {

                        if (i + j == 2 && ((Button) ((TableRow) layout.getChildAt(i)).getChildAt(j)).getText().equals(getResources().getString(R.string.risti))) {
                            lukuvastus++;
                        }
                        if (i + j == 2 && ((Button) ((TableRow) layout.getChildAt(i)).getChildAt(j)).getText().equals(getResources().getString(R.string.nolla))) {
                            lukuoma++;
                        }
                        if (lukuoma >= 2 && !siirtotehty) {
                            for (int l = 0; l < 3; l++) {
                                for (int k = 0; k < 3; k++) {
                                    if (((Button) ((TableRow) layout.getChildAt(k)).getChildAt(l)).getText().equals(getResources().getString(R.string.alkuarvo)) && !siirtotehty && l + k == 2) {
                                        ((Button) ((TableRow) layout.getChildAt(k)).getChildAt(l)).setText(getResources().getString(R.string.nolla));
                                        ((Button) ((TableRow) layout.getChildAt(k)).getChildAt(l)).setEnabled(false);
                                        siirtotehty = true;
                                        lukuoma = 0;
                                        lukuvastus = 0;
                                        break;
                                    } else {
                                        break;
                                    }
                                }
                                break;
                            }
                        }else if (lukuvastus >= 2 && !siirtotehty &&kerta==1) {
                            for (int l = 0; l < 3; l++) {
                                for (int k = 0; k < 3; k++) {
                                    if (((Button) ((TableRow) layout.getChildAt(k)).getChildAt(l)).getText().equals(getResources().getString(R.string.alkuarvo)) && !siirtotehty && l + k == 2) {
                                        ((Button) ((TableRow) layout.getChildAt(k)).getChildAt(l)).setText(getResources().getString(R.string.nolla));
                                        ((Button) ((TableRow) layout.getChildAt(k)).getChildAt(l)).setEnabled(false);
                                        siirtotehty = true;
                                        lukuoma = 0;
                                        lukuvastus = 0;
                                        break;
                                    }
                                }

                            }
                        }
                    }
                }

                if (!siirtotehty&&kerta ==1) {
                    Random rnd = new Random();
                    while (true) {
                        int x = rnd.nextInt(3);
                        int y = rnd.nextInt(3);
                        if (((Button) ((TableRow) layout.getChildAt(x)).getChildAt(y)).getText().equals(getResources().getString(R.string.alkuarvo)) && !siirtotehty) {
                            ((Button) ((TableRow) layout.getChildAt(x)).getChildAt(y)).setText(getResources().getString(R.string.nolla));
                            ((Button) ((TableRow) layout.getChildAt(x)).getChildAt(y)).setEnabled(false);
                            siirtotehty = true;
                            break;
                        }
                    }
                }
            }
        }
    }




