package io.github.oliviercailloux.teach_spreadsheets.read;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import org.odftoolkit.simple.SpreadsheetDocument;

import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

public class TeacherReaderTests {

	@Test
	void testcreateTeacherFromCalc() throws Exception {
		URL resourceUrl = PrefsInitializer.class.getResource("Saisie_des_voeux_format simple.ods");
		try (InputStream stream = resourceUrl.openStream()) {
			try (SpreadsheetDocument document = SpreadsheetDocument.loadDocument(stream)) {
				TeacherReader teacherReader = TeacherReader.newInstance();
				Teacher teacher = teacherReader.createTeacherFromCalc(document);
				String actual = teacher.toString();
				String expected = "Teacher{lastName=Doe, firstName=John, address=19 rue Jacques Louvel-Tessier, postCode=75010, city=Paris, personalPhone=123456789, mobilePhone=987654321, personalEmail=john.doe@outlook.com, dauphineEmail=john.doe@dauphine.eu, status=MCF, dauphinePhoneNumber=1928373645, office=B048}";
				assertEquals(expected, actual);
			}
		}
	}
}
