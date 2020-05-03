package io.github.oliviercailloux.teach_spreadsheets.json;

import java.io.StringReader;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.google.common.collect.ImmutableSet;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

public class JsonRead {
	
	/**
	 * This method checks that the json String is only a single array.
	 * 
	 * @param textArray - the json String.
	 * 
	 * @throws NullPointerException     if the parameter is null
	 * @throws IllegalArgumentException if the parameter does not contain an array
	 *                                  or contains several arrays or is not a
	 *                                  single array.
	 */
	private static void checkFormatOfArray(String textArray) {
		checkNotNull(textArray, "The String must not be null.");
		int leftSquareBracket = textArray.indexOf('[');
		int rightSquareBracket = textArray.indexOf(']');
		checkArgument(leftSquareBracket != -1 && rightSquareBracket != -1, "The parameter must be a json text containing an array.");
		checkArgument(leftSquareBracket == textArray.lastIndexOf('[') && rightSquareBracket == textArray.lastIndexOf(']'), "The parameter must contain a single array.");
		checkArgument(textArray.trim().equals(textArray.substring(leftSquareBracket, rightSquareBracket + 1)), "The parameter should only be a single array.");
	}
	
	/**
	 * This method deserializes a json string into an ImmutableSet of courses. The
	 * lines of code that deserialize the json string are inspired from
	 * <a href="http://json-b.net/docs/user-guide.html">this website</a>. The json
	 * string read must contain a single array.
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
			/**
			 * We first build each course to make sure they represent proper and acceptable
			 * Course objects :
			 */
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
	 * This method connects to RefRof using a json String containing the user name
	 * and the password. The json String must have the following structure: it
	 * contains the key "userName" whose value is the user name needed to get
	 * connected to RefRof and the key "password" whose value is the password needed
	 * to get connected to RefRof.
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
	 * This method reads the content (which is just one line) of the RefRof page
	 * concerning the teachers ; this content is in json. Then, it extracts from it
	 * the informations needed to build the set of of teachers.
	 * 
	 * @param httpAdress - the web address to the RefRof page containing the
	 *                   informations on teachers.
	 * 
	 * @return an ImmutableSet of teachers.
	 * 
	 */
	public static ImmutableSet<Teacher> getSetOfTeachersInfo(String httpAddress) {
		Client client = ClientBuilder.newClient();
		WebTarget t1 = client.target(httpAddress);
		String content = t1.request(MediaType.TEXT_PLAIN).get(String.class);
		client.close();
		
		Set<Teacher> teachers = new LinkedHashSet<>();

		/** ----- Getting the json object associated to the string teacherArray ------ */
		JsonObject formerJson;
		try (JsonReader jr = Json.createReader(new StringReader(content))) {
			formerJson = jr.readObject();

			/** ------ Building a simpler array ------ */
			JsonArray formerJsonArray = formerJson.getJsonArray("rows");

			/** ----- Analyzing each element in the array: ------ */
			for (JsonValue jv : formerJsonArray) {

				/** ------- Converting the jsonValue into a jsonObject ------- */
				try (JsonReader jr2 = Json.createReader(new StringReader(jv.toString()))) {
					JsonObject jo = jr2.readObject();
					
					/** ----- Building a json object with only what is needed ------- */
					Teacher.Builder teacherBuilder = Teacher.Builder.newInstance();
					teacherBuilder.setLastName(jo.getJsonObject("content").getJsonObject("familyName").getString("content", ""));
					teacherBuilder.setFirstName(jo.getJsonObject("content").getJsonObject("givenName").getString("content", ""));
					teacherBuilder.setAddress(jo.getJsonObject("content").getJsonObject("contactData").getJsonObject("content").getJsonObject("address").getJsonObject("content").getJsonObject("street").getString("content", ""));
					teacherBuilder.setPostCode(jo.getJsonObject("content").getJsonObject("contactData").getJsonObject("content").getJsonObject("address").getJsonObject("content").getJsonObject("pcode").getString("content", ""));
					teacherBuilder.setCity(jo.getJsonObject("content").getJsonObject("contactData").getJsonObject("content").getJsonObject("address").getJsonObject("content").getJsonObject("locality").getString("content", ""));
					teacherBuilder.setPersonalPhone(jo.getJsonObject("content").getJsonObject("contactData").getJsonObject("content").getJsonObject("telephone").getString("content", ""));
					teacherBuilder.setDauphineEmail(jo.getJsonObject("content").getJsonObject("contactData").getJsonObject("content").getJsonObject("email").getString("content", ""));
					teacherBuilder.setStatus(jo.getJsonObject("content").getJsonObject("title").getString("content", ""));
					
					teachers.add(teacherBuilder.build());
				}
			}

		}
		
		return ImmutableSet.copyOf(teachers);
	}

}
