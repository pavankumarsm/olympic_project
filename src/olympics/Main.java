package olympics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    public static final int ATHLETE_ID_COLUMN = 0;
    public static final int ATHLETE_NAME_COLUMN = 1;
    public static final int ATHLETE_SEX_COLUMN = 2;
    public static final int ATHLETE_AGE_COLUMN = 3;
    public static final int ATHLETE_HEIGHT_COLUMN = 4;
    public static final int ATHLETE_WEIGHT_COLUMN = 5;
    public static final int ATHLETE_TEAM_COLUMN = 6;
    public static final int ATHLETE_NOC_COLUMN = 7;
    public static final int ATHLETE_GAMES_COLUMN = 8;
    public static final int ATHLETE_YEAR_COLUMN = 9;
    public static final int ATHLETE_SEASON_COLUMN = 10;
    public static final int ATHLETE_CITY_COLUMN = 11;
    public static final int ATHLETE_SPORT_COLUMN = 12;
    public static final int ATHLETE_EVENT_COLUMN = 13;
    public static final int ATHLETE_MEDAL_COLUMN = 14;

    public static final int NOC_CODE_COLUMN = 0;
    public static final int NOC_REGION_COLUMN = 1;
    public static final int NOC_NOTES_COLUMN = 2;

    private static int parseIntSafe(String s) {
        if (s == null || s.isEmpty() || s.equalsIgnoreCase("NA")) return 0;
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static String clean(String value) {
        if (value == null) return "";
        return value.replaceAll("^\"|\"$", "").trim();
    }

    private static List<Athlete> fetchAthletes(String filePath) throws IOException {
        List<Athlete> athletes = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            bufferedReader.readLine(); // skip header
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                if (data.length >= 15) {
                    athletes.add(new Athlete(
                            parseIntSafe(data[ATHLETE_ID_COLUMN]),
                            clean(data[ATHLETE_NAME_COLUMN]),
                            clean(data[ATHLETE_SEX_COLUMN]),
                            parseIntSafe(data[ATHLETE_AGE_COLUMN]),
                            parseIntSafe(data[ATHLETE_HEIGHT_COLUMN]),
                            parseIntSafe(data[ATHLETE_WEIGHT_COLUMN]),
                            clean(data[ATHLETE_TEAM_COLUMN]),
                            clean(data[ATHLETE_NOC_COLUMN]),
                            parseIntSafe(data[ATHLETE_YEAR_COLUMN]),
                            clean(data[ATHLETE_SEASON_COLUMN]),
                            clean(data[ATHLETE_CITY_COLUMN]),
                            clean(data[ATHLETE_SPORT_COLUMN]),
                            clean(data[ATHLETE_EVENT_COLUMN]),
                            clean(data[ATHLETE_MEDAL_COLUMN]),
                            clean(data[ATHLETE_GAMES_COLUMN])
                    ));
                }
            }
        }
        return athletes;
    }

    private static List<NocRegion> fetchNOCRegions(String filePath) throws IOException {
        List<NocRegion> nocs = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            bufferedReader.readLine(); // skip header
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                if (data.length >= 2) {
                    String notes = data.length > 2 ? clean(data[NOC_NOTES_COLUMN]) : "";
                    nocs.add(new NocRegion(
                            clean(data[NOC_CODE_COLUMN]),
                            clean(data[NOC_REGION_COLUMN]),
                            notes
                    ));
                }
            }
        }
        return nocs;
    }


    private static void main(String[] args) throws IOException {
        String athletesFile = "src/Resources/athlete_events.csv";
        String nocFile = "src/Resources/noc_regions.csv";

        List<Athlete> athletes = fetchAthletes(athletesFile);
        List<NocRegion> nocs = fetchNOCRegions(nocFile);

        System.out.println("Total Athletes: " + athletes.size());
        System.out.println("Total NOCs: " + nocs.size());

        findTheGoldMedalsWinnerYearWiseEachPlayer(athletes);
         findTopGoldMedalistEachYear(athletes);
        findAthleteWonGoldMedalIn1980AndAgeIsLessThan30(athletes);
        findEventWiseMedalsIn1980(athletes);
        findGoldMedalInFootballInOlympic(athletes);
        findFemaleAthleteWonMaxGold(athletes);
        findAthleteParticipatedMorethanThreeOlympics(athletes);
        findCountryWiseMedalTallyIn2016(athletes);
    }

    private static void findTheGoldMedalsWinnerYearWiseEachPlayer(List<Athlete> athletes) {
        if (athletes == null || athletes.isEmpty()) return;

        Map<Integer, Map<String, Integer>> yearWiseGoldMap = new TreeMap<>();

        for (Athlete athlete : athletes) {
            String medal = athlete.getMedal();
            String playerName = athlete.getName();

            if ("Gold".equalsIgnoreCase(medal) && playerName != null && !playerName.isBlank()) {
                int year = athlete.getYear();
                Map<String, Integer> playerGoldMap = yearWiseGoldMap.computeIfAbsent(year, k -> new TreeMap<>());
                playerGoldMap.put(playerName, playerGoldMap.getOrDefault(playerName, 0) + 1);
            }
        }

        // Print results
        for (Map.Entry<Integer, Map<String, Integer>> yearEntry : yearWiseGoldMap.entrySet()) {
            System.out.println("==== " + yearEntry.getKey() + " ====");
            for (Map.Entry<String, Integer> playerEntry : yearEntry.getValue().entrySet()) {
                System.out.println(" player Name  " + playerEntry.getKey() + " -> " + playerEntry.getValue());
            }
        }
        System.out.println();
    }

    private static void findTopGoldMedalistEachYear(List<Athlete> athletes) {
        if (athletes == null || athletes.isEmpty()) return;

        Map<Integer, Map<String, Integer>> yearWiseGoldMap = new TreeMap<>();

        for (Athlete athlete : athletes) {
            if ("Gold".equalsIgnoreCase(athlete.getMedal()) && athlete.getName() != null && !athlete.getName().isBlank()) {
                int year = athlete.getYear();
                yearWiseGoldMap.computeIfAbsent(year, k -> new HashMap<>())
                        .merge(athlete.getName(), 1, Integer::sum);
            }
        }

        for (Map.Entry<Integer, Map<String, Integer>> yearEntry : yearWiseGoldMap.entrySet()) {
            String topPlayer = null;
            int maxGolds = 0;
            for (Map.Entry<String, Integer> entry : yearEntry.getValue().entrySet()) {
                if (entry.getValue() > maxGolds) {
                    maxGolds = entry.getValue();
                    topPlayer = entry.getKey();
                }
            }
            System.out.println(yearEntry.getKey() + " -> " + topPlayer + " (" + maxGolds + " Golds)");
        }
        System.out.println();
    }

    public static void findAthleteWonGoldMedalIn1980AndAgeIsLessThan30(List<Athlete> athletes) {
        if (athletes == null || athletes.isEmpty()) return;

        Set<String> athleteNameWonGold = new TreeSet<>();
        for(Athlete athlete:athletes){
        String athleteName = athlete.getName();
        int age = athlete.getAge();
        if(athleteName!=null && athlete.getYear()==1980 && age<30 && "Gold".equalsIgnoreCase(athlete.getMedal())){
            athleteNameWonGold.add(athleteName);
            }
        }
//        System.out.println(athleteNameWonGold.size());
        for(String playerName : athleteNameWonGold){
            System.out.println(playerName);
        }
        System.out.println();
    }

    public static void findEventWiseMedalsIn1980(List<Athlete> athletes) {
        if (athletes == null || athletes.isEmpty()) return;

        Map<String,Map<String,Integer>> eventWiseMedal = new HashMap<>();
        for(Athlete athlete:athletes){
            String athleteName = athlete.getName();
            String eventName = athlete.getEvent();
            String medal = athlete.getMedal();

            if(medal!= null && athleteName!= null && athlete.getYear()==1980 && eventName != null && !medal.equals("NA")){
                Map<String,Integer> medalName = eventWiseMedal.computeIfAbsent(eventName,k->new HashMap<>());
                medalName.put(medal ,medalName.getOrDefault(medal,0)+1);
            }
        }

        for(Map.Entry<String,Map<String,Integer>> outerMap : eventWiseMedal.entrySet()){
            String event = outerMap.getKey();

           Map<String,Integer> entry = outerMap.getValue();
           int gold = entry.getOrDefault("Gold", 0);
            int silver = entry.getOrDefault("Silver", 0);
            int Bronze = entry.getOrDefault("Bronze", 0);

            System.out.println("    "+ event+ "==="+ "Gold ->"+gold+ " Silver-> "+ silver + " Bronze-> "+ Bronze);
        }
        System.out.println();
    }

    private static void findGoldMedalInFootballInOlympic(List<Athlete> athletes) {
        if(athletes != null && athletes.isEmpty()) return;

        Map<Integer,String> goldMedalInFootball = new TreeMap<>();
        for(Athlete athlete: athletes){
            if("FootBall".equalsIgnoreCase(athlete.getSport()) && "Gold".equalsIgnoreCase(athlete.getMedal())) {
                String team = athlete.getTeam();
                int year = athlete.getYear();
                goldMedalInFootball.put(year, team);
            }
        }
        for(Map.Entry<Integer,String> entry : goldMedalInFootball.entrySet()){
            System.out.println(entry.getKey()+" ->  "+ entry.getValue());
        }
        System.out.println();
    }

    public static void findFemaleAthleteWonMaxGold(List<Athlete> athletes) {
        Map<String,Integer> maxGoldFemaleAthlete = new HashMap<>();

        for(Athlete athlete:athletes){
            if("F".equalsIgnoreCase(athlete.getSex()) && athlete.getName() != null){
                maxGoldFemaleAthlete.put(athlete.getName(),maxGoldFemaleAthlete.getOrDefault(athlete.getName(),0)+1);
            }
        }
        String athleteName = null;
        int maxCount = 0;
        for(Map.Entry<String,Integer> entry : maxGoldFemaleAthlete.entrySet()){
            if(entry.getValue()>maxCount) {
                maxCount = entry.getValue();
                athleteName = entry.getKey();
            }
        }
        System.out.println("Athlete Name"+athleteName+"->"+ maxCount);
        System.out.println();
    }

    public static void findAthleteParticipatedMorethanThreeOlympics(List<Athlete> athletes) {

        Map<String,Map<Integer,Boolean>> athleteNamePlayedMoreThanThree = new TreeMap<>();
        for(Athlete athlete:athletes){
            String athleteName = athlete.getName();
            String games = athlete.getGames();
            int year = athlete.getYear();
            if(athleteName!= null && games != null ){
                Map<Integer,Boolean> playedMoreThanThree = athleteNamePlayedMoreThanThree.computeIfAbsent(athleteName,k-> new TreeMap<>());
                playedMoreThanThree.put(year,true);
            }
        }
        for(Map.Entry<String,Map<Integer,Boolean>> entry : athleteNamePlayedMoreThanThree.entrySet()){
            if(entry.getValue().size() >3){
                System.out.println(entry.getKey()+" -> "+ entry.getValue().size());
            }
        }
        System.out.println();
    }

    public static void findCountryWiseMedalTallyIn2016(List<Athlete> athletes) {
       Map<String,Map<String,Integer>> countryWiseMedal = new HashMap<>();
       for(Athlete athlete:athletes){
           String countryName = athlete.getNoc();
           String medal = athlete.getMedal();
           if(countryName!= null && medal != null && athlete.getYear()==2008 && !medal.equalsIgnoreCase("NA")){
               Map<String,Integer> medalTally = countryWiseMedal.computeIfAbsent(countryName,k-> new HashMap<>());
               medalTally.put(medal,medalTally.getOrDefault(medal,0)+1);
           }
       }
       for(Map.Entry<String,Map<String,Integer>> outerMap : countryWiseMedal.entrySet()){
           String country = outerMap.getKey();
           Map<String,Integer> entry = outerMap.getValue();
           int gold = entry.getOrDefault("Gold", 0);
           int silver = entry.getOrDefault("Silver", 0);
           int Bronze = entry.getOrDefault("Bronze", 0);

           System.out.println(country + " === "+ "Gold ->"+gold+ " Silver-> "+ silver + " Bronze-> "+ Bronze);
       }
        System.out.println();
    }
}
