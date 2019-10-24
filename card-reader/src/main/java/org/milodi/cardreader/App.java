package org.milodi.cardreader;

import java.text.MessageFormat;

/**
 * @author Milo Dietrick
 *
 */




public class App 
{	
	
    public static void main( String[] args )
    {
    	
    	Deck cards = new Deck();
    	cards.createDeck("batch-00.json" );

		System.out.println(Deck.isValidDeck);
		System.out.println("Initial calculated waste:");
		System.out.println(cards.calculateWaste());


		
		System.out.println("Single Swap:");
		SwapDriver swaps = new SwapDriver(52, 2, cards);	
		System.out.println(MessageFormat.format("By swapping {0} and {1} we can reduce the waste metric from {2} to {3}",
				swaps.swap_pos1, swaps.swap_pos2, cards.calculateWaste(), swaps.minimum_waste));
		
		
		System.out.println("Single Swap:");
		SwapDriver double_swap = new SwapDriver(52, 4, cards);	
		System.out.println(MessageFormat.format("By swapping {0} and {1} we can reduce the waste metric from {2} to {3}",
				double_swap.swap_pos1, double_swap.swap_pos2, cards.calculateWaste(), double_swap.minimum_waste));
    }
}
