package com.cokreates.grp.util.constant;

import java.lang.reflect.Modifier;
import java.math.BigDecimal;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;

import com.cokreates.grp.handler.AppException;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.request.ServiceRequestHeader;
import com.cokreates.grp.util.date.GsonBigDecimalUtil;
import com.cokreates.grp.util.date.GsonDateTimeUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;

import lombok.Synchronized;

public final class Constant {
	
	public static final String POSTGRES_DATE_TIME_FORMAT = "to_timestamp(?, 'YYYY-MM-DD HH24:MI:SS.MS')::timestamp";
	public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'";
	public static final String JAVA_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String JAVA_DATE_FORMAT = "yyyy-MM-dd";
	public static final boolean PREETY_JSON = true;
	public static final String COMMON_CONTAINER = "COMMON";
	public static final String AUTH_WS_CONTAINER = "AUTH-WS";
	
	public static final String OUTPUT_DIR = "output";
	public static final String REPORT_DIR = "report";
	public static final String LOGO_NAME = "logo.png";
	
	public static final String TI_STATUS_PROCESSING = "Processing";
	public static final String TI_STATUS_READY_FOR_QC = "Ready for QC";
	public static final String TI_STATUS_QC_DONE = "QC done";
	public static final String TI_STATUS_REJECTED = "Rejected";
	public static final String TI_STATUS_ASSET_ADDED = "Asset added";
	public static final String TI_STATUS_DRAFT = "Draft";
	public static final String TI_STATUS_QC_ONGOING = "QC ongoing";
	public static final String TI_STATUS_ASSET_TAGGED = "Asset tagged";
	public static final String TI_STATUS_RE_QC = "Re-QC";
	public static final String TI_STATUS_QC_APPROVED = "QC approved";
	
	public static final String AI_STATUS_DRAFT = "Draft";
	public static final String AI_STATUS_READY_FOR_TAGGING = "Ready for tagging";
	public static final String AI_STATUS_AVAILABLE = "Available";
	public static final String AI_STATUS_IN_USE = "In-use";
	public static final String AI_STATUS_DIRECT_OUT_PENDING = "Direct Out Pending";
	
	public static final String AI_IN_TYPE_QC_PASS = "QC Passed";
	public static final String AI_IN_TYPE_DIRECT_IN = "Direct In";
	
	
	
	public static final String AA_STATUS_FULLY_RECEIVED = "Fully received";
	
	public static final String TI_PRC_METHOD_OTM = "OTM";
	public static final String TI_PRC_METHOD_DM = "DM";
	
	public static final String TI_STATUS = "Ready for QC";
	
	public static final String ED_OUT_OF_SERVICE = "Out of service";
	
	public static final String WO_PARTIALLY_RECEIVED = "Partially received";
	public static final String WO_FULLY_RECEIVED = "Fully received";
	
	public static final String DI_PENDING = "Pending";
	public static final String DI_APPROVED = "Approved";
	public static final String DI_REJECTED = "Rejected";
	
	public static final String DI_TYPE_IN = "In";
	public static final String DI_TYPE_OUT = "Out";
	
	
	public static final String DO_PENDING = "Pending";
	public static final String DO_APPROVED = "Approved";
	public static final String DO_REJECTED = "Rejected";
	
	public static final String GUR_ACTIVE_STATUS_YES = "Yes";
	public static final String GUR_ACTIVE_STATUS_NO = "No";
	public static final String GUR_DEFAULT_STATUS_YES = "Yes";
	public static final String GUR_DEFAULT_STATUS_NO = "No";

	@Synchronized	
	public static ImmutablePair<String, String> getDateTime(DateTime now){
		if(now == null)
			now = new DateTime();
		String time = DateTimeFormat.forPattern(Constant.JAVA_DATE_TIME_FORMAT).print(now);
		return new ImmutablePair<String, String>(Constant.POSTGRES_DATE_TIME_FORMAT, time);
	}
	
	@Synchronized
	public static String getTime(long ms){
		DateTime now = null;
		if(ms <= 0){
			now = new DateTime();
		} else {
			now = new DateTime(ms);			
		}
		return DateTimeFormat.forPattern(Constant.DATE_FORMAT).print(now);
	}
	
	public static String print(Object object){
		GsonBuilder builder = new GsonBuilder()
			.registerTypeAdapter(BigDecimal.class, new GsonBigDecimalUtil())
			.registerTypeAdapter(DateTime.class, new GsonDateTimeUtil());
		builder.excludeFieldsWithoutExposeAnnotation();
		builder.excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT, Modifier.VOLATILE, Modifier.FINAL);
		if(Constant.PREETY_JSON){
			builder.setPrettyPrinting();
		}
		return builder.create().toJson(object);
	}
	
	@Synchronized
	public static AuthUser getAuthUser(ServiceRequestHeader header, String token) throws AppException {
		try {
			Jwt jwtToken = JwtHelper.decode(token.replace("Bearer ", ""));
			String claims = jwtToken.getClaims();
			ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			AuthUser user = mapper.readValue(claims, AuthUser.class);
			user.setToken(token);
			return user;
		} catch (Exception e) {
			throw new AppException(header, Code.C_500.get(), "Unable to parse AuthUser");
		}
	}
	
	
}
