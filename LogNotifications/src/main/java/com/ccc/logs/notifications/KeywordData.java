package com.ccc.logs.notifications;

public class KeywordData {
	private int keywordId;
	private String word;
	
	public KeywordData(int keywordId, String word) {
		this.keywordId = keywordId;
		this.word = word;
	}
	
	public int getKeywordId() {
		return this.keywordId;
	}
	
	public String getWord() {
		return this.word;
	}
	
	@Override
	public String toString() {
		return JsonConverter.prettyStringify(this);
	}
}
