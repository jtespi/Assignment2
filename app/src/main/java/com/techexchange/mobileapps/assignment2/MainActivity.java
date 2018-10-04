package com.techexchange.mobileapps.assignment2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements
        QuestionListFrag.OnFragmentInteractionListener, OneQuestionFrag.OnFragmentInteractionListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.fragment_container);
        if (frag == null) {
            frag = new QuestionListFrag();
            fm.beginTransaction().add(R.id.fragment_container, frag).commit();
        }

        QuestionListFactory.generateQuestionList( this.getApplicationContext());
        QuestionListFactory.populateWrongAnswers();
    }

    public void onQuestionPressed(int questionId){
        Log.d(TAG, "OnQuestionPressed for question number" + questionId);
        Fragment frag2 = OneQuestionFrag.newInstance( questionId );
        fm.beginTransaction()
                .replace(R.id.fragment_container, frag2)
                .addToBackStack(null)
                .commit();
    }

    public void onQuestionAnswered( int questionNum) {
        fm.popBackStack();
    }
}
