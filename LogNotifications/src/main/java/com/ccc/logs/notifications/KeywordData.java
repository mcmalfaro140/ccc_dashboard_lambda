package com.ccc.logs.notifications;

/**
 * Contains information on the list of keywords to be
 * searched for and whether or not all must be found or just one
 */
public class KeywordData {
	/**
	 * The list of keywords to be searched for
	 */
	private String[] wordList;
	
	/**
	 * Indicates whether or not all keywords
	 * must be found or just one
	 */
	private String relationship;
	
	/**
	 * Constructs an object representing keyword
	 * data
	 * @param wordList The list of keywords to search
	 * for
	 * @param relationship "ANY" if one keyword must be found,
	 * "ALL" if all keywords must be found
	 */
	public KeywordData(String[] wordList, String relationship) {
		this.wordList = wordList;
		this.relationship = relationship;
	}
	
	/**
	 * Returns the list of keywords to be searched for
	 * @return The list of keywords to be searched for
	 */
	public String[] getWordList() {
		return this.wordList;
	}
	
	/**
	 * Returns "ANY" if one keyword must be found,
	 * "ALL" if all keywords must be found
	 * @return "ANY" if one keyword must be found,
	 * "ALL" if all keywords must be found
	 */
	public String getRelationship() {
		return this.relationship;
	}
	
	/**
	 * Returns a string representation of this object
	 * as a pretty printed JSON string. Used solely for
	 * testing purposes
	 * @return A string representation of this object
	 * as a pretty printed JSON string
	 */
	@Override
	public String toString() {
		return JsonConverter.prettyStringify(this);
	}
}
