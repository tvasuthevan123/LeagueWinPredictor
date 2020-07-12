import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

public class LeagueWinPredictor {

    public static void main(String args[])
    {
        URL url;
        String mainSummonerRequestURL = "https://euw1.api.riotgames.com/lol/summoner/v4/summoners/by-name/";
        String summonerName = "Yuseya";
//        String region = "euw1";

        

    }

    public String executeRequest(ArrayList<String> urlComponents)
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
        return result;
    }
}
