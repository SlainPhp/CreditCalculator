package org.productStar;

import java.util.List;

public interface ICalculator {
    double getOverpayments();

    void setPrincipal(double principal);
    void setAnnualInterestRate(double annualInterestRate);
    void setYears(int years);

    void setOverpayments(double overpayments);

    void calculatePayments();
    double getTotalPayment();
    double getTotalInterest();
    double getPrincipal();

    int getYears();
    List<Payment> getPaymentsSchedule();
}
