package com.brain2.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotNull  
  private Long correctPrecent;

  @NotNull  
  private Long topicId;

  @NotNull
  private Integer realPostsInTopics;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getCorrectPrecent() {
    return correctPrecent;
  }

  public void setCorrectPrecent(Long correctPrecent) {
    this.correctPrecent = correctPrecent;
  }

  public Long getTopicId() {
    return topicId;
  }

  public void setTopicId(Long topicId) {
    this.topicId = topicId;
  }

  public Integer getRealPostsInTopics() {
    return realPostsInTopics;
  }

  public void setRealPostsInTopics(Integer realPostsInTopics) {
    this.realPostsInTopics = realPostsInTopics;
  }
  
}
