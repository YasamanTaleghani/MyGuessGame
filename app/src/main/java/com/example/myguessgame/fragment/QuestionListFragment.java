package com.example.myguessgame.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myguessgame.R;
import com.example.myguessgame.Repository.QuestionListRepository;
import com.example.myguessgame.controller.GuessGame;
import com.example.myguessgame.models.Question;

import java.util.List;


public class QuestionListFragment extends Fragment {

    public static final String EXTRA_QUESTION_RESID = "com.example.myguessgame.ResId";
    private RecyclerView mRecyclerView;
    private QuestionListRepository mRepository;

    public QuestionListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRepository = QuestionListRepository.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question_list, container, false);

        findViews(view);
        initViews();

        return view;
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_question_list);
    }

    private void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<Question> questions = mRepository.getQuestion();
        QuestionAdapter questionAdapter = new QuestionAdapter(questions);

        mRecyclerView.setAdapter(questionAdapter);
    }

    private class QuestionHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewQuestion;
        private TextView mTextViewAnswer;
        private Question mQuestion;

        public QuestionHolder(@NonNull View itemView) {
            super( itemView);

            mTextViewQuestion = itemView.findViewById(R.id.row_item_Qustion);
            mTextViewAnswer = itemView.findViewById(R.id.row_item_answer);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO: Refactore creating new intent.
                    Intent intent = new Intent(getActivity(), GuessGame.class);
                    intent.putExtra(EXTRA_QUESTION_RESID, mQuestion.getQuestionTextResId());
                    startActivity(intent);
                }
            });
        }

        public void bindQuestion(Question question) {
            mQuestion = question;
            mTextViewQuestion.setText(question.getQuestionTextResId());
            if (question.isAnswerTrue())
                mTextViewAnswer.setText("درست");
            else
                mTextViewAnswer.setText("نادرست");
        }
    }

    private class QuestionAdapter extends RecyclerView.Adapter<QuestionHolder> {

        private List<Question> mQuestions;

        public List<Question> getQuestions() {
            return mQuestions;
        }

        public void setQuestions(List<Question> questions) {
            mQuestions = questions;
        }

        public QuestionAdapter(List<Question> questions) {
            mQuestions = questions;
        }

        @Override
        public int getItemCount() {
            return mQuestions.size();
        }

        @NonNull
        @Override
        public QuestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.question_row_list, parent, false);
            QuestionHolder questionHolder = new QuestionHolder(view);
            return questionHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull QuestionHolder holder, int position) {
            Question question = mQuestions.get(position);
            holder.bindQuestion(question);
        }
    }
}