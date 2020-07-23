import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Summoner{

    private String accountID;
    private int profileIconID;
    private long revisionDate;
    private String name;
    private String id;
    private String puuid;
    private long summonerLevel;

    enum League{
        CHALLENGER,
        GRANDMASTER,
        MASTER,
        DIAMOND,
        PLATINUM,
        GOLD,
        SILVER,
        BRONZE,
        IRON
    }

    enum Division{
        I,
        II,
        III,
        IV
    }


    public Summoner(String accountID, int profileIconID, long revisionDate, String name, String id, String puuid, long summonerLevel)
    {
        this.accountID = accountID;
        this.profileIconID = profileIconID;
        this.revisionDate = revisionDate;
        this.name = name;
        this.id = id;
        this.puuid = puuid;
        this.summonerLevel = summonerLevel;
    }

    public static void generateJson()
    {
        League leagues[] = League.values();
        Division divisions[] = Division.values();
        for(League league: leagues)
        {
            for(Division division: divisions)
            {

            }
        }
    }

    public static ArrayList<Summoner> executeRequest(League league, Division division)
    {
        ArrayList<Summoner> summoners = new ArrayList();
        String urlCombined = "https://euw1.api.riotgames.com/lol/league/v4/entries/RANKED_SOLO_5x5/" + league.name() + "/" + division.name() + "?page=%d";

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
        return null;
    }

    public int getProfileIconID() {
        return profileIconID;
    }

    public long getRevisionDate() {
        return revisionDate;
    }

    public long getSummonerLevel() {
        return summonerLevel;
    }

    public String getAccountID() {
        return accountID;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPuuid() {
        return puuid;
    }
}