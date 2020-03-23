package com.ccc.logs.notifications;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * Converts the timestamp fields in {@code LogEvent} and {@code LogMessage}
 * to strings that can be contained in a json object
 */
class TimestampSerializer extends StdSerializer<ZonedDateTime> {
	/**
	 * Serialization ID
	 */
	private static final long serialVersionUID = 845639574081893274L;
	
	/**
	 * Specifies the format
	 * of the date
	 */
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
	
	/**
	 * Empty Constructor
	 */
	TimestampSerializer() {
		this(null);
	}
	
	/**
	 * Class constructor
	 * @param type The class object
	 * of the type being converted to
	 * a string
	 */
	TimestampSerializer(Class<ZonedDateTime> type) {
		super(type);
	}
	
	/**
	 * Puts the date object that represents a timestamp
	 * into a string
	 */
	@Override
	public void serialize(ZonedDateTime value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		String str = value.format(TimestampSerializer.DATE_FORMATTER);
		jgen.writeString(str);
	}
}
