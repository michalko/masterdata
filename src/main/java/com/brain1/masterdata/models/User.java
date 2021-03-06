package com.brain1.masterdata.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class User implements Serializable {
  /**
   *
   */
  private static final long serialVersionUID = 6133645089613436005L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private String firebaseId;

  @NotNull
  private Boolean startedTest = false;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirebaseId() {
    return firebaseId;
  }

  public void setFirebaseId(String firebaseId) {
    this.firebaseId = firebaseId;
  }

  public Boolean getStartedTest() {
    return startedTest;
  }

  public void setStartedTest(Boolean startedTest) {
    this.startedTest = startedTest;
  }

  @Override
  public String toString() {
    return "User [firebaseId=" + firebaseId + ", id=" + id + ", startedTest=" + startedTest + "]";
  }

}
