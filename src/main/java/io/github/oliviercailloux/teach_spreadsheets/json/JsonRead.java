package io.github.oliviercailloux.teach_spreadsheets.json;

import java.util.ArrayList;
import java.util.List;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import com.google.common.collect.ImmutableSet;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;

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
	
}