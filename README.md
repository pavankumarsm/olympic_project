# Olympics Data Analysis (Java)
About the Project

This project is a Java-based data analysis application that processes Olympic Games data.
It reads athlete and NOC (National Olympic Committee) datasets in CSV format, cleans the data, and performs multiple analytical tasks such as:

- Counting total athletes and countries (NOCs).

- Finding year-wise gold medal winners.

- Identifying the top gold medalist for each year.

- Tracking athletes under 30 who won gold in 1980.

- Summarizing event-wise medal distribution.

- Listing football gold medal winners across Olympics.

- Highlighting the female athlete with the most gold medals.

- Detecting athletes who participated in more than 3 Olympic Games.

- Displaying a country-wise medal tally for a given year.

The goal of this project is to practice Java file handling, collections, and data structures while exploring real-world sports data. It’s ideal for students, beginners, and anyone interested in Java + data analysis.


## 📂 Project Structure
<pre>

  olympics/
│── src/
│   ├── olympics/
│   │   ├── Main.java        # Main program with analysis functions
│   │   ├── Athlete.java     # POJO for athlete details
│   │   ├── NocRegion.java   # POJO for NOC details
│── Resources/
│   ├── athlete_events.csv   # Athlete data (from Kaggle)
│   ├── noc_regions.csv      # NOC region mapping
│── README.md

</pre>



## 🏗️ How It Works

Reads CSV files using BufferedReader.

Cleans and parses the data into Athlete and NocRegion objects.

Uses Java Collections (Map, Set, List) to compute results.

Displays outputs in the console.

