import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class LeagueWinPredictor {

    public static void main(String args[])
    {
        ArrayList<String> url = new ArrayList();
        String mainSummonerRequestURL = "https://euw1.api.riotgames.com/lol/summoner/v4/summoners/by-name/";
        String summonerName = "Yuseya";

        url.add(mainSummonerRequestURL);
        url.add(summonerName);


        System.out.println(summoner.getName());
    }

}
