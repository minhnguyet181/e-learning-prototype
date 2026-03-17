package vn.edu.elearning.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.elearning.domain.Course;
import vn.edu.elearning.domain.Enrollment;
import vn.edu.elearning.domain.User;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
  Optional<Enrollment> findByStudentAndCourse(User student, Course course);
}

