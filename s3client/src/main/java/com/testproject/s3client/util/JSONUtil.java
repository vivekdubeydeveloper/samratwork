package com.testproject.s3client.util;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JSONUtil {

	
	public static String getJson(Object obj) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(obj);
		return jsonInString;
	}
	
	@SuppressWarnings("rawtypes")
	public static Map getJsonObject(String jsonInString) throws JsonParseException, JsonMappingException, IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		Map obj = mapper.readValue(jsonInString, Map.class);
		return obj;
	}
	
    @SuppressWarnings("rawtypes")
	public static Map getJsonObjectFromFile(String filePath) throws JsonParseException, JsonMappingException, IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		Map obj = mapper.readValue(new File(filePath), Map.class);
		return obj;
	}
}
