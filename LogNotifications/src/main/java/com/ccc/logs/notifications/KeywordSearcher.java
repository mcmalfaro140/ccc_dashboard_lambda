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
	public static boolean search(String message, String[] keywordList) {
		if (null == keywordList) {
			return true;
		}
		else {
			for (String keyword : keywordList) {
				if (!message.contains(keyword)) {
					return false;
				}
			}
			
			return true;
		}
	}
}
