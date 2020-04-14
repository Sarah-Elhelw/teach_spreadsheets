package io.github.oliviercailloux.teach_spreadsheets.read;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import io.github.oliviercailloux.teach_spreadsheets.base.CalcData;
import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;
import io.github.oliviercailloux.teach_spreadsheets.base.CoursePref;

public class CalcDataInitializerTests {

	@Test
	void testreadDocument() throws Exception {
		CalcDataInitializer calcDataInitializer = CalcDataInitializer.newInstance();
		final Path infile = Path.of("src\\test\\resources\\io.github.oliviercailloux.teach_spreadsheets.read\\Saisie_des_voeux_format simple.ods");
		CalcData calcData = calcDataInitializer.readDocument(infile);
		
		// Checking the informations of the teacher:
		String expectedTeacher = "Teacher{lastName=Doe, firstName=John, address=19 rue Jacques Louvel-Tessier, postCode=75010, city=Paris, personalPhone=123456789, mobilePhone=987654321, personalEmail=john.doe@outlook.com, dauphineEmail=john.doe@dauphine.eu, status=MCF, dauphinePhoneNumber=1928373645, office=B048}";
		Teacher actualTeacher = calcData.getTeacher();
		assertEquals(expectedTeacher, actualTeacher.toString());
		
		// Checking the information of the course in the cell P11 of the sheet DE1:
		String expectedCoursePref = "CoursePref{prefCM=UNSPECIFIED, prefTD=UNSPECIFIED, prefCMTD=UNSPECIFIED, prefTP=UNSPECIFIED, prefCMTP=UNSPECIFIED, prefNbGroupsCM=0, prefNbGroupsTD=0, prefNbGroupsCMTD=0, prefNbGroupsTP=0, prefNbGroupsCMTP=0, Course=Course{name=Macroéconomie : analyse de long terme, countGroupsTD=6, countGroupsCMTD=0, countGroupsTP=0, countGroupsCMTP=0, countGroupsCM=1, nbMinutesTD=1170, nbMinutesCMTD=0, nbMinutesTP=0, nbMinutesCMTP=0, nbMinutesCM=1170, studyYear=2016/2017, semester=2}, Teacher=Teacher{lastName=Doe, firstName=John, address=19 rue Jacques Louvel-Tessier, postCode=75010, city=Paris, personalPhone=123456789, mobilePhone=987654321, personalEmail=john.doe@outlook.com, dauphineEmail=john.doe@dauphine.eu, status=MCF, dauphinePhoneNumber=1928373645, office=B048}}";
		CoursePref actualCoursePref = calcData.getCoursePref("Macroéconomie : analyse de long terme");
		assertEquals(expectedCoursePref, actualCoursePref.toString());
	}
}
