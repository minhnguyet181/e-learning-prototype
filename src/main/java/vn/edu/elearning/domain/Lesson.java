package vn.edu.elearning.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "lessons")
public class Lesson {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "lesson_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "course_id", nullable = false)
  private Course course;

  @Column(name = "lesson_title", nullable = false, length = 200)
  private String lessonTitle;

  @Lob
  @Column(name = "lesson_content")
  private String lessonContent;

  @Column(name = "lesson_order", nullable = false)
  private Integer lessonOrder = 0;

  @Column(name = "video_url")
  private String videoUrl;

  @Column(name = "document_url")
  private String documentUrl;

  @Column(name = "duration_minutes")
  private Integer durationMinutes;

  @Column(name = "is_published")
  private Boolean published = true;

  @Column(name = "created_at", insertable = false, updatable = false)
  private Instant createdAt;

  @Column(name = "updated_at", insertable = false, updatable = false)
  private Instant updatedAt;

  public Long getId() {
    return id;
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public String getLessonTitle() {
    return lessonTitle;
  }

  public void setLessonTitle(String lessonTitle) {
    this.lessonTitle = lessonTitle;
  }

  public String getLessonContent() {
    return lessonContent;
  }

  public void setLessonContent(String lessonContent) {
    this.lessonContent = lessonContent;
  }

  public Integer getLessonOrder() {
    return lessonOrder;
  }

  public void setLessonOrder(Integer lessonOrder) {
    this.lessonOrder = lessonOrder;
  }

  public String getVideoUrl() {
    return videoUrl;
  }

  public void setVideoUrl(String videoUrl) {
    this.videoUrl = videoUrl;
  }

  public String getDocumentUrl() {
    return documentUrl;
  }

  public void setDocumentUrl(String documentUrl) {
    this.documentUrl = documentUrl;
  }

  public Integer getDurationMinutes() {
    return durationMinutes;
  }

  public void setDurationMinutes(Integer durationMinutes) {
    this.durationMinutes = durationMinutes;
  }

  public Boolean getPublished() {
    return published;
  }

  public void setPublished(Boolean published) {
    this.published = published;
  }
}

