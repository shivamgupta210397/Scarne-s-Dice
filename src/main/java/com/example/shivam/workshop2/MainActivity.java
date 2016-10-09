package com.example.shivam.workshop2;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity {

    public int userOverallScore=0;
    public int userTurnScore=0;
    public int computerOverallScore=0;
    public int computerTurnScore=0;
    public Random r;
    public int flag=0;  //Indicates whose turn is 0->user and 1->computer

  android.os.Handler timeHandler=new android.os.Handler();
    Runnable timerRunnable=new Runnable() {
        @Override
        public void run() {
            if(flag==1 && computerOverallScore<100 && userOverallScore<100) {
                timeHandler.postDelayed(timerRunnable,4000);
                computerTurn();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        r=new Random();
        Button roll=(Button)findViewById(R.id.roll);
        roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rollADice();
            }
        });

        Button hold=(Button)findViewById(R.id.hold);
        hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holdTurn();
            }
        });

        Button reset=(Button)findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTurn();
            }
        });
    }

    public void userTurn()
    {
        Button roll=(Button)findViewById(R.id.roll);
        Button hold=(Button)findViewById(R.id.hold);
        roll.setEnabled(true);
        hold.setEnabled(true);

        int userScore=r.nextInt(6)+1;                                                   //generate a random number
        ImageView imageView=(ImageView)findViewById(R.id.imageView);
        TextView turnScore=(TextView)findViewById(R.id.turn);
        switch (userScore) {
            case 1:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.dice1));
                userTurnScore = 0;                                                      //turns turnScore to 0
                turnScore.setText("Score: " + userTurnScore);
                flag = 1;                                                               //computer turn
                Toast.makeText(this, "Computer Turn", Toast.LENGTH_SHORT).show();
                timeHandler.postDelayed(timerRunnable,2000);                               //call to computer turn
                break;

            case 2:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.dice2));
                userTurnScore = userTurnScore + 2;
                turnScore.setText("Score: " + userTurnScore);
                break;

            case 3:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.dice3));
                userTurnScore = userTurnScore + 3;
                turnScore.setText("Score: " + userTurnScore);
                break;

            case 4:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.dice4));
                userTurnScore = userTurnScore + 4;
                turnScore.setText("Score: " + userTurnScore);
                break;

            case 5:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.dice5));
                userTurnScore = userTurnScore + 5;
                turnScore.setText("Score: " + userTurnScore);
                break;

            case 6:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.dice6));
                userTurnScore = userTurnScore + 6;
                turnScore.setText("Score: " + userTurnScore);
                break;

            default:
                break;
        }
    }

    public void computerTurn()
    {
        ImageView imageView=(ImageView)findViewById(R.id.imageView);
        TextView turnScore=(TextView)findViewById(R.id.turn);
        Button roll=(Button)findViewById(R.id.roll);
        Button hold=(Button)findViewById(R.id.hold);

        roll.setEnabled(false);                                                     //disable the roll button
        hold.setEnabled(false);                                                     //disable the hold button

        if(computerTurnScore<20 && flag==1)
        {
            int computerScore=r.nextInt(6)+1;                                       //generate a random number
            switch (computerScore) {
                case 1:
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.dice1));
                    computerTurnScore = 0;                                          //turns the turnScore to 0
                    turnScore.setText("Score: " + computerTurnScore);
                    flag = 0;                                                       //user turn
                    break;

                case 2:
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.dice2));
                    computerTurnScore = computerTurnScore + 2;
                    turnScore.setText("Score: " + computerTurnScore);
                    break;

                case 3:
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.dice3));
                    computerTurnScore = computerTurnScore + 3;
                    turnScore.setText("Score: " + computerTurnScore);
                    break;

                case 4:
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.dice4));
                    computerTurnScore = computerTurnScore + 4;
                    turnScore.setText("Score: " + computerTurnScore);
                    break;

                case 5:
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.dice5));
                    computerTurnScore = computerTurnScore + 5;
                    turnScore.setText("Score: " + computerTurnScore);
                    break;

                case 6:
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.dice6));
                    computerTurnScore = computerTurnScore + 6;
                    turnScore.setText("Score: " + computerTurnScore);
                    break;

                default:
                    break;
            }
        }
        if(flag==0 || computerTurnScore>=20){                   //if the computer turnScore is greater than 20 or dice turns up as 1
            flag=1;                                             //we want to hold turn of computer
            timeHandler.removeCallbacks(timerRunnable);
            holdTurn();
        }

    }

    public void rollADice() {
        if(flag==0) {                                           //User Turn
          userTurn();
        }
    }

   public void holdTurn()
   {
       Button roll=(Button)findViewById(R.id.roll);
       Button hold=(Button)findViewById(R.id.hold);
       TextView userScore=(TextView)findViewById(R.id.yourScore);
       TextView turnScore=(TextView)findViewById(R.id.turn);
       TextView computerScore=(TextView)findViewById(R.id.computerScore);

       if(flag==0) {                                                                 //user holdTurn
           userOverallScore=userOverallScore+userTurnScore;
           userTurnScore=0;
           userScore.setText("Your Score: " + userOverallScore);
           turnScore.setText("Score: " + userTurnScore);
           if(userOverallScore>=100){                                                //user wins
               roll.setEnabled(false);
               hold.setEnabled(false);
               timeHandler.removeCallbacks(timerRunnable);
               Toast.makeText(this,"You Win ",Toast.LENGTH_SHORT).show();
           }else {
               flag = 1;                                                            //change turn to computer
               Toast.makeText(this, "Computer Turn", Toast.LENGTH_SHORT).show();
               timeHandler.postDelayed(timerRunnable, 0);                           //call to computer turn
           }
       }
       else {                                                                       //computer holdTurn
           computerOverallScore=computerOverallScore+computerTurnScore;
           computerTurnScore=0;
           computerScore.setText("Computer Score: " + computerOverallScore);
           turnScore.setText("Score: " + computerTurnScore);
           if(computerOverallScore>=100){                                           //computer wins
               roll.setEnabled(false);
               hold.setEnabled(false);
               timeHandler.removeCallbacks(timerRunnable);
               Toast.makeText(this,"Computer Win ",Toast.LENGTH_SHORT).show();
           }else {
               flag = 0;                                                            //change turn to user
               Toast.makeText(this, "Your Turn", Toast.LENGTH_SHORT).show();
               roll.setEnabled(true);
               hold.setEnabled(true);
           }
       }
   }

    public void resetTurn()
    {
        userOverallScore=0;
        userTurnScore=0;
        computerTurnScore=0;
        computerOverallScore=0;
        flag=0;

        TextView userScore=(TextView)findViewById(R.id.yourScore);
        TextView computerScore=(TextView)findViewById(R.id.computerScore);
        TextView turnScore=(TextView)findViewById(R.id.turn);
        Button hold=(Button)findViewById(R.id.hold);
        Button roll=(Button)findViewById(R.id.roll);

        userScore.setText("Your Score: 0");
        computerScore.setText("Computer Score: 0");
        turnScore.setText("Score: 0");
        roll.setEnabled(true);
        hold.setEnabled(true);

        timeHandler.removeCallbacks(timerRunnable);
    }
}
