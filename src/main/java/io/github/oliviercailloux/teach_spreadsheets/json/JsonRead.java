package io.github.oliviercailloux.teach_spreadsheets.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

public class JsonRead {
	
	private static String readListInJsonFile(String fileName) throws IOException, URISyntaxException {
		URL resourceUrl = JsonRead.class.getResource(fileName);
		Path path = Path.of(resourceUrl.toURI());
		String tempTextFile = Files.readString(path);
		int leftSquareBracket = tempTextFile.indexOf('[');
		int rightSquareBracket = tempTextFile.indexOf(']');
		return tempTextFile.substring(leftSquareBracket, rightSquareBracket+1);
	}
	
	// Inspiration from : http://json-b.net/docs/user-guide.html
	public static ImmutableSet<Course> getSetOfCoursesInfo(String fileName) throws Exception{	// close() throws Exception
		final String textFile = readListInJsonFile(fileName);
		try(Jsonb jsonb = JsonbBuilder.create()){
			List<Course.Builder> coursesB = jsonb.fromJson(textFile, new ArrayList<Course.Builder>(){
				private static final long serialVersionUID = -7485196487129232750L;}.getClass().getGenericSuperclass()); 	// Not building courses yet to respect the building constraints.
			List<Course> courses = new ArrayList<>();
			for (Course.Builder cb : coursesB) {
				courses.add(cb.build());
			}
			ImmutableSet<Course> is = ImmutableSet.copyOf(courses);
			return is;
		}
	}
	
	private static String readRefRofPage(String loginFileName) throws URISyntaxException, MalformedURLException, IOException {
		URL resourceUrl = JsonRead.class.getResource(loginFileName);
		Path path = Path.of(resourceUrl.toURI());
		List<String> fileContent = Files.readAllLines(path);
		String username = fileContent.get(0);
		String password = fileContent.get(1);
		
		Authenticator.setDefault(new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password.toCharArray());
			}
		});

		URL oracle = new URL("https://rof.testapi.dauphine.fr/ebx-dataservices/rest/data/v1/BpvRefRof/RefRof/root/Person");
		try(BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()))){
			return in.readLine();
		}
	}
	
	private static String reformatTeacherListInJson(String teacherList) {
		JsonArray jsonArray;
		// ----- Getting a json object from a String ------
		JsonObject tempJson;
		try (JsonReader jr = Json.createReader(new StringReader(teacherList))) {
			tempJson = jr.readObject();
			
			// Getting the array :
			JsonArray tempJsonArray = tempJson.getJsonArray("rows");
			JsonArrayBuilder jab = Json.createArrayBuilder();
			
			// ----- Analyzing each element in the array: ------
			for (JsonValue jv : tempJsonArray) {
				
				// ------- Converting the jsonValue into a jsonObject -------
				try( JsonReader jr2 = Json.createReader(new StringReader(jv.toString()))) {
					JsonObject jo = jr2.readObject();
					
					// ----- Building a json object with only what is needed -------
					JsonObjectBuilder job = Json.createObjectBuilder();
					job.add("lastName", jo.getJsonObject("content").getJsonObject("familyName").getString("content", ""));
					job.add("firstName", jo.getJsonObject("content").getJsonObject("givenName").getString("content", ""));
					job.add("address", jo.getJsonObject("content").getJsonObject("contactData").getJsonObject("content").getJsonObject("address").getJsonObject("content").getJsonObject("street").getString("content", ""));
					job.add("postCode", jo.getJsonObject("content").getJsonObject("contactData").getJsonObject("content").getJsonObject("address").getJsonObject("content").getJsonObject("pcode").getString("content", ""));
					job.add("city", jo.getJsonObject("content").getJsonObject("contactData").getJsonObject("content").getJsonObject("address").getJsonObject("content").getJsonObject("locality").getString("content", ""));
					job.add("personalPhone", jo.getJsonObject("content").getJsonObject("contactData").getJsonObject("content").getJsonObject("telephone").getString("content", ""));
					job.add("dauphineEmail", jo.getJsonObject("content").getJsonObject("contactData").getJsonObject("content").getJsonObject("email").getString("content", ""));
					job.add("status", jo.getJsonObject("content").getJsonObject("title").getString("content", ""));
					
					// ---- Adding the json object to the arraybuilder
					jab.add(job);
				}
			}
			
			jsonArray = jab.build();
		}
		JsonObject finalJson = Json.createObjectBuilder().add("rows", jsonArray).build();
		
		String finalText = finalJson.toString();
		
		// Taking only what is between brackets:
		int leftSquareBracket = finalText.indexOf('[');
		int rightSquareBracket = finalText.lastIndexOf(']');

		return finalText.substring(leftSquareBracket, rightSquareBracket+1);
	}
	
	public static ImmutableSet<Teacher> getSetOfTeachersInfo(String loginFileName) throws Exception { 	// close() throws Exception (cf https://raw.githubusercontent.com/oliviercailloux/jsonb-sample/master/src/test/java/io/github/oliviercailloux/jsonb_sample/TestJsonSerialization.java)
		String tempText = readRefRofPage(loginFileName);
		final String finalText = reformatTeacherListInJson(tempText);
		
		// Deserialization
		try(Jsonb jsonb = JsonbBuilder.create()){
			List<Teacher.Builder> teacherB = jsonb.fromJson(finalText, new ArrayList<Teacher.Builder>(){
				private static final long serialVersionUID = 1L;}.getClass().getGenericSuperclass()); 	// Not building teachers yet to respect the building constraints.
			List<Teacher> teachers = new ArrayList<>();
			for (Teacher.Builder tb : teacherB) {
				if (!tb.getTeacherToBuild().getLastName().equals(""))
					teachers.add(tb.build());
			}
			ImmutableSet<Teacher> is = ImmutableSet.copyOf(teachers);
			return is;
		}
	}
	
	
	
	
	
}
