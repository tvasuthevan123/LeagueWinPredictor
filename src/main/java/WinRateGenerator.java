import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

public class WinRateGenerator {

    String key = System.getenv("KEY");

    public void winRateList()
    {
        ArrayList<ArrayList<String>> sample = Summoner.getSampleIDs();
        ArrayList<HashSet<Long>> matchSample = new ArrayList<>();

        JsonArray champArray = generateChampArray();
        int champMaxID = champArray.size();
        Summoner.League[] leagues = Summoner.League.values();
        Summoner.Division[] divisions = Summoner.Division.values();
        int[][][] champMatchTotals = new int[leagues.length][champMaxID][champMaxID];

        for(Summoner.League league : leagues)
        {
            HashSet<Long> leagueMatchSample = new HashSet();
            for(int i=0; i<sample.get(league.ordinal()).size(); i++)
            {
                for(String id : sample.get(i))
                {
                    leagueMatchSample.addAll(getMatches(id));
                }
            }
            matchSample.add(leagueMatchSample);
        }

        for(HashSet<Long> leagueMatchSample: matchSample)
        {

        }

    }

    public JsonArray generateChampArray()
    {
        String version = "10.23.1";
        JsonArray champs = null;
        try
        {
            URL url = new URL("http://ddragon.leagueoflegends.com/cdn/"+ version + "/data/en_US/champion.json");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setDoOutput(true);

//            //<editor-fold desc="API Key - Secret Stuff">
//            con.setRequestProperty("X-Riot-Token", key);
//            //</editor-fold>

            con.getResponseCode();
            champs = new Gson().fromJson(new InputStreamReader(con.getInputStream()), JsonArray.class);
        }
        catch(Exception e)
        {
            System.out.println("The following error has occurred : " + e);
        }

        return champs;
    }

    public ArrayList<Long> getMatches(String summID)
    {
        JsonArray matches = new JsonArray();
        long beginTime = (long) (System.currentTimeMillis() - 5.927e+8);
        ArrayList<Long> matchIDs = new ArrayList<>();

        try {
            URL url = new URL("https://euw1.api.riotgames.com/lol/match/v4/matchlists/by-account/" + summID + "?queue=420");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setDoOutput(true);

            //<editor-fold desc="API Key - Secret Stuff">
            con.setRequestProperty("X-Riot-Token", key);
            //</editor-fold>

            int status = con.getResponseCode();
            System.out.println(status);

            matches = new Gson().fromJson(new InputStreamReader(con.getInputStream()), JsonArray.class);
        } catch (Exception e) {
            System.out.println("The program encountered the following error : " + e);
        }

        for(JsonElement match : matches)
        {
            matchIDs.add(Long.getLong(String.valueOf(match.getAsJsonObject().get("gameId"))));
        }

        return matchIDs;
    }

    public int getChamp(JsonArray champs, int id)
    {
        int index = -1;
        for(int i=0; i<champs.size(); i++)
        {
            JsonElement champ = champs.get(i);
            if(Integer.parseInt(String.valueOf(champ.getAsJsonObject().get("key"))) == id)
            {
                index = i;
            }
        }

        return index;
    }

    public int[][] generateMatchUpStats(Long matchID)
    {
        /*participantStatsDTO
        particpantTimelineDTO
        role
        lane
        check lane opponent champ*/



        JsonObject matchInfo = new JsonObject();
        try {
            URL url = new URL("https://euw1.api.riotgames.com/lol/match/v4/matches/" + matchID);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setDoOutput(true);

            //<editor-fold desc="API Key - Secret Stuff">
            con.setRequestProperty("X-Riot-Token", key);
            //</editor-fold>

            int status = con.getResponseCode();
            System.out.println(status);

            matchInfo = new Gson().fromJson(new InputStreamReader(con.getInputStream()), JsonObject.class);
        } catch (Exception e) {
            System.out.println("The program encountered the following error : " + e);
        }



        return null;
    }

    public int winnerID(JsonObject match)
    {
        int winnerID = -1;
        JsonArray teams = match.get("teams").getAsJsonArray();
        for(JsonElement team : teams)
        {
            if(String.valueOf(team.getAsJsonObject().get("win")) == "Win")
                winnerID = Integer.parseInt(String.valueOf(team.getAsJsonObject().get("teamId")));
        }

        return winnerID;
    }

    public int getParticipantTeam(JsonObject participantDTO)
    {
        return Integer.parseInt(String.valueOf(participantDTO.get("teamId")));
    }

    public String getRole(JsonObject participantTimelineDTO)
    {
        String[] positionArray = {String.valueOf(participantTimelineDTO.get("role")),String.valueOf(participantTimelineDTO.get("lane"))};
        String role = positionArray[1];

        if(positionArray[0] == "DUO_SUPPORT")
            role = "SUPPORT";
        if(positionArray[0] == "DUO_CARRY")
            role = "ADC";

        return role;
    }

    public int[][] getParticipantMatchups()
    {
        return null;
    }
}
