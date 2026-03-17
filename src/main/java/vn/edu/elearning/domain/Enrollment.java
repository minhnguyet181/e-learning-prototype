package vn.edu.elearning.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(
    name = "enrollments",
    uniqueConstraints = {@UniqueConstraint(name = "unique_enrollment", columnNames = {"student_id", "course_id"})})
public class Enrollment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "enrollment_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "student_id", nullable = false)
  private User student;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "course_id", nullable = false)
  private Course course;

  @Column(name = "enrolled_at", insertable = false, updatable = false)
  private Instant enrolledAt;

  @Enumerated(EnumType.STRING)
  private EnrollmentStatus status = EnrollmentStatus.ACTIVE;

  @Column(name = "progress_percentage")
  private BigDecimal progressPercentage = BigDecimal.ZERO;

  @Column(name = "total_xp")
  private Integer totalXp = 0;

  public Long getId() {
    return id;
  }

  public User getStudent() {
    return student;
  }

  public void setStudent(User student) {
    this.student = student;
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public EnrollmentStatus getStatus() {
    return status;
  }

  public void setStatus(EnrollmentStatus status) {
    this.status = status;
  }

  public BigDecimal getProgressPercentage() {
    return progressPercentage;
  }

  public void setProgressPercentage(BigDecimal progressPercentage) {
    this.progressPercentage = progressPercentage;
  }

  public Integer getTotalXp() {
    return totalXp;
  }

  public void setTotalXp(Integer totalXp) {
    this.totalXp = totalXp;
  }
}

