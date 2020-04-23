package com.ccc.logs.notifications;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Represents the possible log levels for
 * each log level. The values must be
 * written from top to bottom from least
 * severe to most severe
 */
@JsonFormat(shape=JsonFormat.Shape.STRING)
enum LogLevel {
	/**
	 * Used for very detailed, high-volume logging.
	 * Should not be used very often, even during
	 * normal development. Examples include dumping
	 * a full object hierarchy, logging some state
	 * during every iteration of a large loop, etc
	 */
	TRACE,
	
	/**
	 * Any message that is helpful in tracking the flow
	 * through the system and isolating issues, especially
	 * during the development and QA phases. Just about everything
	 * that doesn't make the "INFO" cut
	 */
	DEBUG,
	
	/**
	 * Things we want to see at high volume in case you
	 * need to forensically analyze an issue. Typical
	 * business exceptions and any other event you think
	 * you'll need to see in production at high volume
	 * can go here
	 */
	INFO,
	
	/**
	 * An unexpected technical or business event happened,
	 * customers may be affected, but probably no immediate
	 * human intervention is required. On-call people won't
	 * be called immediately, but support personnel will want
	 * to review these issues ASAP to understand what the
	 * impact is. Basically any issue that needs to be tracked
	 * but may not require immediate intervention
	 */
	WARN,
	
	/**
	 * System is in distress, customers are probably being
	 * affected and the fix probably requires human intervention.
	 * The "2AM rule" applies here. If you're on-call, do you
	 * want to be woken up at 2AM if this condition happens?
	 * If yes, then log it as "error".
	 */
	ERROR
}
