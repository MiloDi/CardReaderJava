package org.milodi.cardreader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;

public class JSONReader {
	
	String file;
	

	public JSONReader(String file) {
		this.file=file;
	}	
	
	/**
	 * Create a deck out of the JSON file
	 * @return ArrayList<String> containing the cards
	 */
	public ArrayList<String> getDeck() {	
		return convertJSON(readJSON(file));
	}
	
	/**
	 * Open and read the JSON file
	 * @return JSONArray object
	 */
	private static JSONArray readJSON(String file_name) {
	    try {
	    	File file = new File("./sample-data/".concat(file_name));
	    	String content = FileUtils.readFileToString(file, "utf-8");
	    	JSONArray cardsJson = new JSONArray(content);
	    	return cardsJson;
	    } catch (IOException noFile) {
	        throw new IllegalArgumentException("File not found");
	    }
	}
	
	/**
	 * Covert the JSONArray object to an ArrayList<String> object
	 * @param JSONArray containing the JSON data
	 * @return ArrayList<String> object converted from the JSON data
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
}
