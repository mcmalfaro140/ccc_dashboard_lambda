package com.ccc.logs.notifications;

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
	 * @param keywordList The list of keywords to search
	 * for
	 * @return <tt>true</tt> if all of the given keywords
	 * are found in the log message, <tt>false</tt> otherwise
	 */
	public static boolean search(String message, KeywordData keywordData) {
		if (null == keywordData) {
			return true;
		}
		else {
			switch (keywordData.getRelationship()) {
			case "AND":
				return KeywordSearcher._andSearch(message, keywordData.getWordList());
			case "OR":
				return KeywordSearcher._orSearch(message, keywordData.getWordList());
			default:
				throw new InternalError("Invalid value for keyword relationship");
			}
		}
	}
	
	private static boolean _andSearch(String message, String[] keywordList) {
		for (String keyword : keywordList) {
			if (!KeywordSearcher._containsIgnoreCase(message, keyword)) {
				return false;
			}
		}
		
		return true;
	}
	
	private static boolean _orSearch(String message, String[] keywordList) {
		for (String keyword : keywordList) {
			if (KeywordSearcher._containsIgnoreCase(message, keyword)) {
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
