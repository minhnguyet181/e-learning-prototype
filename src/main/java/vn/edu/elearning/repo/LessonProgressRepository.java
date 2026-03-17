package vn.edu.elearning.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.elearning.domain.Lesson;
import vn.edu.elearning.domain.LessonProgress;
import vn.edu.elearning.domain.User;

public interface LessonProgressRepository extends JpaRepository<LessonProgress, Long> {
  Optional<LessonProgress> findByStudentAndLesson(User student, Lesson lesson);
}

