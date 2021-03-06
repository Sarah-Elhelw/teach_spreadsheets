package io.github.oliviercailloux.teach_spreadsheets.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.junit.jupiter.api.Test;

import io.github.oliviercailloux.teach_spreadsheets.base.TeacherPrefs;
import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.read.TeacherPrefsInitializerTests;

public class JsonSerializerTests {
	@Test
	void testSerializeSet() throws Exception {
		URL url = TeacherPrefsInitializerTests.class.getResource("Saisie_des_voeux_format simple.ods");
		if(url != null) {
			try (InputStream odsStream = url.openStream()) {
				TeacherPrefs teacherPrefs = TeacherPrefs.fromOds(odsStream);

				Set<Course> courses = teacherPrefs.getCourses();

				String serializedSet = JsonSerializer.serializeSet(courses);

				try (JsonReader jr = Json.createReader(new StringReader(serializedSet))) {
					JsonArray ja = jr.readArray();

					assertEquals(courses.size(), ja.size());

					JsonObject actual = ja.getJsonObject(0);
					Course expected = new ArrayList<>(courses).get(0);

					assertEquals(expected.getName(), actual.getString("name"));
					assertEquals(expected.getStudyLevel(), actual.getString("studyLevel"));
					assertEquals(expected.getStudyYear(), actual.getInt("studyYear"));
					assertEquals(expected.getSemester(), actual.getInt("semester"));
					assertEquals(expected.getCountGroupsTD(), actual.getInt("countGroupsTD"));
					assertEquals(expected.getCountGroupsTP(), actual.getInt("countGroupsTP"));
					assertEquals(expected.getCountGroupsCMTD(), actual.getInt("countGroupsCMTD"));
					assertEquals(expected.getCountGroupsCMTP(), actual.getInt("countGroupsCMTP"));
					assertEquals(expected.getCountGroupsCM(), actual.getInt("countGroupsCM"));
					assertEquals(expected.getNbMinutesTD(), actual.getInt("nbMinutesTD"));
					assertEquals(expected.getNbMinutesTP(), actual.getInt("nbMinutesTP"));
					assertEquals(expected.getNbMinutesCMTD(), actual.getInt("nbMinutesCMTD"));
					assertEquals(expected.getNbMinutesCMTP(), actual.getInt("nbMinutesCMTP"));
					assertEquals(expected.getNbMinutesCM(), actual.getInt("nbMinutesCM"));

				}
			}
		}
	}
}
