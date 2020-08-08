package com.example.myguessgame.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myguessgame.R;

public class CheatActivity extends AppCompatActivity {

    public static final String EXTRA_IS_CHEAT = "com.example.myguessgame.ExtraIsCheat";
    private Button mButton_cheat;
    private TextView mTextView_ShowTheCorrectAnswer;
    private boolean mIsAnswerTrue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        mIsAnswerTrue = getIntent().getBooleanExtra(GuessGame.EXTRA_QUESTION_ANSWER,false);
        findView();
        setListers();

    }

    private void setListers() {
        mButton_cheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIsAnswerTrue){
                    mTextView_ShowTheCorrectAnswer.setText(R.string.button_true);
                }
                else {
                    mTextView_ShowTheCorrectAnswer.setText(R.string.button_false);
                    }
                }
        });

        setShownAnswerResult(true);
    }

    private void findView(){
        mButton_cheat = findViewById(R.id.btn_cheat);
        mTextView_ShowTheCorrectAnswer = findViewById(R.id.textView_Cheat_answer_show);
    }

    private void setShownAnswerResult(boolean isCheat){
        Intent intent = new Intent();
        intent.putExtra(EXTRA_IS_CHEAT,isCheat);
        setResult(RESULT_OK,intent);
    }

}