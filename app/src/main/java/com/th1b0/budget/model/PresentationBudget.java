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

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    PresentationBudget that = (PresentationBudget) o;

    if (Double.compare(that.value, value) != 0) return false;
    if (Double.compare(that.out, out) != 0) return false;
    return title != null ? title.equals(that.title) : that.title == null;
  }

  @Override public int hashCode() {
    int result;
    long temp;
    result = title != null ? title.hashCode() : 0;
    temp = Double.doubleToLongBits(value);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(out);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    return result;
  }
}
