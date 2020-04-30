package io.github.oliviercailloux.teach_spreadsheets.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.Authenticator;
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
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

public class JsonRead {

	/**
	 * This method formats a json text, that contains a single array, to extract
	 * this array from it.
	 * 
	 * @param textArray - the json text containing an array.
	 * 
	 * @return the formatted json text to suit Jsonb deserializer.
	 * 
	 * @throws NullPointerException     if the parameter is null
	 * @throws IllegalArgumentException if the parameter does not contain an array
	 *                                  or contains several arrays.
	 */
	private static String formatToArray(String textArray) {
		checkNotNull(textArray, "The String must not be null.");
		int leftSquareBracket = textArray.indexOf('[');
		int rightSquareBracket = textArray.indexOf(']');
		checkArgument(leftSquareBracket != -1 && rightSquareBracket != -1, "The parameter must be a json text containing an array.");
		checkArgument(leftSquareBracket == textArray.lastIndexOf('[') && rightSquareBracket == textArray.lastIndexOf(']'), "The parameter must contain a single array.");
		return textArray.substring(leftSquareBracket, rightSquareBracket + 1);
	}
	
	/**
	 * This method reads a file and returns its content in a String.
	 * 
	 * @param fileName - the name of the file to be read.
	 * 
	 * @return the whole content of the file.
	 * 
	 * @throws IOException        if an I/O error occurs reading from the file or a
	 *                            malformed or unmappable byte sequence is read.
	 * @throws URISyntaxException if this URL is not well formatted and cannot be
	 *                            converted to a URI.
	 */
	private static String readFile(String fileName) throws IOException, URISyntaxException {
		URL resourceUrl = JsonRead.class.getResource(fileName);
		checkNotNull(resourceUrl, "The resource does not exist.");
		final Path path = Path.of(resourceUrl.toURI());
		return Files.readString(path);

	}
	
	/**
	 * This method deserializes a json string into an ImmutableSet of courses. The
	 * lines of code that deserialize the json string are inspired from
	 * <a href="http://json-b.net/docs/user-guide.html">this website</a>.
	 * The json file read must contain a single array.
	 * 
	 * @param fileName - the name of the file to be read.
	 * 
	 * @return an ImmutableSet of courses.
	 * 
	 * @throws Exception, thrown by close() if the resource cannot be closed.
	 */
	public static ImmutableSet<Course> getSetOfCoursesInfo(String fileName) throws Exception {
		String tempTextFile = readFile(fileName);
		final String textFile = formatToArray(tempTextFile);
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
	 * This method connects to RefRof, using the user name and password in a file, to
	 * read its XML content.
	 * 
	 * The file used to get RefReof's login must be structured as follows : it is a json file
	 * with the key "userName" whose value is the user name needed to get connected to RefRof
	 * and the key "password" whose value is the password needed to get connected to RefRof.
	 * 
	 * @param loginFileName - the name of the file containing RefRof's login.
	 * @param httpAdress - the web address to the RefRof page containing the informations on teachers.
	 * 
	 * @return the XML content of the <a href=
	 *         "https://rof.testapi.dauphine.fr/ebx-dataservices/rest/data/v1/BpvRefRof/RefRof/root/Person">RefRof
	 *         page</a> containing the informations on teachers.
	 * 
	 * @throws URISyntaxException if this URL is not well formatted and cannot be
	 *                            converted to a URI.
	 * @throws IOException        if an I/O error occurs reading from the file or a
	 *                            malformed or unmappable byte sequence is read.
	 */
	private static String readRefRofPage(String loginFileName, String httpAddress) throws URISyntaxException, IOException {
		URL resourceUrl = JsonRead.class.getResource(loginFileName);
		checkNotNull(resourceUrl, "The resource does not exist.");
		final Path path = Path.of(resourceUrl.toURI());
		String fileContent = Files.readString(path);
		JsonObject jo;
		String userName;
		String password;
		try (JsonReader jr = Json.createReader(new StringReader(fileContent))) {
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

		URL resourceUrlRef = new URL(httpAddress);
		try (BufferedReader in = new BufferedReader(new InputStreamReader(resourceUrlRef.openStream()))) {
			return in.readLine();
		}
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
		JsonObject finalJson = Json.createObjectBuilder().add("rows", jsonArray).build();

		String finalText = finalJson.toString();

		return formatToArray(finalText);
	}
	
	/**
	 * This method deserializes a json string into an ImmutableSet of teachers. The
	 * lines of code that deserialize the json string are inspired from
	 * <a href="http://json-b.net/docs/user-guide.html">this website</a>.
	 * 
	 * @param loginFileName - the name of the file containing RefRof's login.
	 * @param httpAdress - the web address to the RefRof page containing the informations on teachers.
	 * 
	 * @return an ImmutableSet of teachers.
	 * 
	 * @throws Exception, thrown by close() if the resource cannot be closed.
	 */
	public static ImmutableSet<Teacher> getSetOfTeachersInfo(String loginFileName, String httpAddress) throws Exception {
		String tempText = readRefRofPage(loginFileName, httpAddress);
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
