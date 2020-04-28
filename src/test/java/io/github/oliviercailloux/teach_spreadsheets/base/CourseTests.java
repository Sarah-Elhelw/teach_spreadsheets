package io.github.oliviercailloux.teach_spreadsheets.base;

import org.junit.jupiter.api.Test;

import io.github.oliviercailloux.teach_spreadsheets.base.Course;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CourseTests {
	@Test
	void testBuild() {
		Course.Builder courseBuilder = Course.Builder.newInstance();
		
		assertThrows(IllegalArgumentException.class, () -> {
			courseBuilder.build();
		});
		
		courseBuilder.setSemester(1);
		
		assertThrows(IllegalArgumentException.class, () -> {
			courseBuilder.build();
		});
		
		courseBuilder.setCountGroupsCM(20);
		
		assertThrows(IllegalArgumentException.class, () -> {
			courseBuilder.build();
		});
		
		courseBuilder.setnbMinutesCM(20);
		
		assertThrows(IllegalStateException.class, () -> {
			courseBuilder.build();
		});
	}
}
