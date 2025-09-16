package olympics;

public class Athlete {
    private int id;
    private String name;
    private String sex;
    private int age;
    private int height;
    private int weight;
    private String team;
    private String noc;
    private int year;
    private String season;
    private String city;
    private String sport;
    private String event;
    private String medal;
    private String games;

    public Athlete(int id, String name, String sex, int age, int height, int weight,
                   String team, String noc, int year, String season,
                   String city, String sport, String event, String medal, String games) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.team = team;
        this.noc = noc;
        this.year = year;
        this.season = season;
        this.city = city;
        this.sport = sport;
        this.event = event;
        this.medal = medal;
        this.games = games;
    }

    // ===== Getters & Setters =====
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }

    public String getTeam() { return team; }
    public void setTeam(String team) { this.team = team; }

    public String getNoc() { return noc; }
    public void setNoc(String noc) { this.noc = noc; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public String getSeason() { return season; }
    public void setSeason(String season) { this.season = season; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getSport() { return sport; }
    public void setSport(String sport) { this.sport = sport; }

    public String getEvent() { return event; }
    public void setEvent(String event) { this.event = event; }

    public String getMedal() { return medal; }
    public void setMedal(String medal) { this.medal = medal; }

    public String getGames() { return games; }
    public void setGames(String games) { this.games = games; }

    @Override
    public String toString() {
        return "Athlete{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", height=" + height +
                ", weight=" + weight +
                ", team='" + team + '\'' +
                ", noc='" + noc + '\'' +
                ", year=" + year +
                ", season='" + season + '\'' +
                ", city='" + city + '\'' +
                ", sport='" + sport + '\'' +
                ", event='" + event + '\'' +
                ", medal='" + medal + '\'' +
                ", games='" + games + '\'' +
                '}';
    }
}
