package com.th1b0.budget.model;

/**
 * Created by 7h1b0.
 */

public class PresentationBalance {

  public static final String INCOMES = "incomes";
  public static final String EXPENSES = "expenses";

  private double incomes;
  private double expenses;
  private double balance;

  public PresentationBalance(double incomes, double expenses, double balance) {
    this.incomes = incomes;
    this.expenses = expenses;
    this.balance = balance;
  }

  public double getIncomes() {
    return incomes;
  }

  public void setIncomes(double incomes) {
    this.incomes = incomes;
  }

  public double getExpenses() {
    return expenses;
  }

  public void setExpenses(double expenses) {
    this.expenses = expenses;
  }

  public double getBalance() {
    return balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }
}
