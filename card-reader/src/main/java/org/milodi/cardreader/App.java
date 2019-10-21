package org.milodi.cardreader;
import org.json.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.util.CombinatoricsUtils;


/**
 * @author Milo Dietrick
 *
 */
//public class deck
//{
//
//	p
//	
//	
//}



public class App 
{
	private static Boolean isValidBatch(ArrayList deck) {
		
		String[] suits = {"S", "H", "C", "D"};
		String[] ranks= {"10", "9", "8", "7", "6", "5", "4", "3", "2","A","K","Q","J"};
		ArrayList<String> valid_deck = new ArrayList<String>(52);
		ArrayList<String> given_deck = (ArrayList<String>) deck.clone();
		//Create a valid deck
		for (String s:suits) {
			for(String r:ranks) {
				String new_card  = r.concat(s);
				valid_deck.add(new_card);
			}
		}
		//Sort the decks and compare
		Collections.sort(valid_deck);
		Collections.sort(given_deck);
		return given_deck.equals(valid_deck);
    } 
	
	private static int cardValue(String rank) {
		List<String> face_cards = Arrays.asList("J", "K", "Q");
		if (rank.compareTo("A") == 0) {
			return 1;
		} else if (face_cards.contains(rank)) {
			return 10;
		} else {
			return Integer.valueOf(rank);
		}
	}
	
	/*
	 * TODO outputting incorrect values
	 */
	private static int calculateWaste(ArrayList<String> deck) {
		List<String> red_cards = Arrays.asList("H", "D");
		List<String> black_cards = Arrays.asList("C", "S");		
		int total_waste = 0;
		
		for (int i = 0; i < deck.size()-1; i++) {			
			String rank = StringUtils.chop(deck.get(i));
			String next_rank = StringUtils.chop(deck.get(i+1));			
			String suit = StringUtils.substring(deck.get(i), deck.get(i).length()-1, deck.get(i).length());
			String next_suit = StringUtils.substring(deck.get(i+1), deck.get(i+1).length()-1, deck.get(i+1).length());
			
			if (suit == next_suit) {
				total_waste += Math.abs(cardValue(next_rank)- cardValue(rank));
				
			} else if(red_cards.contains(suit) && red_cards.contains(next_suit) 
					|| black_cards.contains(suit) && black_cards.contains(next_suit)) {
				total_waste += 2 * Math.abs(cardValue(next_rank)- cardValue(rank) );
				
			} else {
				total_waste += 3 * Math.abs(cardValue(next_rank)- cardValue(rank));	
			}
		}

		return total_waste;
	}

	
	/**
	 * 
	 * @return
	 */
	private static JSONArray readJSON() {
	    try {
	    	File file = new File("./sample-data/batch-00.json");
	    	String content = FileUtils.readFileToString(file, "utf-8");
	    	JSONArray cardsJson = new JSONArray(content);
	    	return cardsJson;
	    } catch (IOException noFile) {
	        throw new IllegalArgumentException("File not found");
	    }
	}
	
	/**
	 * 
	 * @param 
	 * @return
	 */
	private static ArrayList<String> convertJSON(JSONArray ar) {
		ArrayList<String> deck = new ArrayList<String>(ar.length());
		//Convert JSON deck
		for (int i = 0; i < ar.length(); i++) {
			String card = (String) ar.get(i);
			deck.add(card);
		}
		return deck;
	}
	

	/**
	 * Generate swap positions
	 * https://www.baeldung.com/java-combinations-algorithm
	 * @param n: number of cards
	 * @param r: number of positions to swap
	 * @return ArrayList<int[]>: possible swap positions
	 */
	public static ArrayList<int[]> generate(int n, int r) {
		ArrayList<int[]> possible_combinations = new ArrayList<int[]>();
		
	    Iterator<int[]> iterator = CombinatoricsUtils.combinationsIterator(n, r);
	    while (iterator.hasNext()) {
	        final int[] combination = iterator.next();
	        possible_combinations.add(combination);
	    }
	    return possible_combinations;
	}
	
/**
 * Swap the cards
 */
	public static int swapCards(ArrayList<String> deck, int[] combination) {
		System.out.println(Arrays.toString(combination));
	    for(int i =0; i < combination.length-1; i++) {
	    	Collections.swap(deck, combination[i], combination[i+1]);
	    }
		return calculateWaste(deck);
	}
	
	
	public static int swapDriver(ArrayList<String> deck) {
		
    	int N =52;
    	int R = 2;
    	int minimum_waste = calculateWaste(deck);  	
    	String swap_pos1, swap_pos2;
    	ArrayList<int[]> swap_postions = generate(N, R);
    	
    	for (int[] combination : swap_postions) {
//    	    System.out.println(Arrays.toString(combination));
    	    int calculated_waste = swapCards(deck, combination);
    	    if(minimum_waste < calculated_waste) {
    	    	minimum_waste = calculated_waste;
    	    	swap_pos1 = deck.get(combination[0]);
    	    	swap_pos1 = deck.get(combination[1]);
//    	    	System.out.println(Arrays.toString(minimum_waste));
    	    }	    
    	}	
    	
    	return minimum_waste;
	}
	
	
	
    public static void main( String[] args )
    {
    	
//        System.out.println( "Hello World!" );
    	ArrayList<String> deck = convertJSON(readJSON());

//		System.out.println(isValidBatch(deck));
		System.out.println(calculateWaste(deck));
		
		int swaps = swapDriver(deck);
    }
}
