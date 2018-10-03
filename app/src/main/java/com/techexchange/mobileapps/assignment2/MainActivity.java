package com.techexchange.mobileapps.assignment2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements QuestionListFrag.OnFragmentInteractionListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.fragment_container);
        if (frag == null) {
            frag = new QuestionListFrag();
            fm.beginTransaction().add(R.id.fragment_container, frag).commit();
        }

        QuestionListFactory.generateQuestionList( this.getApplicationContext());
        QuestionListFactory.populateWrongAnswers();

        Toast.makeText(this, "QuestionList generated successful", Toast.LENGTH_SHORT).show();
    }

    public void onQuestionPressed(int questionId){
        Log.d(TAG, "OnQuestionPressed for question number" + questionId);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new OneQuestionFrag());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
