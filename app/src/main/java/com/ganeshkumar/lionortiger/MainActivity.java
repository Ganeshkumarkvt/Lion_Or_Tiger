package com.ganeshkumar.lionortiger;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    enum Player {

        TIGER, LION, NO

    }

    Player currentPlayer = Player.TIGER;
    Player[] playerChoice = new Player[9];
    int T=0,L=0;

    int [][] winner = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                       {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                       {0, 4, 8}, {2, 4, 6}};

    boolean gameover = false;
    private Button btn;
    private GridLayout mGridLayout;
    private TextView ST, SL;
    private AlertDialog.Builder mAlertDialog;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alert();
        for(int i = 0; i < 9;i++) playerChoice[i] = Player.NO;

        btn = findViewById(R.id.button);
        ST = findViewById(R.id.tigerScr);
        SL = findViewById(R.id.lionScr);
        mGridLayout = findViewById(R.id.gridlayout);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ResetTheGame();

            }
        });

        }


    public void ImageViewIsTapped(View imageView) {

        ImageView tappedImageView = (ImageView) imageView;

        int tiTag = Integer.parseInt(tappedImageView.getTag().toString());

        if(playerChoice[tiTag] == Player.NO && gameover == false) {

            tappedImageView.setTranslationY(-2000);

            playerChoice[tiTag] = currentPlayer;

            if (currentPlayer == Player.TIGER) {
                tappedImageView.setImageResource(R.drawable.tiger);
                currentPlayer = Player.LION;
            } else if (currentPlayer == Player.LION) {
                tappedImageView.setImageResource(R.drawable.lion);
                currentPlayer = Player.TIGER;
            }
            tappedImageView.animate().translationYBy(2000).alpha(1).rotation(1080).setDuration(500);

            for (int[] winnerColumns : winner) {
                if (playerChoice[winnerColumns[0]] == playerChoice[winnerColumns[1]]
                        && playerChoice[winnerColumns[1]] == playerChoice[winnerColumns[2]]
                        && playerChoice[winnerColumns[0]] != Player.NO) {

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

                }else if(!(Arrays.asList(playerChoice).contains(Player.NO))){
                    btn.setVisibility(View.VISIBLE);
                }

            }

                }
            }

            private void alert(){

                mAlertDialog = new AlertDialog.Builder(this)
                        .setTitle("WARNING")
                        .setMessage("FIRST PLAYER must play with TIGER\nSECOND PLAYER play with LION")
                        .setIcon(R.drawable.ic_baseline_warning_24)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                mAlertDialog.create().show();

            }

    private void ResetTheGame(){

        for (int index = 0; index < mGridLayout.getChildCount(); index++){

            ImageView imageView = (ImageView)mGridLayout.getChildAt(index);
            imageView.setImageDrawable(null);
            imageView.setAlpha(0.5f);

        }
        currentPlayer = Player.TIGER;
        for(int i = 0; i < 9;i++) playerChoice[i] = Player.NO;
        gameover = false;
        btn.setVisibility(View.GONE);

        alert();

    }


}