package com.ccc.logs.notifications;

import java.util.List;

public class KeywordDataList {
	private List<KeywordData> keywordList;
	private String relationship;
	
	public KeywordDataList(List<KeywordData> keywordList, String relationship) {
		this.keywordList = keywordList;
		this.relationship = relationship;
	}
	
	public List<KeywordData> getKeywordList() {
		return this.keywordList;
	}
	
	public String getRelationship() {
		return this.relationship;
	}
}
