package com.cokreates.grp.util.date;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.cokreates.grp.util.constant.Constant;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class DateTimeDeserializer extends JsonDeserializer<DateTime> {

    private DateTimeFormatter dateFormat = DateTimeFormat.forPattern(Constant.DATE_FORMAT);

    @Override
    public DateTime deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext) throws IOException {
    	if(StringUtils.isBlank(paramJsonParser.getText())){
    		return null;
    	}
        return dateFormat.parseDateTime(paramJsonParser.getText().trim());
    }
    
}