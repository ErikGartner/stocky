package stocky;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.joda.time.LocalDate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

/**
 * A settings file. Used by GJSON to store settings.
 * Created by erik on 13/03/14.
 */
public class Settings {

    private List<String> symbols;
    private List<String> predictors;
    private List<String> pushoverRecipients;
    private String start;
    private String end;
    private int periodSize;
    private String[] usedMetrics;
    private boolean bootstrapp;

    public static Settings readSettings(String file) throws FileNotFoundException {

        Scanner scan = new Scanner(new File(file)).useDelimiter("\\Z");
        String settingsString = scan.next();
        scan.close();

        Gson gson = new Gson();
        Settings settings = gson.fromJson(settingsString, Settings.class);
        return settings;

    }

    public void writeSettings(String file) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new File(file));
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        pw.print(gson.toJson(this));
        pw.close();
    }

    public List<String> getPredictors() {
        return predictors;
    }

    public String[] getUsedMetrics() {
        return usedMetrics;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        if(end == null){
            end = LocalDate.now().toString();
        }
        return end;

    }

    public List<String> getSymbols() {
        return symbols;
    }

    public List<String> getPushoverRecipients() {
        return pushoverRecipients;
    }

    public int getPeriodSize() {
        return periodSize;
    }

    public boolean isBootstrapp() {
        return bootstrapp;
    }
}