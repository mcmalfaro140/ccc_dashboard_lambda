package com.ccc.lambda;

public class LevelComparer {	
	private LevelComparer() {
	}
	
	public static int compare(String levelOfEvent, String levelOfAlarm) {
		int eventRating = LevelComparer._getRating(levelOfEvent);
		int alarmRating = LevelComparer._getRating(levelOfAlarm);
		
		return eventRating - alarmRating;
	}
	
	private static int _getRating(String level) {
		switch (level) {
		case "TRACE":
			return 0;
		case "DEBUG":
			return 1;
		case "INFO":
			return 2;
		case "WARN":
			return 3;
		default: // ERROR
			return 4;
		}
	}
}
