package com.ganeshkumar.lionortiger;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import java.util.Arrays;

import static android.graphics.Color.RED;

public class MainActivity extends AppCompatActivity {

    enum Player {

        TIGER, LION, NO

    }

    Player currentPlayer;
    String[] playerChoice = new String[9];
    int T=0,L=0;

    int [][] winner = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                       {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                       {0, 4, 8}, {2, 4, 6}};

    boolean gameover = false;
    private Button btn;
    private ImageView tappedImageView;
    private ImageView saved;
    private GridLayout mGridLayout;
    private TextView ST, SL;
    final private String TKEY = "tiger", LKEY = "lion", CPKEY ="Player", ARKEY="TAG",Go ="+";
    int count=0;
    int[] ID ={R.id.img1, R.id.img2, R.id.img3, R.id.img3, R.id.img4, R.id.img5, R.id.img6, R.id.img7, R.id.img8, R.id.img9};


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null) {alert();}
        for(int i = 0; i < 9;i++) playerChoice[i] = String.valueOf(Player.NO);
        btn = findViewById(R.id.button);
        ST = findViewById(R.id.tigerScr);
        SL = findViewById(R.id.lionScr);
        mGridLayout = findViewById(R.id.gridlayout);
        if(savedInstanceState != null ){
            T=savedInstanceState.getInt(TKEY);
            L=savedInstanceState.getInt(LKEY);
            gameover=savedInstanceState.getBoolean(Go);
            currentPlayer =(Player)savedInstanceState.getSerializable(CPKEY);
            playerChoice = savedInstanceState.getStringArray(ARKEY);
            SL.setText("LION :"+ L);
            ST.setText("TIGER :"+T);
            if(currentPlayer == Player.TIGER){
                ST.setBackgroundColor(RED);
                SL.setBackgroundResource(R.color.d);
            }else if(currentPlayer == Player.LION){
                SL.setBackgroundColor(RED);
                ST.setBackgroundResource(R.color.d);
            }
            for(String cr:playerChoice){

                if(cr.equals(String.valueOf(Player.TIGER))){
                    saved = findViewById(ID[count]);
                    saved.setImageResource(R.drawable.tiger);
                    saved.setAlpha(1f);
                    saved = null;
                    count++;
                }
                else if(cr.equals(String.valueOf(Player.LION))){
                    saved = findViewById(ID[count]);
                    saved.setImageResource(R.drawable.lion);
                    saved.setAlpha(1f);
                    saved = null;
                    count++;
                }
                else  if(cr.equals(String.valueOf(Player.NO))){
                    saved = findViewById(ID[count]);
                    saved.setImageResource(R.drawable.ic_baseline_radio_button_checked_24);
                    saved.setAlpha(0.5f);
                    saved = null;
                    count++;
                }
            }
            count = 0;
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ResetTheGame();

            }
        });

        }


    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    public void ImageViewIsTapped(View imageView) {

        tappedImageView = (ImageView) imageView;

        int tiTag = Integer.parseInt(tappedImageView.getTag().toString());

        if(playerChoice[tiTag].equals(String.valueOf(Player.NO)) && !gameover) {

            tappedImageView.setTranslationY(-2000);

            playerChoice[tiTag] = String.valueOf(currentPlayer);

            if (currentPlayer == Player.TIGER) {
                tappedImageView.setImageResource(R.drawable.tiger);
                currentPlayer = Player.LION;
                SL.setBackgroundColor(RED);
                ST.setBackgroundResource(R.color.d);
            } else if (currentPlayer == Player.LION) {
                tappedImageView.setImageResource(R.drawable.lion);
                currentPlayer = Player.TIGER;
                ST.setBackgroundColor(RED);
                SL.setBackgroundResource(R.color.d);
            }
            tappedImageView.animate().translationYBy(2000).alpha(1).rotation(1080).setDuration(100);

            for (int[] winnerColumns : winner) {
                if (playerChoice[winnerColumns[0]].equals(playerChoice[winnerColumns[1]])
                        && playerChoice[winnerColumns[1]].equals(playerChoice[winnerColumns[2]])
                        && !playerChoice[winnerColumns[0]].equals(String.valueOf(Player.NO))) {

                    gameover = true;
                    if (currentPlayer == Player.TIGER) {

                        L++;
                        SL.setText("LION :"+ L);
                        Toast.makeText(this, "LION is the winner", Toast.LENGTH_SHORT).show();

                    } else if (currentPlayer == Player.LION) {

                        T++;
                        ST.setText("TIGER :"+T);
                        Toast.makeText(this, "TIGER is the winner", Toast.LENGTH_SHORT).show();

                    }

                    btn.setVisibility(View.VISIBLE);

                }else if(!(Arrays.asList(playerChoice).contains(String.valueOf(Player.NO)))){
                    btn.setVisibility(View.VISIBLE);
                }

            }

                }
            }

            private void alert(){

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this)
                        .setTitle("TWO PLAYER GAME")
                        .setMessage("FIRST PLAYER,\nChoose an Animal below.")
                        .setIcon(R.drawable.ic_baseline_warning_24)
                        .setCancelable(false)
                        .setPositiveButton("TIGER", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                currentPlayer = Player.TIGER;
                                ST.setBackgroundColor(RED);
                                SL.setBackgroundResource(R.color.d);
                            }
                        })
                        .setNegativeButton("LION", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                currentPlayer = Player.LION;
                                SL.setBackgroundColor(RED);
                                ST.setBackgroundResource(R.color.d);
                            }
                        });
                alertDialog.create().show();

            }

    private void ResetTheGame(){

        for (int index = 0; index < mGridLayout.getChildCount(); index++){

            ImageView imageView = (ImageView)mGridLayout.getChildAt(index);
            imageView.setImageDrawable(null);
            imageView.setAlpha(0.5f);

        }
        currentPlayer = Player.TIGER;
        ST.setBackgroundColor(RED);
        SL.setBackgroundResource(R.color.d);
        for(int i = 0; i < 9;i++) playerChoice[i] = String.valueOf(Player.NO);
        gameover = false;
        btn.setVisibility(View.GONE);
        alert();

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(TKEY,T);
        outState.putInt(LKEY,L);
        outState.putSerializable(CPKEY, currentPlayer);
        outState.putStringArray(ARKEY,playerChoice);
        outState.putBoolean(Go,gameover);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}