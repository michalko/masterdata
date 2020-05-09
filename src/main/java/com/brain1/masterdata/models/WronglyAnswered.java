package com.brain1.masterdata.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

@Entity
@ToString
@AllArgsConstructor
@Builder
public class WronglyAnswered {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotNull
  private User user;

  @NotNull
  private Post post;

  @NotNull
  private Topic topic;

  @NotNull
  private Long answeredWronglyTimes;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Post getPost() {
    return post;
  }

  public void setPost(Post post) {
    this.post = post;
  }

  public Topic getTopic() {
    return topic;
  }

  public void setTopic(Topic topic) {
    this.topic = topic;
  }

  public Long getAnsweredWronglyTimes() {
    return answeredWronglyTimes;
  }

  public void setAnsweredWronglyTimes(Long answeredWronglyTimes) {
    this.answeredWronglyTimes = answeredWronglyTimes;
  }
}
