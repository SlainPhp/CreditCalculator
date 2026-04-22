package org.productStar;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите сумму долга:");
        if (!scanner.hasNextDouble()) {
            throw new RuntimeException("Неверный ввод");
        }
        double principal = scanner.nextDouble();

        System.out.println("Введите срок кредита в годах:");
        if (!scanner.hasNextInt()) {
            throw new RuntimeException("Неверный ввод");
        }
        int years = scanner.nextInt();

        System.out.println("Введите процентную ставку:");
        if (!scanner.hasNextDouble()) {
            throw new RuntimeException("Неверный ввод");
        }
        double annualInterestRate = scanner.nextDouble();

        System.out.println("Выберите вид платежа (1 - аннуитетный, 2 - дифференцированный):");

        int paymentType = scanner.nextInt();
        if (paymentType != 1 && paymentType != 2) {
            throw new RuntimeException("Введите один из предложенных вариантов ответа");
        }

        ICalculator calculator = null;
        double monthlyRate = annualInterestRate / (100 * 12);
        double quantityOfMonth = years * 12;
        double monthAnnuityPayment = principal * (monthlyRate * Math.pow(1 + monthlyRate, quantityOfMonth)) / (Math.pow(1 + monthlyRate, quantityOfMonth) - 1);
        double annuityOverpayment = monthAnnuityPayment * quantityOfMonth - principal;


        double differentiatedOverpayment = principal * monthlyRate * ((quantityOfMonth + 1) / 2.0);


        if (paymentType == 1) {
            calculator = new AnnuityCalculator();
            calculator.setOverpayments(annuityOverpayment);
        }
        if (paymentType == 2) {
            calculator = new DifferentiatedCalculator();
            calculator.setOverpayments(differentiatedOverpayment);
        }
        if (calculator == null) {
            throw new RuntimeException("Неверный вариант ответа");
        }

        calculator.setPrincipal(principal);
        calculator.setAnnualInterestRate(annualInterestRate);
        calculator.setYears(years);

        calculator.calculatePayments();

        printSchedule(calculator);


    }

    private static void printSchedule(ICalculator calculator) {
        System.out.println("График платежей:");
        for (Payment payment : calculator.getPaymentsSchedule()) {
            System.out.printf("Месяц: %d, Платеж по основному долгу: %.2f, Процентный платеж: %.2f, Общий платеж: %.2f%n",
                    payment.getMonth(), payment.getPrincipalPayment(), payment.getInterestPayment(), payment.getTotalPayment());

        }


        System.out.printf("Общая сумма выплат: %.2f%n", calculator.getTotalPayment());
        System.out.printf("Общая сумма процентов: %.2f%n", calculator.getTotalInterest());
        System.out.println("Общая сумма переплат:" + " " + calculator.getOverpayments());

    }
}