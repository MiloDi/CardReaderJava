/**
 * 
 */
package org.milodi.cardreader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Milo Dietrick
 *
 */
public class Deck {
		
	public static ArrayList<String> deck;
	public static Boolean isValidDeck;
	
	
	public Deck() {
		deck = new ArrayList<String>();
	}
	
	/**
	 * Create a deck out of the provided JSON file name
	 * @param file_name
	 */
	public void createDeck(String file_name) {
		JSONReader json_array = new JSONReader( file_name );
		 Deck.deck = json_array.getDeck();
		 Deck.isValidDeck = isValidBatch();
	}
	
	/*
	 * TODO outputting incorrect values
	 */
	public static int calculateWaste() {
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
				
			} else if(red_cards.contains(suit) && red_cards.contains(next_suit) || black_cards.contains(suit) && black_cards.contains(next_suit)) {
				total_waste += 2 * Math.abs(cardValue(next_rank)- cardValue(rank) );				
				
			} else {
				total_waste += 3 * Math.abs(cardValue(next_rank)- cardValue(rank));	
			}
		}
		return total_waste;
	}
	
	/**
	 * Determine if the provided deck represents a valid deck of cards
	 * @return Boolean: Deck is valid or not
	 */
	private static Boolean isValidBatch() {		
		String[] suits = {"S", "H", "C", "D"};
		String[] ranks= {"10", "9", "8", "7", "6", "5", "4", "3", "2","A","K","Q","J"};
		ArrayList<String> valid_deck = new ArrayList<String>(52);
		ArrayList<String> given_deck = deck;

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
	

	/**
	 * Determine the value of the card
	 * @param rank: The rank of the card
	 * @return The rank value of the card
	 */
	public static int cardValue(String rank) {
		List<String> face_cards = Arrays.asList("J", "K", "Q");
		if (rank.compareTo("A") == 0) {
			return 1;
		} else if (face_cards.contains(rank)) {
			return 10;
		} else {
			return Integer.valueOf(rank);
		}
	}
	
	/**
	 * Print out the cards in the deck
	 */
	public static void printCards() {
		for (int i=0; i<deck.size(); i++ ) {
			System.out.println(deck.get(i));
		}
	}
}
