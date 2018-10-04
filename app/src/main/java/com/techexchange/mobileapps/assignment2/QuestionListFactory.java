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
        int index = 0;

        String line;
        try {
            while ( (line = br.readLine()) != null ) {
                String[] values = line.split(",");

                String country = values[0].substring(1, values[0].length()-1);
                String capital = values[1].substring(1, values[1].length()-1);

                if ( index != 0 )
                    questionList.add( new Question(capital, country, index));

                index++;
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

    public static String generateScoreReport() {
        StringBuilder sb = new StringBuilder();
        int finalScore = 0;

        sb.append("Here is the complete score report:\n\n");
        for ( int index = 0; index < questionList.size(); index++ ) {
            Question curQ = questionList.get(index);
            int points = 0;
            if ( curQ.getChosenAns().equals(curQ.getCorAns())) points = 1;

            sb.append("Question: ");
            sb.append(curQ.getQuestion()).append("\n");
            sb.append("Your answer: ").append(curQ.getChosenAns()).append("\n");
            sb.append("Correct answer: ").append(curQ.getCorAns()).append("\n");
            sb.append("Points: ").append( points ).append("\n\n");

            finalScore += points;
        }

        String summaryLine = "Summary: " + finalScore + " out of 248\n\n";

        return summaryLine + sb.toString();

    }
}
