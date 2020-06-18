package io.github.oliviercailloux.teach_spreadsheets.base;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.net.URL;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableSet;

public class AggregatedPrefsTests {

	private static Teacher teacher1 = Teacher.Builder.newInstance().setLastName("Dupond").setFirstName("Jack").build();
	private static Teacher teacher2 = Teacher.Builder.newInstance().setLastName("Dupont").setFirstName("Jane").build();

	private static Course course1 = Course.Builder.newInstance().setName("Algèbre").setStudyYear(2016)
			.setStudyLevel("DE1").setSemester(1).setCountGroupsTD(6).setNbMinutesTD(900).build();
	private static Course course2 = Course.Builder.newInstance().setName("Analyse").setStudyYear(2016)
			.setStudyLevel("DE1").setSemester(1).setCountGroupsTD(5).setNbMinutesTD(700).build();
	private static Set<Course> courses1 = Set.of(course1);
	private static Set<Course> courses2 = Set.of(course1, course2);
	
	private static CoursePref coursePref1 = CoursePref.Builder.newInstance(course1, teacher1).setPrefTD(Preference.A)
			.build();
	private static CoursePref coursePref2 = CoursePref.Builder.newInstance(course1, teacher1).setPrefTD(Preference.B)
			.build();
	private static CoursePref coursePref3 = CoursePref.Builder.newInstance(course2, teacher2).setPrefTD(Preference.C)
			.build();
	private static CoursePref coursePref4 = CoursePref.Builder.newInstance(course1, teacher2).setPrefTD(Preference.C)
			.build();
	private static CoursePref coursePref5 = CoursePref.Builder.newInstance(course2, teacher1).build();

	private static TeacherPrefs teacherPrefs1 = TeacherPrefs.newInstance(Set.of(coursePref1), teacher1);
	private static TeacherPrefs teacherPrefs2 = TeacherPrefs.newInstance(Set.of(coursePref2), teacher1);
	private static TeacherPrefs teacherPrefs3 = TeacherPrefs.newInstance(Set.of(coursePref3), teacher2);
	private static TeacherPrefs teacherPrefs4 = TeacherPrefs.newInstance(Set.of(coursePref4), teacher2);
	
	private static TeacherPrefs teacherPrefs5 = TeacherPrefs.newInstance(Set.of(coursePref1, coursePref5), teacher1);
	private static TeacherPrefs teacherPrefs6 = TeacherPrefs.newInstance(Set.of(coursePref3, coursePref4), teacher2);

	@Test
	void testAddTeacherPrefsWithSameTeacher() {
		AggregatedPrefs.Builder aggregatedPrefsBuilder = AggregatedPrefs.Builder.newInstance(courses1);
		aggregatedPrefsBuilder.addTeacherPrefs(teacherPrefs1);
		Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
			aggregatedPrefsBuilder.addTeacherPrefs(teacherPrefs2);
		});
		assertEquals("You cannot add twice all the preferences of the teacher : " + teacher1, exception.getMessage());
	}

	@Test
	void testAddTeacherPrefsWithDifferentCourses() {
		AggregatedPrefs.Builder aggregatedPrefs = AggregatedPrefs.Builder.newInstance(courses1);
		aggregatedPrefs.addTeacherPrefs(teacherPrefs1);
		Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
			aggregatedPrefs.addTeacherPrefs(teacherPrefs3);
		});
		assertEquals("There must be the same courses in the teacherPrefs.", exception.getMessage());
	}
	
	@Test
	void testBuildAggregatedPrefsWithCoursePrefs() {
		AggregatedPrefs.Builder aggregatedPrefsBuilder = AggregatedPrefs.Builder.newInstance(courses2);
		aggregatedPrefsBuilder.addCoursePrefs(Set.of(coursePref1, coursePref3, coursePref4));
		AggregatedPrefs aggregatedPrefs = aggregatedPrefsBuilder.build();
		
		assertTrue(aggregatedPrefs.getTeacherPrefsSet().size() == 2);
		
		assertEquals(aggregatedPrefs.getTeacherPrefs(teacher1), teacherPrefs5.getCoursePrefs());
		assertEquals(aggregatedPrefs.getTeacherPrefs(teacher2), teacherPrefs6.getCoursePrefs());
	}

	/**
	 * The aim of this method is to test that when we will concretely and
	 * realistically use the class AgregatedPrefs, it works properly.
	 */
	@Test
	void testAddTeacherPrefsConcretely() throws Exception {
		URL resourceUrl1 = AggregatedPrefs.class.getResource("Saisie_des_voeux_format simple1.ods");
		TeacherPrefs cd1;
		try (InputStream stream = resourceUrl1.openStream()) {
			cd1 = TeacherPrefs.getData(stream);
		}

		URL resourceUrl2 = AggregatedPrefs.class.getResource("Saisie_des_voeux_format simple2.ods");
		TeacherPrefs cd2;
		try (InputStream stream = resourceUrl2.openStream()) {
			cd2 = TeacherPrefs.getData(stream);
		}
		
		Set<Course> courses = cd1.getCourses();

		AggregatedPrefs.Builder aggregatedPrefsBuilder = AggregatedPrefs.Builder.newInstance(courses);
		aggregatedPrefsBuilder.addTeacherPrefs(cd1);
		assertDoesNotThrow(() -> {
			aggregatedPrefsBuilder.addTeacherPrefs(cd2);
			aggregatedPrefsBuilder.build();
		});
		
	}

	@Test
	void testGetTeacherPrefs() {
		AggregatedPrefs.Builder aggregatedPrefsBuilder = AggregatedPrefs.Builder.newInstance(courses1);
		aggregatedPrefsBuilder.addTeacherPrefs(teacherPrefs1);
		aggregatedPrefsBuilder.addTeacherPrefs(teacherPrefs4);
		AggregatedPrefs aggregatedPrefs = aggregatedPrefsBuilder.build();

		assertEquals(ImmutableSet.of(coursePref1), aggregatedPrefs.getTeacherPrefs(teacher1));
	}

	@Test
	void testGetCoursePrefs() {
		AggregatedPrefs.Builder aggregatedPrefsBuilder = AggregatedPrefs.Builder.newInstance(courses1);
		aggregatedPrefsBuilder.addTeacherPrefs(teacherPrefs1);
		aggregatedPrefsBuilder.addTeacherPrefs(teacherPrefs4);
		AggregatedPrefs aggregatedPrefs = aggregatedPrefsBuilder.build();

		assertEquals(ImmutableSet.of(coursePref1, coursePref4), aggregatedPrefs.getCoursePrefs(course1));
	}

}