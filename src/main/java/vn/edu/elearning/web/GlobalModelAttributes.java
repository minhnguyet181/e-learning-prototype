package vn.edu.elearning.web;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import vn.edu.elearning.domain.User;
import vn.edu.elearning.repo.UserRepository;

@ControllerAdvice(annotations = Controller.class)
public class GlobalModelAttributes {
  private final UserRepository userRepository;

  public GlobalModelAttributes(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @ModelAttribute("currentUser")
  public User currentUser(HttpSession session) {
    Long userId = (Long) session.getAttribute(SessionKeys.USER_ID);
    if (userId == null) {
      return null;
    }
    return userRepository.findById(userId).orElse(null);
  }
}

