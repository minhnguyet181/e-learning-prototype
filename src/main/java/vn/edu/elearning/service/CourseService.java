package vn.edu.elearning.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.elearning.domain.*;
import vn.edu.elearning.repo.CourseRepository;
import vn.edu.elearning.repo.EnrollmentRepository;
import vn.edu.elearning.repo.LessonRepository;
import vn.edu.elearning.repo.UserRepository;

@Service
public class CourseService {
  private final CourseRepository courseRepository;
  private final LessonRepository lessonRepository;
  private final EnrollmentRepository enrollmentRepository;
  private final UserRepository userRepository;

  public CourseService(
      CourseRepository courseRepository,
      LessonRepository lessonRepository,
      EnrollmentRepository enrollmentRepository,
      UserRepository userRepository) {
    this.courseRepository = courseRepository;
    this.lessonRepository = lessonRepository;
    this.enrollmentRepository = enrollmentRepository;
    this.userRepository = userRepository;
  }

  public List<Course> listPublishedCourses() {
    return courseRepository.findByStatusOrderByIdDesc(CourseStatus.PUBLISHED);
  }

  public Course requireCourse(Long courseId) {
    return courseRepository.findById(courseId).orElseThrow();
  }

  public List<Lesson> listLessons(Course course) {
    return lessonRepository.findByCourseOrderByLessonOrderAsc(course);
  }

  public boolean isEnrolled(User student, Course course) {
    return enrollmentRepository.findByStudentAndCourse(student, course).isPresent();
  }

  @Transactional
  public void enroll(Long studentId, Long courseId, String coursePassword) {
    User student = userRepository.findById(studentId).orElseThrow();
    Course course = courseRepository.findById(courseId).orElseThrow();

    if (student.getRole() != UserRole.STUDENT) {
      throw new IllegalArgumentException("Chỉ STUDENT mới có thể đăng ký khóa học");
    }
    if (course.getStatus() != CourseStatus.PUBLISHED) {
      throw new IllegalArgumentException("Khóa học chưa được publish");
    }
    if (enrollmentRepository.findByStudentAndCourse(student, course).isPresent()) {
      return;
    }
    if (course.getEnrollPassword() != null && !course.getEnrollPassword().isBlank()) {
      if (coursePassword == null || !course.getEnrollPassword().equals(coursePassword)) {
        throw new IllegalArgumentException("Mật khẩu khóa học không đúng");
      }
    }

    Enrollment e = new Enrollment();
    e.setStudent(student);
    e.setCourse(course);
    e.setStatus(EnrollmentStatus.ACTIVE);
    enrollmentRepository.save(e);
  }

  @Transactional
  public void seedIfEmpty() {
    if (userRepository.count() > 0 || courseRepository.count() > 0) {
      return;
    }

    // Users + courses seeded from code (avoid placeholder hashes in SQL).
    // Actual data created in DataSeeder.
  }
}

