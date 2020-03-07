package com.brain2.demo.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Post {

  @Id
  private String id;

  @NotNull
  private Double correctPrecent;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "topic_id")
  private Topic topic;

  @NotNull
  private Integer realPostsInTopics;

  @NotNull
  private String topicName;

  public Integer getRealPostsInTopics() {
    return realPostsInTopics;
  }

  public void setRealPostsInTopics(final Integer realPostsInTopics) {
    this.realPostsInTopics = realPostsInTopics;
  }

  public Topic getTopic() {
    return topic;
  }

  public void setTopic(Topic topic) {
    this.topic = topic;
  }

  @Override
  public String toString() {
    return "Post [correctPrecent=" + correctPrecent + ", id=" + id + ", realPostsInTopics=" + realPostsInTopics
        + ", topic=" + topic + "]";
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Double getCorrectPrecent() {
    return correctPrecent;
  }

  public void setCorrectPrecent(Double correctPrecent) {
    this.correctPrecent = correctPrecent;
  }

  public String getTopicName() {
    return topicName;
  }

  public void setTopicName(String topicName) {
    this.topicName = topicName;
  }

}
