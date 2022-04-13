package com.example.quizappdmrd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;


import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import 	android.widget.VideoView;

public class activity_question extends AppCompatActivity {
    private final List<QuestionsList> questionsLists = new ArrayList<>();

    //Button btnLogout;
    Button bRecord;
    static final int REQUEST_VIDEO_CAPTURE = 1;
    Button btnOK;
    private RelativeLayout option1Layout, option2Layout, option3Layout, option4Layout;
    private TextView option1TV,option2TV,option3TV,option4TV;
    private ImageView option1Icon,option2Icon,option3Icon,option4Icon;

    private TextView currentQuestion;
    private TextView totalQuestionTV;

    private int selectedOption=0;
    private int currentQuestionPosition=0;

    private TextView questionTV;
    FirebaseAuth mAuth;

    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://quizappdmrd-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
       // btnLogout=findViewById(R.id.btnLogout);

        bRecord=findViewById(R.id.bRecord);

        btnOK=findViewById(R.id.btnOK);
        questionTV = findViewById(R.id.questionTV);

        option1Layout=findViewById(R.id.option1Layout);
        option2Layout=findViewById(R.id.option2Layout);
        option3Layout=findViewById(R.id.option3Layout);
        option4Layout=findViewById(R.id.option4Layout);

        option1TV=findViewById(R.id.option1TV);
        option2TV=findViewById(R.id.option2TV);
        option3TV=findViewById(R.id.option3TV);
        option4TV=findViewById(R.id.option4TV);

        option1Icon =findViewById(R.id.option1Icon);
        option2Icon =findViewById(R.id.option2Icon);
        option3Icon =findViewById(R.id.option3Icon);
        option4Icon =findViewById(R.id.option4Icon);

        totalQuestionTV=findViewById(R.id.totalQuestionsTV);
        currentQuestion=findViewById(R.id.currentQuestionTV);

        mAuth=FirebaseAuth.getInstance();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot Questions : snapshot.child("Questions").getChildren()){
                    String getQuestion = Questions.child("Question").getValue(String.class);
                    String getOption1 = Questions.child("Option1").getValue(String.class);
                    String getOption2 = Questions.child("Option2").getValue(String.class);
                    String getOption3 = Questions.child("Option3").getValue(String.class);
                    String getOption4 = Questions.child("Option4").getValue(String.class);
                    int getAnswer = Integer.parseInt(Questions.child("Answer").getValue(String.class));

                      QuestionsList questionsList =new QuestionsList(getQuestion,getOption1,getOption2,getOption3,getOption4,getAnswer);
                      questionsLists.add(questionsList);
                }
                totalQuestionTV.setText("/"+questionsLists.size());

                selectQuestion(currentQuestionPosition);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(activity_question.this,"Failed to get data from Firebase", Toast.LENGTH_SHORT).show();
            }
        });


/////////////////////////////////////////////////////////////////////////////////
        bRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakeVideoIntent();
            }
        });
//////////////////////////////////////////////////////////////////////////////////
        option1Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedOption=1;
                selectOption(option1Layout,option1Icon);
            }
        });
        option2Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedOption=2;
                selectOption(option2Layout,option2Icon);
            }
        });
        option3Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedOption=3;
                selectOption(option3Layout,option3Icon);
            }
        });
        option4Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedOption=4;
                selectOption(option4Layout,option4Icon);
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedOption!=0){
                     questionsLists.get(currentQuestionPosition).setUserSelectedAnswer(selectedOption);
                     selectedOption=0;
                     currentQuestionPosition++;

                    if (currentQuestionPosition < questionsLists.size()) {

                        selectQuestion(currentQuestionPosition);

                    }else{
                        finishQuiz();
                    }
                }
                else{
                    Toast.makeText(activity_question.this,"Please select an option",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    /////////////////////////////////////////////
    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }
    ////////////////////////////////////////////



    private void finishQuiz(){
        Intent intent = new Intent(activity_question.this,activity_score.class);

        //to pass QuizQuestionList

        Bundle bundle=new Bundle();
        bundle.putSerializable("Questions",(Serializable)questionsLists);
        // add bundle to intent
        intent.putExtras(bundle);

        startActivity(intent);
        //destroy current activity

        finish();

    }
    private void selectQuestion(int questionListPosition){
        //for new quest
        resetOptions();
        questionTV.setText(questionsLists.get(questionListPosition).getQuestion());
        option1TV.setText(questionsLists.get(questionListPosition).getOption1());
        option2TV.setText(questionsLists.get(questionListPosition).getOption2());
        option3TV.setText(questionsLists.get(questionListPosition).getOption3());
        option4TV.setText(questionsLists.get(questionListPosition).getOption4());

        currentQuestion.setText("Question"+(questionListPosition+1));
    }

    private void resetOptions(){
        option1Layout.setBackgroundResource(R.drawable.round_back_white50_10);
        option2Layout.setBackgroundResource(R.drawable.round_back_white50_10);
        option3Layout.setBackgroundResource(R.drawable.round_back_white50_10);
        option4Layout.setBackgroundResource(R.drawable.round_back_white50_10);

        option1Icon.setImageResource(R.drawable.round_back_white50_100);
        option2Icon.setImageResource(R.drawable.round_back_white50_100);
        option3Icon.setImageResource(R.drawable.round_back_white50_100);
        option4Icon.setImageResource(R.drawable.round_back_white50_100);



    }
private void selectOption(RelativeLayout selectedOptionLayout,ImageView selectedOptionIcon){
        resetOptions();
        selectedOptionIcon.setImageResource(R.drawable.ic_baseline_check_24);
        selectedOptionLayout.setBackgroundResource(R.drawable.round_back_selected_option);
}

}