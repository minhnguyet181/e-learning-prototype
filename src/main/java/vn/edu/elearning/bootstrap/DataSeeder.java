package vn.edu.elearning.bootstrap;

import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.elearning.domain.*;
import vn.edu.elearning.repo.CourseRepository;
import vn.edu.elearning.repo.LessonRepository;
import vn.edu.elearning.repo.UserRepository;
import vn.edu.elearning.service.PasswordService;

@Component
public class DataSeeder implements CommandLineRunner {
  private final UserRepository userRepository;
  private final CourseRepository courseRepository;
  private final LessonRepository lessonRepository;
  private final PasswordService passwordService;

  public DataSeeder(
      UserRepository userRepository,
      CourseRepository courseRepository,
      LessonRepository lessonRepository,
      PasswordService passwordService) {
    this.userRepository = userRepository;
    this.courseRepository = courseRepository;
    this.lessonRepository = lessonRepository;
    this.passwordService = passwordService;
  }

  @Override
  @Transactional
  public void run(String... args) {
    if (userRepository.count() > 0) {
      return;
    }

    User admin = new User();
    admin.setUsername("admin");
    admin.setEmail("admin@elearning.edu.vn");
    admin.setFullName("Quản trị viên hệ thống");
    admin.setRole(UserRole.ADMIN);
    admin.setPasswordHash(passwordService.hash("admin123"));

    User teacher = new User();
    teacher.setUsername("teacher");
    teacher.setEmail("teacher@elearning.edu.vn");
    teacher.setFullName("Giảng viên demo");
    teacher.setRole(UserRole.TEACHER);
    teacher.setPasswordHash(passwordService.hash("teacher123"));

    User student = new User();
    student.setUsername("student");
    student.setEmail("student@elearning.edu.vn");
    student.setFullName("Sinh viên demo");
    student.setRole(UserRole.STUDENT);
    student.setPasswordHash(passwordService.hash("student123"));

    userRepository.saveAll(List.of(admin, teacher, student));

    Course c1 = new Course();
    c1.setCourseCode("SE-101");
    c1.setCourseName("Nhập môn Kỹ nghệ phần mềm");
    c1.setDescription("Prototype course để demo luồng web: list → detail → lesson → progress.");
    c1.setTeacher(teacher);
    c1.setStatus(CourseStatus.PUBLISHED);
    c1.setSemester("2026-1");
    c1.setAcademicYear("2026");
    c1.setEnrollPassword("join123");

    courseRepository.save(c1);

    Lesson l1 = new Lesson();
    l1.setCourse(c1);
    l1.setLessonOrder(1);
    l1.setLessonTitle("Bài 1: Giới thiệu & SRS/ERD");
    l1.setLessonContent("Nội dung demo: SRS, ERD, yêu cầu chức năng.");

    Lesson l2 = new Lesson();
    l2.setCourse(c1);
    l2.setLessonOrder(2);
    l2.setLessonTitle("Bài 2: MVC + Thymeleaf flow");
    l2.setLessonContent("Nội dung demo: Controller → Service → Repository → View.");

    lessonRepository.saveAll(List.of(l1, l2));
  }
}

