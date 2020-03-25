package com.ccc.logs.notifications;

import java.util.LinkedList;

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
	 * @param keywordData The keyword information to be
	 * applied to the search
	 * @return <tt>true</tt> if the logs meets all of the criteria
	 * specified in the keyword data, <tt>false</tt> otherwise
	 */
	public static boolean search(String message, KeywordDataList keywordDataList) {
		if (null == keywordDataList) {
			return true;
		}
		else {
			switch (keywordDataList.getRelationship()) {
			case "AND":
				return KeywordSearcher._andSearch(message, keywordDataList.getKeywordList());
			case "OR":
				return KeywordSearcher._orSearch(message, keywordDataList.getKeywordList());
			default:
				throw new LogNotificationException("Invalid value for keyword relationship");
			}
		}
	}
	
	/**
	 * Checks if all of the keywords appear in the log message
	 * @param message The message to be searched
	 * @param keywordList The keywords to searched for
	 * @return <tt>true</tt> if all keywords are present in the
	 * log message, <tt>false</tt> otherwise
	 */
	private static boolean _andSearch(String message, LinkedList<KeywordData> keywordList) {
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
	private static boolean _orSearch(String message, LinkedList<KeywordData> keywordList) {
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
