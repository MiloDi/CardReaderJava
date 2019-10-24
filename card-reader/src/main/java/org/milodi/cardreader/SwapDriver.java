package org.milodi.cardreader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.apache.commons.math3.util.CombinatoricsUtils;

public class SwapDriver {

	int num_cards, num_swap_postions;
	static int minimum_waste;
	static String swap_pos1 = "";
	static String swap_pos2 = "";
	static Deck cards;
	static ArrayList<int[]> possible_combinations = new ArrayList<int[]>();
		
	/**
	 * 
	 * @param n
	 * @param r
	 * @param cards
	 */
	public SwapDriver(int n, int r, Deck cards) {
		this.num_cards = n;
		this.num_swap_postions = r;
		SwapDriver.cards = cards;
		minimum_waste = Deck.calculateWaste();
		generate();
		runSwap();
	}
	
	/**
	 * Generate swap positions
	 * https://www.baeldung.com/java-combinations-algorithm
	 * @param n: number of cards
	 * @param r: number of positions to swap
	 * @return ArrayList<int[]>: possible swap positions
	 */
	private ArrayList<int[]> generate() {;		
	    Iterator<int[]> iterator = CombinatoricsUtils.combinationsIterator(num_cards, num_swap_postions);
	    while (iterator.hasNext()) {
	        final int[] combination = iterator.next();
	        possible_combinations.add(combination);
	    }
	    return possible_combinations;
	}
	/**
	 * 
	 * @param combination
	 * @return
	 */
	private static int swapCards ( int[] combination ){
	    for(int i =0; i < combination.length-1; i++) {
	    	Collections.swap(Deck.deck, combination[i], combination[i+1]);
	    }
	    int newWaste = Deck.calculateWaste();
		return newWaste;
	}
	
	/**
	 * 
	 */		
	public static void runSwap() {		
		if(!Deck.isValidDeck) {
			System.out.println("WARNING: This is not a valid deck.");
		}
    	for (int[] combination : possible_combinations) {
    	    int calculated_waste = swapCards(combination);
    	    if(minimum_waste < calculated_waste) {
    	    	minimum_waste = calculated_waste;
    	    	swap_pos1 = cards.deck.get(combination[0]);
    	    	swap_pos2 = cards.deck.get(combination[1]);
    	    }	    
    	}	
    	
	}
	
}
