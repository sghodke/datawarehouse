package edu.buffalo.ktb.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Converter {

	static final String PATH = "/home/sid/Documents/dm/Data_For_Project1";

	public static void main(String[] args) {

		FileReader fr;
		BufferedReader br;
		FileWriter fw;

		final File fileDir = new File(PATH);
		try {
			String line;
			StringBuilder sb;
			for (File file : fileDir.listFiles()) {
				if (!file.isDirectory()) {
					fr = new FileReader(file);
					br = new BufferedReader(fr);
					sb = new StringBuilder();
					while ((line = br.readLine()) != null) {
						line = line.replaceAll("\t", ",");
						sb = sb.append(line + "\n");
					}

					fw = new FileWriter(PATH + "/csv/" + file.getName().replaceAll("txt", "csv"));
					fw.write(sb.toString());
					fw.close();
					fr.close();
					br.close();
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}
}
