package com.ccc.logs.notifications;

public class KeywordData {
	private String[] wordList;
	private String relationship;
	
	public KeywordData(String[] wordList, String relationship) {
		this.wordList = wordList;
		this.relationship = relationship;
	}
	
	public String[] getWordList() {
		return this.wordList;
	}
	
	public String getRelationship() {
		return this.relationship;
	}
}
