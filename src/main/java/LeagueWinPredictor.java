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

        Summoner summoner = executeRequest(url);
        System.out.println(summoner.getName());
    }

    public static Summoner executeRequest(ArrayList<String> urlComponents)
    {
        String urlCombined = "";

        for(int i=0; i<urlComponents.size(); i++)
        {
            urlCombined+=urlComponents.get(i);
        }

        String result = "";

        try{
            URL url = new URL(urlCombined);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setDoOutput(true);
            //<editor-fold desc="API Key - Secret Stuff">
            con.setRequestProperty("X-Riot-Token", System.getenv("Key"));
            //</editor-fold>
            int status = con.getResponseCode();
            System.out.println(status);
            BufferedReader webScraper = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = webScraper.readLine();
            while(line != null)
            {
                result+=line;
                line = webScraper.readLine();
            }
        }
        catch(Exception e){
            System.out.println("The program encountered the following error : " + e);
        }

        Summoner summoner = new Gson().fromJson(result, Summoner.class);
        return summoner;
    }
}
