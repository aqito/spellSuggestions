package Coursework2;

import java.io.*;
import java.util.Scanner;

public class newsuggestions {
	public static void main(String[] args) throws IOException{
		String path = "C:\\Users\\gspri\\eclipse-workspace\\Coursework2\\src\\Coursework2\\";
		String filename = "Dictionary.txt";
		//file of original dictionary
		File file = new File(path+filename);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
//		buffered reader.
		File newFile = new File(path + "Spellchecked.txt");
		PrintWriter print = new PrintWriter(newFile);
//		new spellchecked file
		Scanner input = new Scanner(System.in);
		System.out.println("What file do you want to check");
		String checkFile = input.nextLine();
		File filein = new File(path+checkFile+".txt");
		FileReader frin = new FileReader(filein);
		BufferedReader brin = new BufferedReader(frin);
		//file to be checked.
		String wordFFile = null,checkWord = null;
		String check = null;
		boolean exit = true;
		//initialise variables
		while(exit) {
			while((checkWord = brin.readLine()) !=null) {
				if(checkDictionary(fr, br, checkWord, wordFFile)) {
					//call check method
					//if check = true program terminated
					add(print, checkWord);
					exit = false;
				}
				else {
					//else program not terminated.
					System.out.println(checkWord + " is not in the dictionary.");
					System.out.println("Here are suggested words.");
					levenshtein(fr, br, checkWord, wordFFile);
					System.out.println("Type 'add' if you want to add it to the dictionary anyway.");
					//add the word into the dictionary.
					System.out.println("Type 'alt' alternative spelling for this word");
					System.out.println("Type 'exit' if you want to exit");
					//exit program

					check = input.nextLine();

					if(check.equals("exit")) {
						exit = false;
						//exit program
					}
					else if(check.equals("add")) {
						add(print, checkWord);
						//call add method
						exit = false;
						//exit program
					}
					else if(check.equals("alt")) {
						System.out.println("Type alternative spelling here");
						checkWord = input.nextLine();
						add(print, checkWord);
						// call add method to add alternative spelling.
					}
					
				else {
						System.out.println("Try again");
						check = input.nextLine();
					}
				}
			
		}
		System.out.println("Thank you...");
		input.close();
		brin.close();
		print.close();
	}
	}

	public static boolean checkDictionary(FileReader fr,BufferedReader br, String checkword, String wordFFile) throws IOException {
		String path = "C:\\Users\\gspri\\eclipse-workspace\\Coursework2\\src\\Coursework2\\";
		String filename = "Dictionary.txt";
		//file of original dictionary
		File file = new File(path+filename);
		fr = new FileReader(file);
		br = new BufferedReader(fr);
		wordFFile = null;
		//refresh BufferedReader		
		boolean tof = false;
		while((wordFFile = br.readLine()) !=null && tof == false) {
			//iterate over dictionary checking every word.
			if(checkword.equals(wordFFile)) {
				tof = true;
				//if word found in dictionary exit.
			}
		}
		br.close();
		return tof;
	}

	public static void add(PrintWriter print, String checkedWord) throws IOException {
		print.println(checkedWord);
	}

	public static void levenshtein(FileReader fr,BufferedReader br, String checkWord, String wordFFile) throws IOException {
		String path = "C:\\Users\\gspri\\eclipse-workspace\\Coursework2\\src\\Coursework2\\";
		String filename = "Dictionary.txt";
		//file of original dictionary
		File file = new File(path+filename);
		fr = new FileReader(file);
		br = new BufferedReader(fr);
		wordFFile = null;
		//refresh BufferedReader

		while((wordFFile = br.readLine()) !=null) {
			if(distance(checkWord, wordFFile) < 2) {
				System.out.println(wordFFile);
				//print out suggested words
			}
		}
		br.close();
	}

	public static int distance(String a, String b) {
		a = a.toLowerCase();
		b = b.toLowerCase();
		//make sure strings are all lower case
		int [] costs = new int [b.length() + 1];
		//make costs.

		for (int j = 0; j < costs.length; j++)

			costs[j] = j;
		//index costs array

		for (int i = 1; i <= a.length(); i++) {
			costs[0] = i;
			int nw = i - 1;
			for (int j = 1; j <= b.length(); j++) {
				int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
				nw = costs[j];
				costs[j] = cj;
			}
		}
		return costs[b.length()];
		//return cost of difference between 2 string
	}
}

