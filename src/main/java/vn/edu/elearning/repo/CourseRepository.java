package vn.edu.elearning.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.elearning.domain.Course;
import vn.edu.elearning.domain.CourseStatus;

public interface CourseRepository extends JpaRepository<Course, Long> {
  List<Course> findByStatusOrderByIdDesc(CourseStatus status);
}

