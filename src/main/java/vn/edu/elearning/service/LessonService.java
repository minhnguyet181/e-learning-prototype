package vn.edu.elearning.service;

import java.time.Instant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.elearning.domain.Lesson;
import vn.edu.elearning.domain.LessonProgress;
import vn.edu.elearning.domain.User;
import vn.edu.elearning.repo.LessonProgressRepository;
import vn.edu.elearning.repo.LessonRepository;
import vn.edu.elearning.repo.UserRepository;

@Service
public class LessonService {
  private final LessonRepository lessonRepository;
  private final LessonProgressRepository lessonProgressRepository;
  private final UserRepository userRepository;

  public LessonService(
      LessonRepository lessonRepository,
      LessonProgressRepository lessonProgressRepository,
      UserRepository userRepository) {
    this.lessonRepository = lessonRepository;
    this.lessonProgressRepository = lessonProgressRepository;
    this.userRepository = userRepository;
  }

  public Lesson requireLesson(Long lessonId) {
    return lessonRepository.findById(lessonId).orElseThrow();
  }

  @Transactional
  public void markCompleted(Long studentId, Long lessonId) {
    User student = userRepository.findById(studentId).orElseThrow();
    Lesson lesson = lessonRepository.findById(lessonId).orElseThrow();

    LessonProgress lp =
        lessonProgressRepository
            .findByStudentAndLesson(student, lesson)
            .orElseGet(
                () -> {
                  LessonProgress x = new LessonProgress();
                  x.setStudent(student);
                  x.setLesson(lesson);
                  return x;
                });
    lp.setCompleted(true);
    lp.setCompletedAt(Instant.now());
    lessonProgressRepository.save(lp);
  }
}

