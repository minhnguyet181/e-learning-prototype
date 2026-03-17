package vn.edu.elearning.web;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.edu.elearning.domain.User;
import vn.edu.elearning.repo.UserRepository;

@Controller
public class HomeController {
  private final UserRepository userRepository;

  public HomeController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping("/")
  public String home(HttpSession session, Model model) {
    Long userId = (Long) session.getAttribute(SessionKeys.USER_ID);
    if (userId != null) {
      User u = userRepository.findById(userId).orElse(null);
      model.addAttribute("currentUser", u);
    }
    return "home";
  }
}

