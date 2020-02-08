package com.brain2.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Topictags {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @JsonIgnore
  private Integer id;

  @NotNull
  private String name;

  @NotNull
  @ManyToOne
  @JoinColumn(name="topic_id")
  @JsonBackReference
  private Topic topic;

  public Integer getId() {
    return id;
  }

  public void setId(final Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }


  public Topic getTopic() {
    return topic;
  }

  public void setTopic(final Topic topic) {
    this.topic = topic;
  }

@Override
public String toString() {
	return "Topictags [id=" + id + ", name=" + name + ", topic=" + topic + "]";
}

}
