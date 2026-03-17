package vn.edu.elearning.service;

import java.time.Instant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.elearning.domain.User;
import vn.edu.elearning.domain.UserRole;
import vn.edu.elearning.repo.UserRepository;

@Service
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordService passwordService;

  public AuthService(UserRepository userRepository, PasswordService passwordService) {
    this.userRepository = userRepository;
    this.passwordService = passwordService;
  }

  public User requireUser(Long userId) {
    return userRepository.findById(userId).orElseThrow();
  }

  @Transactional
  public User register(String username, String email, String fullName, String rawPassword) {
    if (userRepository.existsByUsername(username)) {
      throw new IllegalArgumentException("Username đã tồn tại");
    }
    if (userRepository.existsByEmail(email)) {
      throw new IllegalArgumentException("Email đã tồn tại");
    }

    User u = new User();
    u.setUsername(username);
    u.setEmail(email);
    u.setFullName(fullName);
    u.setRole(UserRole.STUDENT);
    u.setPasswordHash(passwordService.hash(rawPassword));
    return userRepository.save(u);
  }

  @Transactional
  public User login(String username, String rawPassword) {
    User u = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("Sai username hoặc mật khẩu"));
    if (Boolean.FALSE.equals(u.getActive()) || Boolean.TRUE.equals(u.getLocked())) {
      throw new IllegalArgumentException("Tài khoản đang bị khóa hoặc không hoạt động");
    }
    if (!passwordService.matches(rawPassword, u.getPasswordHash())) {
      throw new IllegalArgumentException("Sai username hoặc mật khẩu");
    }
    u.setLastLogin(Instant.now());
    return u;
  }
}

