package com.ccc.logs.notifications;

public class KeywordData {
	private Long keywordId;
	private String word;
	
	public KeywordData(Long keywordId, String word) {
		this.keywordId = keywordId;
		this.word = word;
	}
	
	public Long getKeywordId() {
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
