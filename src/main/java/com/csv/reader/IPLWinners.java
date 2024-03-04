package com.csv.reader;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class IPLWinners {

	public static void main(String[] args) {
		String csvFile = "C:\\Users\\Pavan\\Desktop\\Projects\\csv-reader\\src\\main\\resources\\ipl-matches.csv";
		int seasonIndex = -1, team1Index = -1, team2Index = -1, winnerIndex = -1;

		try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
			String[] headers = reader.readNext();
			for (int i = 0; i < headers.length; i++) {
				if (headers[i].equals("date")) {
					seasonIndex = i;
				} else if (headers[i].equals("team1")) {
					team1Index = i;
				} else if (headers[i].equals("team2")) {
					team2Index = i;
				} else if (headers[i].equals("winner")) {
					winnerIndex = i;
				}
			}

			if (seasonIndex == -1 || team1Index == -1 || team2Index == -1 || winnerIndex == -1) {
				throw new IllegalArgumentException("Required columns not found in the CSV file.");
			}

			Map<String, String> winners = new HashMap<>();
			Map<String, String> runnerUps = new HashMap<>();

			String[] record;
			while ((record = reader.readNext()) != null) {
				String season = record[seasonIndex];
				if (season != null && season.length() == 10) {
					season = season.substring(0, 4);
				}
				String team1 = record[team1Index];
				String team2 = record[team2Index];
				String winner = record[winnerIndex];

				if (!winner.equals("")) {
					winners.put(season, winner);
					runnerUps.put(season, team1.equals(winner) ? team2 : team1);
				}
			}

			for (String season : winners.keySet()) {
				String winner = winners.get(season);
				String runnerUp = runnerUps.get(season);

				System.out.println("Season: " + season);
				System.out.println("Winner: " + winner);
				System.out.println("Runner-up: " + runnerUp);
				System.out.println();
			}

		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}
	}
}
