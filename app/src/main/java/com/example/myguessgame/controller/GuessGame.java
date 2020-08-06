package com.example.myguessgame.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myguessgame.R;
import com.example.myguessgame.models.Question;

public class GuessGame extends AppCompatActivity {

    public static final String GUESS_GAME = "GuessGame";
    public static final String BUNDLE_M_CURRENT_INDEX = "Bundle_mCurrentIndex";
    public static final String BUNDLE_SCORE = "bundle_score";
    public static final String IS_CLICKED = "IsClicked";

    private TextView mTextView;
    private TextView mScore;
    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mDoubleLeftButton;
    private ImageButton mLeftButton;
    private ImageButton mRightButton;
    private ImageButton mDoubleRightButton;

    private int mCorrectQuestion = 0;
    private int mAnsweredQuestions = 0;
    private LinearLayout main;
    private LinearLayout mFinalScores;
    private TextView mFinalScoreShow;
    private Button mButtonReset;

    private int mCurrentIndex = 0;
    private Question[] mQuestionBank = {
            new Question(R.string.question_australia, false),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, true),
            new Question(R.string.question_americas, false),
            new Question(R.string.question_asia, false)
    };

    private boolean[] mIsClicked = new boolean[mQuestionBank.length];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(GUESS_GAME, "onCreate: ");

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(BUNDLE_M_CURRENT_INDEX, 0);
            mCorrectQuestion = savedInstanceState.getInt(BUNDLE_SCORE, 0);
            mIsClicked = savedInstanceState.getBooleanArray(IS_CLICKED);
        }

        setContentView(R.layout.activity_main);
        findView();
        listers();
        updateQuestion();

    }

    private void updateQuestion() {

        int questionTextResId = mQuestionBank[mCurrentIndex].getQuestionTextResId();
        mTextView.setText(questionTextResId);
        mScore.setText("   امتیاز شما  " + mCorrectQuestion);
        mFinalScoreShow.setText("   امتیاز شما  " + mCorrectQuestion);
        if (mIsClicked[mCurrentIndex]) {
            mTrueButton.setEnabled(false);
            mFalseButton.setEnabled(false);
        } else {
            mTrueButton.setEnabled(true);
            mFalseButton.setEnabled(true);
        }
        IsGameOver();
    }

    private void checkAnswer(boolean userPressed) {
        if (mQuestionBank[mCurrentIndex].isAnswerTrue() == userPressed) {
            Toast.makeText(GuessGame.this, R.string.toast_correct, Toast.LENGTH_LONG)
                    .show();
            mCorrectQuestion++;
            mAnsweredQuestions++;
            mScore.setText("   امتیاز شما  " + mCorrectQuestion);
            mFinalScoreShow.setText("   امتیاز شما  " + mCorrectQuestion);

        } else {
            Toast.makeText(GuessGame.this, R.string.toast_incorrect, Toast.LENGTH_SHORT)
                    .show();
            mAnsweredQuestions++;
            mScore.setText("   امتیاز شما  " + mCorrectQuestion);
            mFinalScoreShow.setText("   امتیاز شما  " + mCorrectQuestion);

        }
    }

    private void findView() {
        mTextView = findViewById(R.id.txtview_question_text);
        mTrueButton = findViewById(R.id.btn_true);
        mFalseButton = findViewById(R.id.btn_false);
        mDoubleLeftButton = findViewById(R.id.left_double_array_icon);
        mLeftButton = findViewById(R.id.left_array_icon);
        mRightButton = findViewById(R.id.right_array_icon);
        mDoubleRightButton = findViewById(R.id.right_double_araay_icon);
        mScore = findViewById(R.id.ScoreText);
        main = findViewById(R.id.main);
        mFinalScoreShow = findViewById(R.id.ScoreShow);
        mFinalScores = findViewById(R.id.finalShowScoresLayout);
        mButtonReset = findViewById(R.id.button_reset);

    }

    private void listers() {
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIsClicked[mCurrentIndex] = true;
                mTrueButton.setEnabled(false);
                mFalseButton.setEnabled(false);
                checkAnswer(true);
                IsGameOver();
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIsClicked[mCurrentIndex] = true;
                mTrueButton.setEnabled(false);
                mFalseButton.setEnabled(false);
                checkAnswer(false);
                IsGameOver();
            }
        });

        mDoubleLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int questionTextResId = mQuestionBank[mQuestionBank.length - 1].getQuestionTextResId();
                mTextView.setText(questionTextResId);
                if (mIsClicked[mCurrentIndex]) {
                    mTrueButton.setEnabled(false);
                    mFalseButton.setEnabled(false);
                } else {
                    mTrueButton.setEnabled(true);
                    mFalseButton.setEnabled(true);
                }
            }
        });

        mDoubleRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int questionTextResId = mQuestionBank[0].getQuestionTextResId();
                mTextView.setText(questionTextResId);
                if (mIsClicked[mCurrentIndex]) {
                    mTrueButton.setEnabled(false);
                    mFalseButton.setEnabled(false);
                } else {
                    mTrueButton.setEnabled(true);
                    mFalseButton.setEnabled(true);
                }
            }
        });

        mLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
                if (mIsClicked[mCurrentIndex]) {
                    mTrueButton.setEnabled(false);
                    mFalseButton.setEnabled(false);
                } else {
                    mTrueButton.setEnabled(true);
                    mFalseButton.setEnabled(true);
                }
            }
        });

        mRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mCurrentIndex = (mCurrentIndex - 1 + mQuestionBank.length) % mQuestionBank.length;
                updateQuestion();
                if (mIsClicked[mCurrentIndex]) {
                    mTrueButton.setEnabled(false);
                    mFalseButton.setEnabled(false);
                } else {
                    mTrueButton.setEnabled(true);
                    mFalseButton.setEnabled(true);
                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(getIntent());
            }
        });

    }

    private void IsGameOver() {
        if (mAnsweredQuestions == mQuestionBank.length) {
            main.setVisibility(View.GONE);
            mFinalScoreShow.setText("   امتیاز نهایی شما  " + mCorrectQuestion);
            mFinalScores.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(GUESS_GAME, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(GUESS_GAME, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(GUESS_GAME, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(GUESS_GAME, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(GUESS_GAME, "onDestroy: ");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(GUESS_GAME, "onSaveInstant: " + mCurrentIndex);
        outState.putInt(BUNDLE_M_CURRENT_INDEX, mCurrentIndex);
        outState.putInt(BUNDLE_SCORE, mCorrectQuestion);
        outState.putBooleanArray(IS_CLICKED, mIsClicked);
    }
}