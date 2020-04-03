package com.ccc.logs.notifications;

import java.util.LinkedList;
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
	public static boolean search(String message, Optional<KeywordDataList> nullableKeywordDataList) {
		if (nullableKeywordDataList.isPresent()) {
			KeywordDataList keywordDataList = nullableKeywordDataList.get();
			
			switch (keywordDataList.getRelationship()) {
			case "ANY":
				return KeywordSearcher._anySearch(message, keywordDataList.getKeywordList());
			case "ALL":
				return KeywordSearcher._allSearch(message, keywordDataList.getKeywordList());
			default:
				throw new LogNotificationException("Invalid value for keyword relationship");
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
	private static boolean _allSearch(String message, LinkedList<KeywordData> keywordList) {
		for (KeywordData keyword : keywordList) {
			if (!KeywordSearcher._containsIgnoreCase(message, keyword.getWord())) {
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
	private static boolean _anySearch(String message, LinkedList<KeywordData> keywordList) {
		for (KeywordData keyword : keywordList) {
			if (KeywordSearcher._containsIgnoreCase(message, keyword.getWord())) {
				return true;
			}
		}
		
		return false;
	}
	
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
