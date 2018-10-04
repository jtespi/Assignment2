package com.techexchange.mobileapps.assignment2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OneQuestionFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OneQuestionFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OneQuestionFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String  ARG_QNUM = "qNum";

    // TODO: Rename and change types of parameters
    private int qNum;

    List<Question> questions;

    private OnFragmentInteractionListener mListener;

    public OneQuestionFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 The question number.
     * @return A new instance of fragment OneQuestionFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static OneQuestionFrag newInstance(int param1) {
        OneQuestionFrag fragment = new OneQuestionFrag();
        Bundle args = new Bundle();
        args.putInt(ARG_QNUM, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            qNum = getArguments().getInt(ARG_QNUM);
        }
        QuestionListFactory qlFact = QuestionListFactory.get(getActivity());
        questions = qlFact.getQuestionList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one_question, container, false);

        Button submitButton = view.findViewById(R.id.btn_submit);
        TextView questionTxt = view.findViewById(R.id.oneQuestionTxt);
        RadioButton rb_one = view.findViewById(R.id.radio1);
        RadioButton rb_two = view.findViewById(R.id.radio2);
        RadioButton rb_three = view.findViewById(R.id.radio3);
        RadioButton rb_four = view.findViewById(R.id.radio4);

        Question currentQuestion = questions.get(qNum-1);

        questionTxt.setText(currentQuestion.getQuestion());

        String[] rButtonText = new String[4];
        String[] wrongAnswers = currentQuestion.getWrongAnswers();

        // get a random start index from 0 to 3
        int index = (int)(Math.random() * 4);
        int indexB = 0;
        rButtonText[index] = currentQuestion.getCorAns();
        index++;
        for ( int count = 1; count <= 3; count++ ) {
            if (index > 3) index = 0;
            rButtonText[index] = wrongAnswers[indexB];
            index++; indexB++;
        }

        rb_one.setText(rButtonText[0]);
        rb_two.setText(rButtonText[1]);
        rb_three.setText(rButtonText[2]);
        rb_four.setText(rButtonText[3]);

        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onSubmitPressed(int qNum) {
        if (mListener != null) {
            mListener.onQuestionAnswered( qNum );
        }
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onQuestionAnswered( int questionNum );
    }
}
