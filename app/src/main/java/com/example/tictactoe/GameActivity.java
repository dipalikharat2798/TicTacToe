package com.example.tictactoe;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tictactoe.databinding.ActivityGameBinding;

public class GameActivity extends AppCompatActivity  implements View.OnClickListener {
    ActivityGameBinding binding;
    TextView headerText;

    int PLAYER_O = 0;
    int PLAYER_X = 1;
    int j=0;

    int activePlayer = PLAYER_O;

    int[] filledPos = {-1, -1, -1, -1, -1, -1, -1, -1, -1};

    boolean isGameActive = true;
    public static int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        headerText = findViewById(R.id.header_text);
        headerText.setText("O turn");


        binding.btn0.setOnClickListener(this);
        binding.btn1.setOnClickListener(this);
        binding.btn2.setOnClickListener(this);
        binding.btn3.setOnClickListener(this);
        binding.btn4.setOnClickListener(this);
        binding.btn5.setOnClickListener(this);
        binding.btn6.setOnClickListener(this);
        binding.btn7.setOnClickListener(this);
        binding.btn8.setOnClickListener(this);


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        // logic for button press

        if (!isGameActive)
            return;

        Button clickedBtn = findViewById(v.getId());
        int clickedTag = Integer.parseInt(v.getTag().toString());

//        if (filledPos[clickedTag] != -1) {
//            return;
//        }

        if (filledPos[clickedTag] == -1) {
            // increase the counter
            // after every tap
            counter++;

            // check if its the last box
            if (counter == 9) {
                // reset the game
                isGameActive = false;
            }
            filledPos[clickedTag] = activePlayer;

            if (activePlayer == PLAYER_O) {
                clickedBtn.setText("O");
                activePlayer = PLAYER_X;
                headerText.setText("X turn");
            } else {
                clickedBtn.setText("X");
                activePlayer = PLAYER_O;
                headerText.setText("O turn");
            }

            checkForWin();


        }
    }
    private void checkForWin() {
        //we will check who is winner and show
        int[][] winningPos = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
        int flag = 0;
        for (int i = 0; i < 8; i++) {
            int val0 = winningPos[i][0];
            int val1 = winningPos[i][1];
            int val2 = winningPos[i][2];

            if (filledPos[val0] == filledPos[val1] && filledPos[val1] == filledPos[val2]) {
                if (filledPos[val0] != -1) {
                    //winner declare
                    flag = 1;
                    isGameActive = false;

                    if (filledPos[val0] == PLAYER_O)
                        showDialog("O is winner");
                    else
                        showDialog("X is winner");
                }
            }
        }
        if (counter == 9 && flag == 0) {
            showDialog("Match draw");
        }
    }

    private void showDialog(String winnerText) {
        new AlertDialog.Builder(this)
                .setTitle(winnerText)
                .setPositiveButton("Restart game", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        restartGame();
                    }
                })
                .show();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void restartGame() {
        activePlayer = PLAYER_O;
        headerText.setText("O turn");
        filledPos = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1};
        binding.btn0.setText("");
        binding.btn1.setText("");
        binding.btn2.setText("");
        binding.btn3.setText("");
        binding.btn4.setText("");
        binding.btn5.setText("");
        binding.btn6.setText("");
        binding.btn7.setText("");
        binding.btn8.setText("");

        isGameActive = true;
    }


}