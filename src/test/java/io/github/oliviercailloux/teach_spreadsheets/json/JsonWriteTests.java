package io.github.oliviercailloux.teach_spreadsheets.json;

import static com.google.common.base.Verify.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.base.CalcData;
import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.read.CalcDataInitializerTests;

public class JsonWriteTests {
	@Test
	void testSerializeSet() throws Exception {
		URL url = CalcDataInitializerTests.class.getResource("Saisie_des_voeux_format simple.ods");
		verify(url != null);
		
		try (InputStream odsStream = url.openStream()) {
			CalcData calcData = CalcData.getData(odsStream);

			ImmutableSet<CoursePref> coursePrefs = calcData.getCoursePrefs();
			Set<Course> courses = new LinkedHashSet<>();
			for (CoursePref coursePref : coursePrefs) {
				courses.add(coursePref.getCourse());
			}

			String serializedSet = JsonWrite.serializeSet(courses);

			try (JsonReader jr = Json.createReader(new StringReader(serializedSet))) {
				JsonArray ja = jr.readArray();
				
				assertEquals(ja.size(), courses.size());
				
				JsonObject actual = ja.getJsonObject(0);
				Course expected = new ArrayList<Course>(courses).get(0);
				
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
