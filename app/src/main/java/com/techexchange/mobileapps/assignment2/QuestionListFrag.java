package com.techexchange.mobileapps.assignment2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuestionListFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuestionListFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionListFrag extends Fragment {
    private static final String ARG_QUIZ_FINALIZED = "quizFinalized";
    private static final String TAG = QuestionListFrag.class.getSimpleName();

    private RecyclerView rView;
    private Button emailButton;
    private QuestionAdapter qAdapter;

    private boolean quizFinalized;

    private OnFragmentInteractionListener mListener;

    public QuestionListFrag() {
        // Required empty public constructor
    }

    private class QuestionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Question mQuestion;

        private TextView mQuestionNumberTxt;
        private TextView mQuestionTxt;
        private LinearLayout mQuestionLayout;

        public QuestionHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_question, parent, false));
            itemView.setOnClickListener(this);

            mQuestionNumberTxt = (TextView) itemView.findViewById(R.id.question_num);
            mQuestionTxt = (TextView) itemView.findViewById(R.id.question_txt);
            mQuestionLayout = (LinearLayout) itemView.findViewById(R.id.question_layout);
        }

        public void bind( Question q ) {
            mQuestion = q;
            mQuestionNumberTxt.setText("Question #" + q.getqNumber());
            mQuestionTxt.setText(q.getQuestion());

            String chosenAnswer = mQuestion.getChosenAns();
            String correctAnswer = mQuestion.getCorAns();

            if ( !(quizFinalized) && chosenAnswer.equals("None"))
                mQuestionLayout.setBackgroundResource(R.drawable.rect1);
            else if ( !(quizFinalized) && !(chosenAnswer.equals("None"))){
                mQuestionLayout.setBackgroundResource(R.drawable.rect_blue);
            }
            else if ( quizFinalized && chosenAnswer.equals(correctAnswer)) {
                mQuestionLayout.setBackgroundResource(R.drawable.rect_green);
            }
            else {
                mQuestionLayout.setBackgroundResource(R.drawable.rect_red);
            }
        }

        @Override
        public void onClick(View view) {
            int questionNum = mQuestion.getqNumber();

            if (quizFinalized) return;

            Log.d(TAG, "Calling mListener for question number" + questionNum);
            mListener.onQuestionPressed(questionNum);
        }
    }

    private class QuestionAdapter extends RecyclerView.Adapter<QuestionHolder> {
    private List<Question> mQuestions;
        public QuestionAdapter(List<Question> questionList) {
            mQuestions = questionList;
        }

        @NonNull
        @Override
        public QuestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new QuestionHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull QuestionHolder questionHolder, int position) {
            Question q = mQuestions.get(position);
            questionHolder.bind(q);
        }

        @Override
        public int getItemCount() {
            return mQuestions.size();
        }
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 A boolean specifying if the quiz was finalized.
     * @return A new instance of fragment QuestionListFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestionListFrag newInstance(Boolean param1) {
        QuestionListFrag fragment = new QuestionListFrag();
        Bundle args = new Bundle();
        args.putBoolean(ARG_QUIZ_FINALIZED, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            quizFinalized = getArguments().getBoolean(ARG_QUIZ_FINALIZED);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_question_list, container, false);

        rView = (RecyclerView) view.findViewById(R.id.recyclerView);
        rView.setLayoutManager(new LinearLayoutManager(getActivity()));

        emailButton = view.findViewById(R.id.btn_email);
        Button mainSubmitButton = view.findViewById(R.id.btn_submit);

        if (!quizFinalized) emailButton.setEnabled(false);

        mainSubmitButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmitButtonPressed( );
            }
        });

        emailButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEmailButtonPressed( );
            }
        });

        updateUI();

        // Inflate the layout for this fragment
        return view;
    }

    private void updateUI() {
        QuestionListFactory qlFact = QuestionListFactory.get(getActivity());
        List<Question> questions = qlFact.getQuestionList();
        qAdapter = new QuestionAdapter(questions);
        rView.setAdapter(qAdapter);
    }

    private void onSubmitButtonPressed() {
        Log.d(TAG, "Submit button was pressed!");
        quizFinalized = true;
        emailButton.setEnabled(true);
    }

    private void onEmailButtonPressed() {
        String scoreReport = QuestionListFactory.generateScoreReport();
        Context context = this.getContext();

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // Only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Quiz Score Report");
        intent.putExtra(Intent.EXTRA_TEXT, scoreReport); // Enter a string for the email body
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(context,
                    "The activity could not be resolved.", Toast.LENGTH_SHORT).show();
        }
    }

    interface OnFragmentInteractionListener {
        void onQuestionPressed(int questionId);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
