package io.github.oliviercailloux.teach_spreadsheets.json;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
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
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

public class JsonRead {
	
	/**
	 * This method checks that the json String is only a single array.
	 * 
	 * @param textArray - the json text containing an array.
	 *  
	 * @throws NullPointerException     if the parameter is null
	 * @throws IllegalArgumentException if the parameter does not contain an array
	 *                                  or contains several arrays.
	 */
	private static void checkFormatOfArray(String textArray) {
		checkNotNull(textArray, "The String must not be null.");
		int leftSquareBracket = textArray.indexOf('[');
		int rightSquareBracket = textArray.indexOf(']');
		checkArgument(leftSquareBracket != -1 && rightSquareBracket != -1, "The parameter must be a json text containing an array.");
		checkArgument(leftSquareBracket == textArray.lastIndexOf('[') && rightSquareBracket == textArray.lastIndexOf(']'), "The parameter must contain a single array.");
	}
	
	
	/**
	 * This method deserializes a json string into an ImmutableSet of courses. The
	 * lines of code that deserialize the json string are inspired from
	 * <a href="http://json-b.net/docs/user-guide.html">this website</a>.
	 * The json string read must contain a single array.
	 * 
	 * @param textFile - the json string containing a list of courses.
	 * 
	 * @return an ImmutableSet of courses.
	 * 
	 * @throws Exception, thrown by close() if the resource cannot be closed.
	 */
	public static ImmutableSet<Course> getSetOfCoursesInfo(String textFile) throws Exception {
		checkFormatOfArray(textFile);
		try (Jsonb jsonb = JsonbBuilder.create()) {
			/** We first build each course to make sure they represent proper and acceptable Course objects :*/
			List<Course.Builder> coursesB = jsonb.fromJson(textFile, new ArrayList<Course.Builder>() {
				private static final long serialVersionUID = -7485196487129232750L;
			}.getClass().getGenericSuperclass());
			List<Course> courses = new ArrayList<>();
			for (Course.Builder cb : coursesB) {
				courses.add(cb.build());
			}
			ImmutableSet<Course> is = ImmutableSet.copyOf(courses);
			return is;
		}
	}
	
	/**
	 * This method connects to RefRof using a json String containing the user name and the password.
	 * 
	 * @param jsonLogin - the json String containing the login informations
	 * 
	 */
	public static void authentification(String jsonLogin) {
		JsonObject jo;
		String userName;
		String password;
		try (JsonReader jr = Json.createReader(new StringReader(jsonLogin))) {
			jo = jr.readObject();
			userName = jo.getString("userName");
			password = jo.getString("password");
		}

		Authenticator.setDefault(new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password.toCharArray());
			}
		});
	}

	
	/**
	 * This method takes in argument a json string from RefRof that has a complex
	 * structure and simplifies it to be able to deserialize it later on.
	 * 
	 * @param teacherArray - the json string containing an array to be simplified.
	 * 
	 * @return the simplified json string.
	 */
	private static String reformatTeacherListInJson(String teacherArray) {
		/**
		 * It represents the simplified array built by this method.
		 */
		JsonArray jsonArray;

		/** ----- Getting the json object associated to the string teacherArray ------ */
		JsonObject formerJson;
		try (JsonReader jr = Json.createReader(new StringReader(teacherArray))) {
			formerJson = jr.readObject();

			/** ------ Building a simpler array ------ */
			JsonArray formerJsonArray = formerJson.getJsonArray("rows");
			JsonArrayBuilder jab = Json.createArrayBuilder();

			/** ----- Analyzing each element in the array: ------ */
			for (JsonValue jv : formerJsonArray) {

				/** ------- Converting the jsonValue into a jsonObject ------- */
				try (JsonReader jr2 = Json.createReader(new StringReader(jv.toString()))) {
					JsonObject jo = jr2.readObject();
					
					/** ----- Building a json object with only what is needed ------- */
					JsonObjectBuilder job = Json.createObjectBuilder();
					job.add("lastName", jo.getJsonObject("content").getJsonObject("familyName").getString("content", ""));
					job.add("firstName", jo.getJsonObject("content").getJsonObject("givenName").getString("content", ""));
					job.add("address", jo.getJsonObject("content").getJsonObject("contactData").getJsonObject("content").getJsonObject("address").getJsonObject("content").getJsonObject("street").getString("content", ""));
					job.add("postCode", jo.getJsonObject("content").getJsonObject("contactData").getJsonObject("content").getJsonObject("address").getJsonObject("content").getJsonObject("pcode").getString("content", ""));
					job.add("city", jo.getJsonObject("content").getJsonObject("contactData").getJsonObject("content").getJsonObject("address").getJsonObject("content").getJsonObject("locality").getString("content", ""));
					job.add("personalPhone", jo.getJsonObject("content").getJsonObject("contactData").getJsonObject("content").getJsonObject("telephone").getString("content", ""));
					job.add("dauphineEmail", jo.getJsonObject("content").getJsonObject("contactData").getJsonObject("content").getJsonObject("email").getString("content", ""));
					job.add("status", jo.getJsonObject("content").getJsonObject("title").getString("content", ""));
					
					/** ---- Adding the json object to the JsonArrayBuilder */
					jab.add(job);
				}
			}

			jsonArray = jab.build();
		}

		String finalText = jsonArray.toString();

		return finalText;
	}
	
	/**
	 * This method deserializes a json string into an ImmutableSet of teachers. The
	 * lines of code that deserialize the json string are inspired from
	 * <a href="http://json-b.net/docs/user-guide.html">this website</a>.
	 * 
	 * @param httpAdress    - the web address to the RefRof page containing the
	 *                      informations on teachers.
	 * 
	 * @return an ImmutableSet of teachers.
	 * 
	 * @throws Exception, thrown by close() if the resource cannot be closed.
	 */
	public static ImmutableSet<Teacher> getSetOfTeachersInfo(String httpAddress) throws Exception {
		URL resourceUrlRef = new URL(httpAddress);
		String tempText;
		try (BufferedReader in = new BufferedReader(new InputStreamReader(resourceUrlRef.openStream()))) {
			tempText = in.readLine();
		}
		final String finalText = reformatTeacherListInJson(tempText);
		try (Jsonb jsonb = JsonbBuilder.create()) {
			/** We first build each teacher to make sure they represent proper and acceptable Teacher objects : */
			List<Teacher.Builder> teacherB = jsonb.fromJson(finalText, new ArrayList<Teacher.Builder>() {
				private static final long serialVersionUID = 1L;
			}.getClass().getGenericSuperclass());
			List<Teacher> teachers = new ArrayList<>();
			for (Teacher.Builder tb : teacherB) {
				teachers.add(tb.build());
			}
			ImmutableSet<Teacher> is = ImmutableSet.copyOf(teachers);
			return is;
		}
	}

}
