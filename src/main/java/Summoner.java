import com.google.gson.*;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Summoner{

    private static String key = System.getenv("KEY");
    private String accountID;
    private int profileIconID;
    private long revisionDate;
    private String name;
    private String id;
    private String puuid;
    private long summonerLevel;

    public enum League{
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

    public enum Division{
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

    public static ArrayList<ArrayList<String>> getSampleIDs()
    {
        ArrayList<ArrayList<String>> sample = new ArrayList<>();
        League leagues[] = League.values();
        Division divisions[] = Division.values();
        for(League league: leagues)
        {
            ArrayList<String> leagueMembers = new ArrayList();
            for(Division division: divisions)
            {
                leagueMembers.addAll(getDivMembers(league, division));
            }
        }
        return sample;
    }

    public static ArrayList<String> getDivMembers(League league, Division division)
    {
        ArrayList<String> summIDs = new ArrayList();


        String urlCombined = "https://euw1.api.riotgames.com/lol/league-exp/v4/entries/RANKED_SOLO_5x5/" + league.name() + "/" + division.name() + "?page=1";
        int status = 429;

        while(status==429) {
            try {
                URL url = new URL(urlCombined);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setDoOutput(true);

                //<editor-fold desc="API Key - Secret Stuff">
                con.setRequestProperty("X-Riot-Token", key);
                //</editor-fold>

                status = con.getResponseCode();
                if(status==429)
                {
                    
                }
                System.out.println(status);

                JsonArray summArray = new Gson().fromJson(new InputStreamReader(con.getInputStream()), JsonArray.class);
                for (JsonElement summoner : summArray) {
                    System.out.println(summoner.getAsJsonObject().get("summonerName"));
                    summIDs.add(String.valueOf(summoner.getAsJsonObject().get("summonerId")));
                }
            } catch (Exception e) {
                System.out.println("The program encountered the following error : " + e);
            }
        }
        return summIDs;
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