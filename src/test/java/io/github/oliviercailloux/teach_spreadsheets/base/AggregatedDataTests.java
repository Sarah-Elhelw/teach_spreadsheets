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

	private static CoursePref coursePref1 = CoursePref.Builder.newInstance(course1, teacher1).setPrefTD(Preference.A)
			.build();
	private static CoursePref coursePref2 = CoursePref.Builder.newInstance(course1, teacher1).setPrefTD(Preference.B)
			.build();
	private static CoursePref coursePref3 = CoursePref.Builder.newInstance(course2, teacher2).setPrefTD(Preference.C)
			.build();
	private static CoursePref coursePref4 = CoursePref.Builder.newInstance(course1, teacher2).setPrefTD(Preference.C)
			.build();

	private static CalcData calcData1 = CalcData.newInstance(Set.of(coursePref1), teacher1);
	private static CalcData calcData2 = CalcData.newInstance(Set.of(coursePref2), teacher1);
	private static CalcData calcData3 = CalcData.newInstance(Set.of(coursePref3), teacher2);
	private static CalcData calcData4 = CalcData.newInstance(Set.of(coursePref4), teacher2);

	@Test
	void testAddCalcDataWithSameTeacher() {
		AggregatedData.Builder aggregatedDataBuilder = AggregatedData.Builder.newInstance();
		aggregatedDataBuilder.addCalcData(calcData1);
		Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
			aggregatedDataBuilder.addCalcData(calcData2);
		});
		assertEquals("You cannot add twice all the preferences of the teacher : " + teacher1, exception.getMessage());
	}

	@Test
	void testAddCalcDataWithDifferentCourses() {
		AggregatedData.Builder aggregatedData = AggregatedData.Builder.newInstance();
		aggregatedData.addCalcData(calcData1);
		Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
			aggregatedData.addCalcData(calcData3);
		});
		assertEquals("There must be the same courses in the calc datas.", exception.getMessage());
	}

	/**
	 * The aim of this method is to test that when we will concretely and
	 * realistically use the class AgregatedData, it works properly.
	 */
	@Test
	void testAddCalcDataConcretely() throws Exception {
		URL resourceUrl1 = AggregatedData.class.getResource("Saisie_des_voeux_format simple1.ods");
		CalcData cd1;
		try (InputStream stream = resourceUrl1.openStream()) {
			cd1 = CalcData.getData(stream);
		}

		URL resourceUrl2 = AggregatedData.class.getResource("Saisie_des_voeux_format simple2.ods");
		CalcData cd2;
		try (InputStream stream = resourceUrl2.openStream()) {
			cd2 = CalcData.getData(stream);
		}

		AggregatedData.Builder aggregatedDataBuilder = AggregatedData.Builder.newInstance();
		aggregatedDataBuilder.addCalcData(cd1);
		assertDoesNotThrow(() -> {
			aggregatedDataBuilder.addCalcData(cd2);
			aggregatedDataBuilder.build();
		});
		
	}

	@Test
	void testGetTeacherPrefs() {
		AggregatedData.Builder aggregatedDataBuilder = AggregatedData.Builder.newInstance();
		aggregatedDataBuilder.addCalcData(calcData1);
		aggregatedDataBuilder.addCalcData(calcData4);
		AggregatedData aggregatedData = aggregatedDataBuilder.build();

		assertEquals(ImmutableSet.of(coursePref1), aggregatedData.getTeacherPrefs(teacher1));
	}

	@Test
	void testGetCoursePrefs() {
		AggregatedData.Builder aggregatedDataBuilder = AggregatedData.Builder.newInstance();
		aggregatedDataBuilder.addCalcData(calcData1);
		aggregatedDataBuilder.addCalcData(calcData4);
		AggregatedData aggregatedData = aggregatedDataBuilder.build();

		assertEquals(ImmutableSet.of(coursePref1, coursePref4), aggregatedData.getCoursePrefs(course1));
	}

}