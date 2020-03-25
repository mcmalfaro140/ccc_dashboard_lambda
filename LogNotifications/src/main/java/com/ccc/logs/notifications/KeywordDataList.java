package com.ccc.logs.notifications;

import java.util.LinkedList;

public class KeywordDataList {
	private LinkedList<KeywordData> keywordList;
	private String relationship;
	
	public KeywordDataList(LinkedList<KeywordData> keywordList, String relationship) {
		this.keywordList = keywordList;
		this.relationship = relationship;
	}
	
	public LinkedList<KeywordData> getKeywordList() {
		return this.keywordList;
	}
	
	public String getRelationship() {
		return this.relationship;
	}
}
