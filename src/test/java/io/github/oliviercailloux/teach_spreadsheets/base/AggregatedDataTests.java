package io.github.oliviercailloux.teach_spreadsheets.base;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.InputStream;
import java.net.URL;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableSet;

public class AggregatedDataTests {

	private static Teacher teacher1 = Teacher.Builder.newInstance().setLastName("Dupond").setFirstName("Jack").build();
	private static Teacher teacher2 = Teacher.Builder.newInstance().setLastName("Dupont").setFirstName("Jane").build();

	private static Course course1 = Course.Builder.newInstance().setName("Algèbre").setStudyYear(2016)
			.setStudyLevel("DE1").setSemester(1).setCountGroupsTD(6).setNbMinutesTD(900).build();
	private static Course course2 = Course.Builder.newInstance().setName("Analyse").setStudyYear(2016)
			.setStudyLevel("DE1").setSemester(1).setCountGroupsTD(5).setNbMinutesTD(700).build();
	private static Set<Course> courses = Set.of(course1);
	
	private static CoursePref coursePref1 = CoursePref.Builder.newInstance(course1, teacher1).setPrefTD(Preference.A)
			.build();
	private static CoursePref coursePref2 = CoursePref.Builder.newInstance(course1, teacher1).setPrefTD(Preference.B)
			.build();
	private static CoursePref coursePref3 = CoursePref.Builder.newInstance(course2, teacher2).setPrefTD(Preference.C)
			.build();
	private static CoursePref coursePref4 = CoursePref.Builder.newInstance(course1, teacher2).setPrefTD(Preference.C)
			.build();

	private static TeacherPrefs teacherPrefs1 = TeacherPrefs.newInstance(Set.of(coursePref1), teacher1);
	private static TeacherPrefs teacherPrefs2 = TeacherPrefs.newInstance(Set.of(coursePref2), teacher1);
	private static TeacherPrefs teacherPrefs3 = TeacherPrefs.newInstance(Set.of(coursePref3), teacher2);
	private static TeacherPrefs teacherPrefs4 = TeacherPrefs.newInstance(Set.of(coursePref4), teacher2);

	@Test
	void testAddTeacherPrefsWithSameTeacher() {
		AggregatedData.Builder aggregatedDataBuilder = AggregatedData.Builder.newInstance(courses);
		aggregatedDataBuilder.addTeacherPrefs(teacherPrefs1);
		Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
			aggregatedDataBuilder.addTeacherPrefs(teacherPrefs2);
		});
		assertEquals("You cannot add twice all the preferences of the teacher : " + teacher1, exception.getMessage());
	}

	@Test
	void testAddTeacherPrefsWithDifferentCourses() {
		AggregatedData.Builder aggregatedData = AggregatedData.Builder.newInstance(courses);
		aggregatedData.addTeacherPrefs(teacherPrefs1);
		Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
			aggregatedData.addTeacherPrefs(teacherPrefs3);
		});
		assertEquals("There must be the same courses in the teacherPrefs.", exception.getMessage());
	}

	/**
	 * The aim of this method is to test that when we will concretely and
	 * realistically use the class AgregatedData, it works properly.
	 */
	@Test
	void testAddTeacherPrefsConcretely() throws Exception {
		URL resourceUrl1 = AggregatedData.class.getResource("Saisie_des_voeux_format simple1.ods");
		TeacherPrefs cd1;
		try (InputStream stream = resourceUrl1.openStream()) {
			cd1 = TeacherPrefs.getData(stream);
		}

		URL resourceUrl2 = AggregatedData.class.getResource("Saisie_des_voeux_format simple2.ods");
		TeacherPrefs cd2;
		try (InputStream stream = resourceUrl2.openStream()) {
			cd2 = TeacherPrefs.getData(stream);
		}
		
		Set<Course> courses = cd1.getCourses();

		AggregatedData.Builder aggregatedDataBuilder = AggregatedData.Builder.newInstance(courses);
		aggregatedDataBuilder.addTeacherPrefs(cd1);
		assertDoesNotThrow(() -> {
			aggregatedDataBuilder.addTeacherPrefs(cd2);
			aggregatedDataBuilder.build();
		});
		
	}

	@Test
	void testGetTeacherPrefs() {
		AggregatedData.Builder aggregatedDataBuilder = AggregatedData.Builder.newInstance(courses);
		aggregatedDataBuilder.addTeacherPrefs(teacherPrefs1);
		aggregatedDataBuilder.addTeacherPrefs(teacherPrefs4);
		AggregatedData aggregatedData = aggregatedDataBuilder.build();

		assertEquals(ImmutableSet.of(coursePref1), aggregatedData.getTeacherPrefs(teacher1));
	}

	@Test
	void testGetCoursePrefs() {
		AggregatedData.Builder aggregatedDataBuilder = AggregatedData.Builder.newInstance(courses);
		aggregatedDataBuilder.addTeacherPrefs(teacherPrefs1);
		aggregatedDataBuilder.addTeacherPrefs(teacherPrefs4);
		AggregatedData aggregatedData = aggregatedDataBuilder.build();

		assertEquals(ImmutableSet.of(coursePref1, coursePref4), aggregatedData.getCoursePrefs(course1));
	}

}