import java.util.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class Settings {

	// public static HashMap<String, Integer> optionMap = new HashMap<String,
	// Integer>();
	public static String[] optionString = { "Map Size", "Tile Size",
			"Default Snake Length", "Speed", "Growth Per Apple" };

	public static String[] highScoreNames = new String[5];
	public static int[] highScoreValues = new int[5];
	public static int mapSize = 50;
	public static int tileSize = 10;
	public static int defaultSnakeLegnth = 15;
	public static int speed = 100;
	public static int gpa = 5;

	public static void populateHighScores() {
		URL urlHighscores;
		try {
			urlHighscores = new URL("http://www.nicksapplets.net23.net/snake/highscores.html");
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlHighscores.openStream()));
			String s;
			int num = 1;
			while ((s = reader.readLine()) != null) {
				String[] hsLine = s.split(" ");
				if(hsLine[0].equals("end"))
					break;
				highScoreNames[num - 1] = hsLine[0];
				highScoreValues[num - 1] = Integer.parseInt(hsLine[1]);
				num++;
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		try {
			File file = new File("highscores.txt");
			if (!file.exists())
				file.createNewFile();
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String s;
			int num = 1;
			while ((s = reader.readLine()) != null) {
				String[] hsLine = s.split(" ");
				highScoreNames[num - 1] = hsLine[0];
				highScoreValues[num - 1] = Integer.parseInt(hsLine[1]);
				num++;
			}

			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}

	public static void newHighScore(String name, int score) {
		for (int i = 0; i < highScoreValues.length; i++) {
			if (score > highScoreValues[i]) {
				for (int j = highScoreValues.length - 1; j > i; j--) {
					if (highScoreValues[j-1] != 0) {
						highScoreNames[j] = highScoreNames[j-1];
						highScoreValues[j] = highScoreValues[j-1];
					}
				}
				highScoreNames[i] = name;
				highScoreValues[i] = score;
				break;
			}
		}
		saveHighScores();
	}

	public static void saveHighScores() {
		/*
		try {
			File file = new File("highscores.txt");
			if (!file.exists())
				file.createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			for (int i = 0; i < highScoreNames.length; i++) {
				if (highScoreNames[i] != null) {
					writer.write(highScoreNames[i] + " " + highScoreValues[i]);
					writer.newLine();
				}
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}

}
