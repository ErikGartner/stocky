package com.smoiz.stocky;

import com.smoiz.stocky.dataloader.Sentiment;
import com.smoiz.stocky.utils.Settings;

import java.io.FileNotFoundException;

/**
 * Created by erik on 16/03/14.
 */
public class StockyMain {
    public static void main(String[] args) {

        Settings settings = null;
        try {
            settings = Settings.readSettings("config.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        Stocky stocky = new Stocky(settings);
        stocky.predictStocks();

    }

}
