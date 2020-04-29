package io.github.oliviercailloux.teach_spreadsheets.base;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.base.Preference;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

public class CoursePrefTests {
	@Test
	void testBuild() {
		Teacher.Builder teacherBuilder = Teacher.Builder.newInstance();
		teacherBuilder.setLastName("Doe");

		Course.Builder courseBuilder = Course.Builder.newInstance();
		courseBuilder.setCountGroupsCM(10);
		courseBuilder.setnbMinutesCM(20);
		courseBuilder.setSemester(1);
		courseBuilder.setName("Programmation");
		courseBuilder.setStudyYear("2020");

		CoursePref.Builder coursePrefBuilder = CoursePref.Builder.newInstance(courseBuilder.build(),
				teacherBuilder.build());
		coursePrefBuilder.setPrefCM(Preference.A);
		coursePrefBuilder.setPrefCMTD(Preference.B);
		
		assertThrows(IllegalStateException.class, () -> {
			coursePrefBuilder.build();
		});
		
		coursePrefBuilder.setPrefCMTD(Preference.UNSPECIFIED);
		coursePrefBuilder.setPrefNbGroupsCM(11);
		
		assertThrows(IllegalStateException.class, () -> {
			coursePrefBuilder.build();
		});
	}
}
