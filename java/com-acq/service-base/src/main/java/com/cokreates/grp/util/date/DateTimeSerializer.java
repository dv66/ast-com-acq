package com.cokreates.grp.util.date;

import java.io.IOException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.cokreates.grp.util.constant.Constant;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class DateTimeSerializer extends JsonSerializer<DateTime> {
	
    private DateTimeFormatter dateFormat = DateTimeFormat.forPattern(Constant.DATE_FORMAT);

	@Override
	public void serialize(DateTime date, JsonGenerator gen, SerializerProvider provider) throws IOException, JsonProcessingException {
		if(date == null){
			return;
		}
		gen.writeString(dateFormat.print(date));
	}
	
}