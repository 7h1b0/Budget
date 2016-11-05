package com.th1b0.budget.model;

/**
 * Created by 7h1b0.
 */

public class PresentationBalance {

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

  public double getExpenses() {
    return expenses;
  }

  public double getBalance() {
    return balance;
  }
}
