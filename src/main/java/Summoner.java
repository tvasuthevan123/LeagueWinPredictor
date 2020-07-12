public class Summoner{

    private String accountID;
    private int profileIconID;
    private long revisionDate;
    private String name;
    private String id;
    private String puuid;
    private long summonerLevel;

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