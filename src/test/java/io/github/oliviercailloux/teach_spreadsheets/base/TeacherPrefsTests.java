package io.github.oliviercailloux.teach_spreadsheets.base;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.base.TeacherPrefs;
import io.github.oliviercailloux.teach_spreadsheets.base.Course;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;
import io.github.oliviercailloux.teach_spreadsheets.base.Preference;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

public class TeacherPrefsTests {

	@Test
	void testNewInstanceWithSameCoursesNames() {

		Teacher teacher = Teacher.Builder.newInstance().setAddress("Pont du maréchal de lattre de tassigny")
				.setLastName("Doe").build();

		Course course1 = Course.Builder.newInstance().setCountGroupsCM(1).setNbMinutesCM(600)
				.setName("Analyse de données").setStudyYear(2012).setStudyLevel("DE1").setSemester(1).build();
		Course course2 = Course.Builder.newInstance().setCountGroupsCM(1).setNbMinutesCM(600)
				.setName("Analyse de données").setStudyYear(2012).setStudyLevel("DE1").setSemester(1).build();

		CoursePref coursePref1 = CoursePref.Builder.newInstance(course1, teacher).setPrefCM(Preference.A).build();
		CoursePref coursePref2 = CoursePref.Builder.newInstance(course2, teacher).setPrefCM(Preference.B).build();
		ImmutableSet<CoursePref> coursePrefs = ImmutableSet.copyOf(new CoursePref[] { coursePref1, coursePref2 });

		Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
			TeacherPrefs.newInstance(coursePrefs, teacher);
		});
		assertEquals("You can't have twice the preferences of a course.", exception.getMessage());

	}

	@Test
	void testGetCoursePrefWithNotMatchingCourse() {

		Teacher teacher = Teacher.Builder.newInstance().setAddress("Elysee").setLastName("Smith").build();

		Course course1 = Course.Builder.newInstance().setCountGroupsCM(1).setNbMinutesCM(900).setName("Java")
				.setStudyYear(2012).setStudyLevel("DE2").setSemester(2).build();

		CoursePref coursePref1 = CoursePref.Builder.newInstance(course1, teacher).setPrefCM(Preference.UNSPECIFIED)
				.build();

		ImmutableSet<CoursePref> coursePrefs = ImmutableSet.of(coursePref1);

		TeacherPrefs teacherPrefs = TeacherPrefs.newInstance(coursePrefs, teacher);

		Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
			teacherPrefs.getCoursePref("Algèbre");
		});
		assertEquals("The name given in parameter does not match any course.", exception.getMessage());

	}
}