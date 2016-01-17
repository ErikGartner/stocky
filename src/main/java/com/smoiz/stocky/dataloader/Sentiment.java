package com.smoiz.stocky.dataloader;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.smoiz.stocky.Stocky;

import java.net.URLEncoder;

/**
 * Created by erik on 15/03/15.
 */
public class Sentiment {

    private String lang;
    private double score;

    public Sentiment(String lang, double score){
        this.lang = lang;
        this.score = score;
    }

    public String toString(){
        return String.format("%f - %s", score, lang);
    }

    public double getScore() {
        return score;
    }

    public String getLang() {
        return lang;
    }

    public static Sentiment getSentiment(String msg) {

        try {
            String enc = URLEncoder.encode(msg, "UTF-8");
            HttpResponse<JsonNode> response = Unirest.get("https://loudelement-free-natural-language-processing-service.p.mashape.com/nlp-text/?text=" + enc)
                    .header("X-Mashape-Key", "a9ZF7GStGlmshLsHW3LKLj6TwndRp1lop1ZjsnOvlIl6mY7Nre")
                    .header("Accept", "application/json")
                    .asJson();
            double score = (Double)response.getBody().getObject().get("sentiment-score");
            String lang = (String)response.getBody().getObject().get("language");
            return new Sentiment(lang, score);
        } catch (Exception e) {
            Stocky.dbg("Error while doing sentiment analysis: " + e.getMessage());
            return null;
        }

    }

}
