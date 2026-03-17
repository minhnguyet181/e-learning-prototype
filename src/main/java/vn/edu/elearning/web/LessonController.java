package vn.edu.elearning.web;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.elearning.domain.Course;
import vn.edu.elearning.domain.Lesson;
import vn.edu.elearning.domain.User;
import vn.edu.elearning.repo.UserRepository;
import vn.edu.elearning.service.CourseService;
import vn.edu.elearning.service.LessonService;

@Controller
public class LessonController {
  private final LessonService lessonService;
  private final CourseService courseService;
  private final UserRepository userRepository;

  public LessonController(
      LessonService lessonService, CourseService courseService, UserRepository userRepository) {
    this.lessonService = lessonService;
    this.courseService = courseService;
    this.userRepository = userRepository;
  }

  @GetMapping("/courses/{courseId}/lessons/{lessonId}")
  public String view(
      @PathVariable Long courseId, @PathVariable Long lessonId, HttpSession session, Model model) {
    Course course = courseService.requireCourse(courseId);
    Lesson lesson = lessonService.requireLesson(lessonId);

    Long userId = (Long) session.getAttribute(SessionKeys.USER_ID);
    User currentUser = null;
    boolean enrolled = false;
    if (userId != null) {
      currentUser = userRepository.findById(userId).orElse(null);
      if (currentUser != null) {
        enrolled = courseService.isEnrolled(currentUser, course);
      }
    }

    model.addAttribute("course", course);
    model.addAttribute("lesson", lesson);
    model.addAttribute("currentUser", currentUser);
    model.addAttribute("enrolled", enrolled);
    return "lessons/view";
  }

  @PostMapping("/courses/{courseId}/lessons/{lessonId}/complete")
  public String markCompleted(
      @PathVariable Long courseId,
      @PathVariable Long lessonId,
      HttpSession session,
      RedirectAttributes ra) {
    Long userId = (Long) session.getAttribute(SessionKeys.USER_ID);
    if (userId == null) {
      ra.addFlashAttribute("error", "Bạn cần đăng nhập để thao tác");
      return "redirect:/login";
    }
    try {
      lessonService.markCompleted(userId, lessonId);
      ra.addFlashAttribute("success", "Đã đánh dấu hoàn thành bài học");
    } catch (Exception e) {
      ra.addFlashAttribute("error", "Không thể cập nhật tiến độ");
    }
    return "redirect:/courses/" + courseId + "/lessons/" + lessonId;
  }
}

