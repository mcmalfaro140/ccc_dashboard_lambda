package com.ccc.logs.notifications;

import java.util.Optional;

/**
 * Class container for function that searches
 * a log message for certain keywords
 */
class KeywordSearcher {
	/**
	 * Suppresses default constructor
	 */
	private KeywordSearcher() {
		throw new LogNotificationError("No instances of KeywordSearcher should be made");
	}
	
	/**
	 * Searches a log message for certain keywords
	 * and sees if it finds any
	 * @param message The log message to be searched
	 * @param nullableKeywordDataList The keyword information to be
	 * applied to the search
	 * @return <tt>true</tt> if the logs meets all of the criteria
	 * specified in the keyword data, <tt>false</tt> otherwise
	 */
	public static boolean search(String message, Optional<KeywordData> nullableKeywordData) {
		if (nullableKeywordData.isPresent()) {
			KeywordData keywordData = nullableKeywordData.get();
			
			switch (keywordData.getRelationship()) {
			case "ANY":
				return KeywordSearcher._anySearch(message, keywordData.getWordList());
			case "ALL":
				return KeywordSearcher._allSearch(message, keywordData.getWordList());
			default:
				throw new LogNotificationError("Invalid value for keyword relationship");
			}
		}
			
		return true;
	}
	
	/**
	 * Checks if all of the keywords appear in the log message
	 * @param message The message to be searched
	 * @param keywordList The keywords to searched for
	 * @return <tt>true</tt> if all keywords are present in the
	 * log message, <tt>false</tt> otherwise
	 */
	private static boolean _allSearch(String message, String[] wordList) {
		for (String word : wordList) {
			if (!KeywordSearcher._containsIgnoreCase(message, word)) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Checks if any of the keywords appear in the log message
	 * @param message The message to be searched
	 * @param keywordList The keywords to search for
	 * @return <tt>true</tt> if any keywords are present in the
	 * log message, <tt>false</tt> otherwise
	 */
	private static boolean _anySearch(String message, String[] wordList) {
		for (String word : wordList) {
			if (KeywordSearcher._containsIgnoreCase(message, word)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Performs a case-insensitive search for a word in a string
	 * @param str The string to be searched
	 * @param searchStr The word to search for
	 * @return <tt>true</tt> if the given word is contained in the
	 * given string, <tt>false</tt> otherwise
	 */
	private static boolean _containsIgnoreCase(String str, String searchStr) {
		final int lengthOfSearchStr = searchStr.length();

	    for (int i = str.length() - lengthOfSearchStr; i >= 0; i--) {
	        if (str.regionMatches(true, i, searchStr, 0, lengthOfSearchStr)) {
	            return true;
	        }
	    }
	    
	    return false;
	}
}
