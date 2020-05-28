package io.github.oliviercailloux.teach_spreadsheets.base;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.io.InputStream;
import java.net.URL;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class AggregatedDataTests {

	private static Teacher teacher1 = Teacher.Builder.newInstance().setLastName("Dupond")
			.setDauphineEmail("jack.dupond@dauphine.fr").build();
	private static Teacher teacher2 = Teacher.Builder.newInstance().setLastName("Dupont")
			.setDauphineEmail("jane.dupont@dauphine.fr").build();

	private static Course course1 = Course.Builder.newInstance().setName("Algèbre").setStudyYear("2016").setSemester(1)
			.setCountGroupsTD(6).setnbMinutesTD(900).build();
	private static Course course2 = Course.Builder.newInstance().setName("Analyse").setStudyYear("2016").setSemester(1)
			.setCountGroupsTD(5).setnbMinutesTD(700).build();

	private static CoursePref coursepref1 = CoursePref.Builder.newInstance(course1, teacher1).setPrefTD(Preference.A)
			.build();
	private static CoursePref coursepref2 = CoursePref.Builder.newInstance(course1, teacher1).setPrefTD(Preference.B)
			.build();
	private static CoursePref coursepref3 = CoursePref.Builder.newInstance(course2, teacher2).setPrefTD(Preference.C)
			.build();

	private static CalcData calcData1 = CalcData.newInstance(Set.of(coursepref1), teacher1);
	private static CalcData calcData2 = CalcData.newInstance(Set.of(coursepref2), teacher1);
	private static CalcData calcData3 = CalcData.newInstance(Set.of(coursepref3), teacher2);

	@Test
	void testAddCalcDataWithDifferentCourses() {
		AggregatedData.Builder aggregatedDataBuilder = AggregatedData.Builder.newInstance();
		aggregatedDataBuilder.addCalcData(calcData1);
		Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
			aggregatedDataBuilder.addCalcData(calcData2);
		});
		assertEquals("You cannot add twice all the preferences of a teacher.", exception.getMessage());
	}

	@Test
	void testAddCalcDataWithSameTeacher() {
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
		assertAll(() -> {
			aggregatedDataBuilder.addCalcData(cd2);
			aggregatedDataBuilder.build();
		});

	}

}