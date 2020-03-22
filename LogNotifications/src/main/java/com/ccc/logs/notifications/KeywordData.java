package com.ccc.logs.notifications;

/**
 * Holds information on what keywords are to be searched for
 * and how many keywords must be found
 */
public class KeywordData {
	/**
	 * The list of keywords that will be searched for in the
	 * current set of log events
	 */
	private String[] wordList;
	
	/**
	 * Can have two values: "AND" or "OR". If "AND", all keywords
	 * must be present in a log message to trigger an alarm. If "OR",
	 * only one keyword must be present in a log message to trigger an
	 * alarm
	 */
	private String relationship;
	
	/**
	 * Constructs a keyword data object
	 * @param wordList The list of keywords
	 * @param relationship "AND" if all keywords must be found in the
	 * log message, "OR" if only one keyword must be found in the log
	 * message
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
	 * Returns "AND" if all keywords must be found,
	 * Returns "OR" if only one keywor must be found
	 * @return "AND" if all keywords must be found, "OR"
	 * if only one keyword must be found
	 */
	public String getRelationship() {
		return this.relationship;
	}
}
