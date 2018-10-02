package com.techexchange.mobileapps.assignment2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView rView;
    private QuestionAdapter qAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public QuestionListFrag() {
        // Required empty public constructor
    }

    private class QuestionHolder extends RecyclerView.ViewHolder {

        private Question mQuestion;

        private TextView mQuestionNumberTxt;
        private TextView mQuestionTxt;

        public QuestionHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_quesion, parent, false));

            mQuestionNumberTxt = (TextView) itemView.findViewById(R.id.question_num);
            mQuestionTxt = (TextView) itemView.findViewById(R.id.question_txt);
        }

        public void bind( Question q ) {
            mQuestion = q;
            mQuestionNumberTxt.setText("Question #" + q.getqNumber());
            mQuestionTxt.setText(q.getQuestion());
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
            if (position == 0) { position = 1; }
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
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuestionListFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestionListFrag newInstance(String param1, String param2) {
        QuestionListFrag fragment = new QuestionListFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_question_list, container, false);

        rView = (RecyclerView) view.findViewById(R.id.recyclerView);
        rView.setLayoutManager(new LinearLayoutManager(getActivity()));

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
