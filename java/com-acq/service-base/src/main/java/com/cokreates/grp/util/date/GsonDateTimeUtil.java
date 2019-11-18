package com.cokreates.grp.util.date;

import java.lang.reflect.Type;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.cokreates.grp.util.constant.Constant;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class GsonDateTimeUtil implements JsonSerializer<DateTime>, JsonDeserializer<DateTime> {
	
	private DateTimeFormatter dateFormat = DateTimeFormat.forPattern(Constant.DATE_FORMAT);
	
	@Override
	public JsonElement serialize(DateTime src, Type typeOfSrc, JsonSerializationContext context) {
		if (src == null) {
			return null;
		}
		return new JsonPrimitive(dateFormat.print(src));
	}

	@Override
	public DateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		if (StringUtils.isBlank(json.getAsString())) {
			return null;
		}
		return dateFormat.parseDateTime(json.getAsString().trim());
	}
	
}