package vn.edu.elearning.web;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.elearning.domain.Course;
import vn.edu.elearning.domain.Lesson;
import vn.edu.elearning.domain.User;
import vn.edu.elearning.repo.UserRepository;
import vn.edu.elearning.service.CourseService;

import java.util.List;

@Controller
@Validated
public class CourseController {
  private final CourseService courseService;
  private final UserRepository userRepository;

  public CourseController(CourseService courseService, UserRepository userRepository) {
    this.courseService = courseService;
    this.userRepository = userRepository;
  }

  @GetMapping("/courses")
  public String list(Model model) {
    model.addAttribute("courses", courseService.listPublishedCourses());
    return "courses/list";
  }

  @GetMapping("/courses/{courseId}")
  public String detail(@PathVariable Long courseId, HttpSession session, Model model) {
    Course course = courseService.requireCourse(courseId);
    List<Lesson> lessons = courseService.listLessons(course);

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
    model.addAttribute("lessons", lessons);
    model.addAttribute("currentUser", currentUser);
    model.addAttribute("enrolled", enrolled);
    return "courses/detail";
  }

  @PostMapping("/courses/{courseId}/enroll")
  public String enroll(
      @PathVariable Long courseId,
      @RequestParam(name = "password", required = false) String password,
      HttpSession session,
      RedirectAttributes ra) {
    Long userId = (Long) session.getAttribute(SessionKeys.USER_ID);
    if (userId == null) {
      ra.addFlashAttribute("error", "You need to login to enroll in a course");
      return "redirect:/login";
    }
    try {
      courseService.enroll(userId, courseId, password);
      ra.addFlashAttribute("success", "Successfully enrolled in course");
    } catch (IllegalArgumentException e) {
      ra.addFlashAttribute("error", e.getMessage());
    }
    return "redirect:/courses/" + courseId;
  }
}

