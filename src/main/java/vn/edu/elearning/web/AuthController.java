package vn.edu.elearning.web;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.elearning.domain.User;
import vn.edu.elearning.service.AuthService;
import vn.edu.elearning.web.dto.LoginForm;
import vn.edu.elearning.web.dto.RegisterForm;
import vn.edu.elearning.web.dto.ForgotPasswordForm;

@Controller
public class AuthController {
  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @GetMapping("/login")
  public String loginForm(Model model) {
    if (!model.containsAttribute("form")) {
      model.addAttribute("form", new LoginForm());
    }
    return "auth/login";
  }

  @PostMapping("/login")
  public String login(
      @Valid @ModelAttribute("form") LoginForm form,
      BindingResult bindingResult,
      HttpSession session,
      RedirectAttributes ra) {
    if (bindingResult.hasErrors()) {
      ra.addFlashAttribute("org.springframework.validation.BindingResult.form", bindingResult);
      ra.addFlashAttribute("form", form);
      return "redirect:/login";
    }

    try {
      User u = authService.login(form.getEmail(), form.getPassword());
      session.setAttribute(SessionKeys.USER_ID, u.getId());
      return "redirect:/";
    } catch (IllegalArgumentException e) {
      ra.addFlashAttribute("error", e.getMessage());
      ra.addFlashAttribute("form", form);
      return "redirect:/login";
    }
  }

  @PostMapping("/logout")
  public String logout(HttpSession session) {
    session.invalidate();
    return "redirect:/";
  }

  @GetMapping("/forgot-password")
  public String forgotPasswordForm(Model model) {
    if (!model.containsAttribute("form")) {
      model.addAttribute("form", new ForgotPasswordForm());
    }
    return "auth/forgot-password";
  }

  @PostMapping("/forgot-password")
  public String forgotPassword(
      @Valid @ModelAttribute("form") ForgotPasswordForm form,
      BindingResult bindingResult,
      RedirectAttributes ra) {
    if (bindingResult.hasErrors()) {
      ra.addFlashAttribute("org.springframework.validation.BindingResult.form", bindingResult);
      ra.addFlashAttribute("form", form);
      return "redirect:/forgot-password";
    }
    // Always show success to avoid email enumeration
    ra.addFlashAttribute("success", "Nếu email tồn tại trong hệ thống, bạn sẽ nhận được hướng dẫn đặt lại mật khẩu.");
    return "redirect:/forgot-password";
  }

  @GetMapping("/register")
  public String registerForm(Model model) {
    if (!model.containsAttribute("form")) {
      model.addAttribute("form", new RegisterForm());
    }
    return "auth/register";
  }

  @PostMapping("/register")
  public String register(
      @Valid @ModelAttribute("form") RegisterForm form,
      BindingResult bindingResult,
      RedirectAttributes ra) {
    if (bindingResult.hasErrors()) {
      ra.addFlashAttribute("org.springframework.validation.BindingResult.form", bindingResult);
      ra.addFlashAttribute("form", form);
      return "redirect:/register";
    }

    // Check password confirmation
    if (!form.getPassword().equals(form.getConfirmPassword())) {
      ra.addFlashAttribute("error", "Passwords do not match");
      ra.addFlashAttribute("form", form);
      return "redirect:/register";
    }

    try {
      authService.register(form.getEmail(), form.getFullName(), form.getPassword());
      ra.addFlashAttribute("success", "Registration successful. Please login with your credentials.");
      return "redirect:/login";
    } catch (IllegalArgumentException e) {
      ra.addFlashAttribute("error", e.getMessage());
      ra.addFlashAttribute("form", form);
      return "redirect:/register";
    }
  }
}

