package com.techexchange.mobileapps.assignment2;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class QuestionListFactory {
    private static QuestionListFactory sQuestionListFactory;
    private static List<Question> questionList;

    public static QuestionListFactory get(Context context) {
        if (sQuestionListFactory == null) {
            sQuestionListFactory = new QuestionListFactory( context );
        }
        return sQuestionListFactory;
    }

    private QuestionListFactory( Context context ) {
        //questionList = new ArrayList<>();
        questionList = questionList;
    }

    public List<Question> getQuestionList(){
        return questionList;
    }

    public static boolean generateQuestionList(Context context) {
        InputStream ins = context.getResources().openRawResource(
               context.getResources().getIdentifier("country_list",
                        "raw", context.getPackageName()));

        BufferedReader br = new BufferedReader( new InputStreamReader(ins) );

        questionList = new ArrayList<Question>();

        String line;
        try {
            while ( (line = br.readLine()) != null ) {
                String[] values = line.split(",");

                String country = values[0].substring(1, values[0].length()-1);
                String capital = values[1].substring(1, values[1].length()-1);

                questionList.add( new Question(capital, country));
            }
        } catch (IOException e ) {
            return false;
        }

        return true;
    }

    public static void populateWrongAnswers() {
        int max = questionList.size();
        for ( int index = 0; index < max; index++ ){
            String[] otherCountries = new String[3];

            for ( int count = 1; count <=3; count++ ) {
                int randomIndex = (int) (Math.random() * max );
                // if randomIndex happens to be the same index, then try again
                if (randomIndex == index ) randomIndex = (int)(Math.random() * max );

                otherCountries[count-1] = questionList.get(randomIndex).getCorAns();
            }
            questionList.get(index).setWrongAnswers( otherCountries );
        }
    }
}
