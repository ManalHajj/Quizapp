package com.example.quizappdmrd;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class activity_score extends AppCompatActivity {
    private List<QuestionsList> questionsLists=new ArrayList<>();
    private int score;
    private TextView scorePourcentage;
    private Button bRetry, bShare;
    private app.futured.donut.DonutProgressView donut;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        scorePourcentage=findViewById(R.id.scorePourcentage);
        bRetry = (Button) findViewById(R.id.bRetry);
        bShare = (Button) findViewById(R.id.bShare);

        questionsLists =(List<QuestionsList>) getIntent().getSerializableExtra("Questions");
        scorePourcentage.setText(getCorrectAnswers()*100/5+"%");
        DonutAnimation();


        bShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent= new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,"My Score =" +score);
                Intent shareIntent=Intent.createChooser(sendIntent,"Share via");
                startActivity(shareIntent);
            }
        });

        bRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity_score.this,activity_question.class));
                finish();
            }
        });

        scorePourcentage = (TextView) findViewById(R.id.scorePourcentage);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void DonutAnimation() {
        score=getCorrectAnswers()*100/5;
        donut = (app.futured.donut.DonutProgressView) findViewById(R.id.donut);
        donut.setAnimationDurationMs(2000);
        donut.setCap(100);
        donut.addAmount("", score, Color.parseColor("#CB1976D2"));
    }



    private int getCorrectAnswers(){
       int correctAnswer =0;

        for(int i=0; i<questionsLists.size();i++){
            int getUserSelectedOption = questionsLists.get(i).getUserSelectedAnswer();
            int getQuestionAnswer= questionsLists.get(i).getAnswer();

            if(getQuestionAnswer==getUserSelectedOption){
                correctAnswer++;
            }

        }
        return  correctAnswer;
    }
}