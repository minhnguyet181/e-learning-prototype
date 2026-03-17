package vn.edu.elearning.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.elearning.domain.Course;
import vn.edu.elearning.domain.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
  List<Lesson> findByCourseOrderByLessonOrderAsc(Course course);
}

