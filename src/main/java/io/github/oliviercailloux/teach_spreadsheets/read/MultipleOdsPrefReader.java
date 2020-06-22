package io.github.oliviercailloux.teach_spreadsheets.read;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.base.AggregatedData;
import io.github.oliviercailloux.teach_spreadsheets.base.TeacherPrefs;

/**
 * This class contains the function that enables the reading of multiple Ods Pref files.
 */
public class MultipleOdsPrefReader {
	/**
	 * Reads the Ods Pref files from the path given in the parameter.
	 * @param pathToFolder the path to the folder where all the Ods files are.This path should not be null.
	 * @return an AggregatedData containing all the TeacherPrefs from all the read files.
	 * @throws IOException, Exception
	 */
	public static AggregatedData readFilesFromFolder(Path pathToFolder) throws IOException, Exception {
		checkNotNull(pathToFolder);
		Set<TeacherPrefs> tempTeacherPrefsSet = new LinkedHashSet<>();
		try (Stream<Path> walk = Files.walk(pathToFolder)) {
			Set<Path> result = walk.filter(f -> f.toString().endsWith(".ods")).collect(Collectors.toSet());
			for (Path filePath : result) {
				try (InputStream fileStream = Files.newInputStream(filePath)) {
					TeacherPrefs teacherPrefs = TeacherPrefs.fromOds(fileStream);
					tempTeacherPrefsSet.add(teacherPrefs);
				}
			}
		}
		ImmutableSet<TeacherPrefs> teacherPrefsSet = ImmutableSet.copyOf(tempTeacherPrefsSet);
		if (teacherPrefsSet.size() >= 1) {
			AggregatedData.Builder aggregatedDataBuilder = AggregatedData.Builder
					.newInstance(teacherPrefsSet.asList().get(0).getCourses());
			aggregatedDataBuilder.addTeacherPrefsSet(teacherPrefsSet);
			return aggregatedDataBuilder.build();
		} else {
			throw new IllegalArgumentException(
					"The path does not contain an input vows file. An AggregatedData cannot be created.");
		}
	}
}
