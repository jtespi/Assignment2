package com.techexchange.mobileapps.assignment2;

public class Question {
    private final String cityName, chosenAns, corAns;
    private String[] wrongAnswers;
    private int qNumber;

    public Question(String cityName, String corAns) {
        this.cityName = cityName;
        this.corAns = corAns;
        this.qNumber = 0;
        this.wrongAnswers = null;
        this.chosenAns = null;
    }

    public Question(String cityName, String corAns, int qNum) {
        this.cityName = cityName;
        this.corAns = corAns;
        this.qNumber = qNum;
        this.wrongAnswers = null;
        this.chosenAns = null;
    }

    public int getqNumber() {
        return qNumber;
    }

    public void setWrongAnswers(String[] wrongAns ) {
        this.wrongAnswers = wrongAns;
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

    public String getChosenAns() {
        if (chosenAns == null) return "None";

        return chosenAns;
    }

    public String getQuestion() {
        StringBuilder sb = new StringBuilder();
        sb.append(cityName).append(" is the capital of which country?");

        return sb.toString();
    }
}
