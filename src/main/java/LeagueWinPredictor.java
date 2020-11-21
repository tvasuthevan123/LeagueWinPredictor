import java.util.ArrayList;

public class LeagueWinPredictor {

    public static void main(String args[])
    {
        ArrayList<String> url = new ArrayList();
        String mainSummonerRequestURL = "https://euw1.api.riotgames.com/lol/summoner/v4/summoners/by-name/";
        String summonerName = "Yuseya";

        url.add(mainSummonerRequestURL);
        url.add(summonerName);
    }

}
