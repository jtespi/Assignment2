package com.techexchange.mobileapps.assignment2;

public class Question {
    private final String cityName, corAns;
    private final String[] wrongAnswers;

    public Question(String cityName, String corAns, String[] wrongAnswers) {
        this.cityName = cityName;
        this.corAns = corAns;
        this.wrongAnswers = wrongAnswers;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCorAns() {
        return corAns;
    }

    public String[] getWrongAnswers() {
        return wrongAnswers;
    }

    public String getQuestion() {
        StringBuilder sb = new StringBuilder();
        sb.append(cityName).append(" is the capital of which country?");

        return sb.toString();
    }
}
