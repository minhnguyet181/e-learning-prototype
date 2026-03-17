package vn.edu.elearning.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(
    name = "lesson_progress",
    uniqueConstraints = {@UniqueConstraint(name = "unique_progress", columnNames = {"student_id", "lesson_id"})})
public class LessonProgress {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "progress_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "student_id", nullable = false)
  private User student;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lesson_id", nullable = false)
  private Lesson lesson;

  @Column(name = "is_completed")
  private Boolean completed = false;

  @Column(name = "watched_duration_seconds")
  private Integer watchedDurationSeconds = 0;

  @Column(name = "last_position_seconds")
  private Integer lastPositionSeconds = 0;

  @Column(name = "completed_at")
  private Instant completedAt;

  public Long getId() {
    return id;
  }

  public User getStudent() {
    return student;
  }

  public void setStudent(User student) {
    this.student = student;
  }

  public Lesson getLesson() {
    return lesson;
  }

  public void setLesson(Lesson lesson) {
    this.lesson = lesson;
  }

  public Boolean getCompleted() {
    return completed;
  }

  public void setCompleted(Boolean completed) {
    this.completed = completed;
  }

  public Integer getWatchedDurationSeconds() {
    return watchedDurationSeconds;
  }

  public void setWatchedDurationSeconds(Integer watchedDurationSeconds) {
    this.watchedDurationSeconds = watchedDurationSeconds;
  }

  public Integer getLastPositionSeconds() {
    return lastPositionSeconds;
  }

  public void setLastPositionSeconds(Integer lastPositionSeconds) {
    this.lastPositionSeconds = lastPositionSeconds;
  }

  public Instant getCompletedAt() {
    return completedAt;
  }

  public void setCompletedAt(Instant completedAt) {
    this.completedAt = completedAt;
  }
}

