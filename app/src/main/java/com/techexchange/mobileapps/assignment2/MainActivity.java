package com.techexchange.mobileapps.assignment2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QuestionListFactory.generateQuestionList( this.getApplicationContext());
        QuestionListFactory.populateWrongAnswers();

        Toast.makeText(this, "QuestionList generated successful", Toast.LENGTH_SHORT).show();
    }
}
