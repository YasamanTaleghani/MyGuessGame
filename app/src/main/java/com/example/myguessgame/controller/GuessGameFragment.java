package com.example.myguessgame.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myguessgame.R;
import com.example.myguessgame.models.Question;


public class GuessGameFragment extends Fragment {

    public static final String GUESS_GAME = "GuessGame";
    public static final String BUNDLE_M_CURRENT_INDEX = "Bundle_mCurrentIndex";
    public static final String BUNDLE_SCORE = "bundle_score";
    public static final String IS_CLICKED = "IsClicked";
    public static final String EXTRA_QUESTION_ANSWER = "com.example.myguessgame.extraQuestionAnswer";
    public static final int REQUEST_CODE_CHEAT = 0;
    public static final String BUNDLE_IS_CHEATER = "Bundle_IsCheater";

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
    private Button mButtonCheat;

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
    private boolean[] mIsCheater = new boolean[mQuestionBank.length];

    // ------------------------------------------------------------------------------

    public GuessGameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_guess_game,container,false);
        findView(view);
        listers();

        if (savedInstanceState!=null){
            mCurrentIndex = savedInstanceState.getInt(BUNDLE_M_CURRENT_INDEX);
            mCorrectQuestion = savedInstanceState.getInt(BUNDLE_SCORE);
            mScore.setText("   امتیاز شما  " + mCorrectQuestion);
            mIsCheater = savedInstanceState.getBooleanArray(BUNDLE_IS_CHEATER);
            mIsClicked = savedInstanceState.getBooleanArray(IS_CLICKED);
        }

        updateQuestion();
        return view;
    }

    private void findView(View view) {
        mTextView = view.findViewById(R.id.txtview_question_text);
        mTrueButton = view.findViewById(R.id.btn_true);
        mFalseButton = view.findViewById(R.id.btn_false);
        mDoubleLeftButton = view.findViewById(R.id.left_double_array_icon);
        mLeftButton = view.findViewById(R.id.left_array_icon);
        mRightButton = view.findViewById(R.id.right_array_icon);
        mDoubleRightButton = view.findViewById(R.id.right_double_araay_icon);
        mScore = view.findViewById(R.id.ScoreText);
        main = view.findViewById(R.id.main);
        mFinalScoreShow = view.findViewById(R.id.ScoreShow);
        mFinalScores = view.findViewById(R.id.finalShowScoresLayout);
        mButtonReset = view.findViewById(R.id.button_reset);
        mButtonCheat = view.findViewById(R.id.btn_cheat);
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
                /*finish();
                startActivity(getIntent());*/
            }
        });

        mButtonCheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CheatActivity.class);
                intent.putExtra(EXTRA_QUESTION_ANSWER,mQuestionBank[mCurrentIndex].isAnswerTrue());
                startActivityForResult(intent,REQUEST_CODE_CHEAT);
            }

        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK || data == null )
            return;

        if (requestCode == REQUEST_CODE_CHEAT )
            mIsCheater[mCurrentIndex] = data.getBooleanExtra(CheatActivityFragment.EXTRA_IS_CHEAT,
                    false);

    }

    private void IsGameOver() {
        if (mAnsweredQuestions == mQuestionBank.length) {
            main.setVisibility(View.GONE);
            mFinalScoreShow.setText("   امتیاز نهایی شما  " + mCorrectQuestion);
            mFinalScores.setVisibility(View.VISIBLE);
        }
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
        if (mIsCheater[mCurrentIndex]){
            Toast.makeText(getContext(), R.string.CheatToast, Toast.LENGTH_LONG)
                    .show();
        }
        else {
            if (mQuestionBank[mCurrentIndex].isAnswerTrue() == userPressed) {
                Toast.makeText(getContext(), R.string.toast_correct, Toast.LENGTH_LONG)
                        .show();
                mCorrectQuestion++;
                mAnsweredQuestions++;
                mScore.setText("   امتیاز شما  " + mCorrectQuestion);
                mFinalScoreShow.setText("   امتیاز شما  " + mCorrectQuestion);

            } else {
                Toast.makeText(getContext(), R.string.toast_incorrect, Toast.LENGTH_SHORT)
                        .show();
                mAnsweredQuestions++;
                mScore.setText("   امتیاز شما  " + mCorrectQuestion);
                mFinalScoreShow.setText("   امتیاز شما  " + mCorrectQuestion);

            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(GUESS_GAME, "onSaveInstant: " + mCurrentIndex);
        outState.putInt(BUNDLE_M_CURRENT_INDEX, mCurrentIndex);
        outState.putInt(BUNDLE_SCORE, mCorrectQuestion);
        outState.putBooleanArray(BUNDLE_IS_CHEATER,mIsCheater);
        outState.putBooleanArray(IS_CLICKED, mIsClicked);
    }

}