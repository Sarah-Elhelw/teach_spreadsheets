package io.github.oliviercailloux.teach_spreadsheets.base;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;

import io.github.oliviercailloux.teach_spreadsheets.read.TeacherPrefsInitializer;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

import java.io.InputStream;
import java.util.Set;
import java.util.stream.Collectors;

import org.odftoolkit.simple.SpreadsheetDocument;

/**
 * Immutable. Class used to store information from an excel spreadsheet : one
 * teacher, multiple courses and his/her preferences for these courses.
 */
public class TeacherPrefs {
	private ImmutableSet<CoursePref> coursePrefs;
	private Teacher teacher;

	private TeacherPrefs(Set<CoursePref> coursePrefs, Teacher teacher) {
		this.coursePrefs = ImmutableSet.copyOf(coursePrefs);
		this.teacher = teacher;
	}

	public static TeacherPrefs newInstance(Set<CoursePref> coursePrefs, Teacher teacher) {
		checkNotNull(coursePrefs);
		checkNotNull(teacher);

		for (CoursePref coursePref : coursePrefs) {

			checkArgument(teacher.equals(coursePref.getTeacher()),
					"The set of CoursePref must concern the same Teacher as the one specified.");

			for (CoursePref otherCoursePref : coursePrefs) {
				if (coursePref != otherCoursePref
						&& coursePref.getCourse().equals(otherCoursePref.getCourse())) {
					throw new IllegalArgumentException("You can't have twice the preferences of a course.");
				}
			}
		}

		return new TeacherPrefs(coursePrefs, teacher);
	}

	public ImmutableSet<CoursePref> getCoursePrefs() {
		return coursePrefs;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	/**
	 * @param courseName the name of a Course
	 * @return the CoursePref corresponding to courseName, null if not found
	 */
	public CoursePref getCoursePref(String courseName) {
		checkNotNull(coursePrefs);
		checkNotNull(courseName);
		
		CoursePref cp = null;
		for (CoursePref coursePref : coursePrefs) {
			if (coursePref.getCourse().getName().equals(courseName))
				cp = coursePref;
		}
		checkArgument(cp != null, "The name given in parameter does not match any course.");
		return cp;
	}

	/**
	 * This methods gets the preferences for a given course in the TeacherPrefs.
	 * 
	 * @param course - the Course whose preferences for we want to get.
	 * 
	 * @return the CoursePref corresponding to course.
	 * 
	 * @throws IllegaleArgumentException if the course given in parameter does not
	 *                                   match any course.
	 */
	public CoursePref getCoursePref(Course course) {
		checkNotNull(course);

		for (CoursePref coursePref : coursePrefs) {
			if (coursePref.getCourse().equals(course)) {
				return coursePref;
			}
		}
		throw new IllegalArgumentException("The course given in parameter does not match any course.");
	}
	
	/**
	 * This method returns all the courses presented in the TeacherPrefs.
	 * 
	 * @return - an ImmutableSet of the courses presented in the TeacherPrefs.
	 */
	public ImmutableSet<Course> getCourses(){
		Set<Course> courses = coursePrefs.stream().map(CoursePref::getCourse)
				.collect(Collectors.toSet());
		return ImmutableSet.copyOf(courses);
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("coursePrefs", coursePrefs).add("teacher", teacher).toString();
	}

	/**
	 * Opens and creates a {@link TeacherPrefs} from a document whose path is passed as
	 * a parameter.
	 * 
	 * @param stream - the path of the file to be read
	 * @return a {@link TeacherPrefs} gathering the informations read in the document
	 * @throws Exception to handle the exception type IOException
	 */

	public static TeacherPrefs fromOds(InputStream stream) throws Exception {
			try (SpreadsheetDocument document = SpreadsheetDocument.loadDocument(stream)) {
				return TeacherPrefsInitializer.createTeacherPrefs(document);
			}

	}
}
