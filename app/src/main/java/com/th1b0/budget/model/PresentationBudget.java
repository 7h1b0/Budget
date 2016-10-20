package com.th1b0.budget.model;

/**
 * Created by 7h1b0.
 */

public class PresentationBudget {

  public static final String OUT = "out";

  private long id;
  private String title;
  private double value;
  private double out;

  public PresentationBudget(long id, String title, double value, double out) {
    this.id = id;
    this.title = title;
    this.value = value;
    this.out = out;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public double getValue() {
    return value;
  }

  public void setValue(double value) {
    this.value = value;
  }

  public double getOut() {
    return out;
  }

  public void setOut(double out) {
    this.out = out;
  }
}
