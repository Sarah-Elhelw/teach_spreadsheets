package io.github.oliviercailloux.teach_spreadsheets.base;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Set;

import org.junit.jupiter.api.Test;

public class AgregatedDataTests {
	
	private static Teacher teacher1 = Teacher.Builder.newInstance().setLastName("Dupond").setDauphineEmail("jack.dupond@dauphine.fr").build();
	private static Teacher teacher2 = Teacher.Builder.newInstance().setLastName("Dupont").setDauphineEmail("jane.dupont@dauphine.fr").build();
	
	private static Course course1 = Course.Builder.newInstance().setName("Algèbre").setStudyYear("2016").setSemester(1).setCountGroupsTD(6).setnbMinutesTD(900).build();
	private static Course course2 = Course.Builder.newInstance().setName("Analyse").setStudyYear("2016").setSemester(1).setCountGroupsTD(5).setnbMinutesTD(700).build();
	
	private static CoursePref coursepref1 = CoursePref.Builder.newInstance(course1, teacher1).setPrefTD(Preference.A).build();
	private static CoursePref coursepref2 = CoursePref.Builder.newInstance(course1, teacher1).setPrefTD(Preference.B).build();
	private static CoursePref coursepref3 = CoursePref.Builder.newInstance(course2, teacher2).setPrefTD(Preference.C).build();
	
	private static CalcData calcData1 = CalcData.newInstance(Set.of(coursepref1), teacher1);
	private static CalcData calcData2 = CalcData.newInstance(Set.of(coursepref2), teacher1);
	private static CalcData calcData3 = CalcData.newInstance(Set.of(coursepref3), teacher2);
	
	@Test
	void testAddCalcDataWithDifferentCourses() {
		AgregatedData agregatedData = AgregatedData.newInstance();
		agregatedData.addCalcData(calcData1);
		Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
			agregatedData.addCalcData(calcData2);
		});
		assertEquals(
				"You cannot add twice all the preferences of a teacher.",
				exception.getMessage());
	}
	
	@Test
	void testAddCalcDataWithSameTeacher() {
		AgregatedData agregatedData = AgregatedData.newInstance();
		agregatedData.addCalcData(calcData1);
		Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
			agregatedData.addCalcData(calcData3);
		});
		assertEquals(
				"There must be the same courses in the calc datas.",
				exception.getMessage());
	}
	
	
}
