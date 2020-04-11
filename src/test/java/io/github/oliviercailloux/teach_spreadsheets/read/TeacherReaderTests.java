package io.github.oliviercailloux.teach_spreadsheets.read;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Path;

import org.odftoolkit.simple.SpreadsheetDocument;

import io.github.oliviercailloux.teach_spreadsheets.base.Teacher;

public class TeacherReaderTests {

	@Test
	void testcreateTeacherFromCalc() throws Exception {
		final Path infile = Path.of("src\\test\\resources\\io.github.oliviercailloux.teach_spreadsheets.read\\AA - Saisie des voeux 2016-2017_Emplois-du-temps.ods");
		InputStream stream = Files.newInputStream(infile);
		SpreadsheetDocument document = SpreadsheetDocument.loadDocument(stream);
		TeacherReader teacherReader = TeacherReader.newInstance();
		Teacher teacher = teacherReader.createTeacherFromCalc(document);
		String actual = teacher.toString();
		String expected = "Teacher{lastName=Doe, firstName=John, address=19 rue Jacques Louvel-Tessier, postCode=75010, city=Paris, personalPhone=123456789, mobilePhone=987654321, personalEmail=john.doe@outlook.com, dauphineEmail=john.doe@dauphine.eu, status=MCF, dauphinePhoneNumber=1928373645, office=B048}";
		assertEquals(expected, actual);
		stream.close();
		
	}
}
