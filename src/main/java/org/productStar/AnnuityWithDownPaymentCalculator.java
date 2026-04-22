package org.productStar;


import java.util.ArrayList;
import java.util.List;

    public class AnnuityWithDownPaymentCalculator {
        public static class Payment {
            int month;
            double principalPayment;
            double interestPayment;
            double totalPayment;
            double remainingBalance;

            public Payment(int month, double principalPayment, double interestPayment,
                           double totalPayment, double remainingBalance) {
                this.month = month;
                this.principalPayment = principalPayment;
                this.interestPayment = interestPayment;
                this.totalPayment = totalPayment;
                this.remainingBalance = remainingBalance;
            }
        }

        public static List<Payment> calculateAnnuityWithDownPayment(
                double loanAmount, double downPayment, double annualInterestRate, int years) {

            double principal = loanAmount - downPayment;
            double monthlyRate = annualInterestRate / (100 * 12);
            int quantityOfMonth = years * 12;

            double monthAnnuityPayment = principal *
                    (monthlyRate * Math.pow(1 + monthlyRate, quantityOfMonth)) /
                    (Math.pow(1 + monthlyRate, quantityOfMonth) - 1);

            List<Payment> schedule = new ArrayList<>();
            double remainingBalance = principal;

            for (int month = 1; month <= quantityOfMonth; month++) {
                double interestPayment = remainingBalance * monthlyRate;
                double principalPayment = monthAnnuityPayment - interestPayment;

                if (month == quantityOfMonth) {
                    principalPayment = remainingBalance;
                    monthAnnuityPayment = principalPayment + interestPayment;
                }

                remainingBalance -= principalPayment;

                schedule.add(new Payment(month, principalPayment, interestPayment,
                        monthAnnuityPayment, Math.max(0, remainingBalance)));
            }

            return schedule;
        }
    }


