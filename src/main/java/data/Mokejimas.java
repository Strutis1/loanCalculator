package data;

public class Mokejimas {

    private int month;
    private double totalPayment;
    private double principal;
    private double interest;
    private double remainingBalance;

    // Constructor
    public Mokejimas(int month, double totalPayment, double principal, double interest, double remainingBalance) {
        this.month = month;
        this.totalPayment = totalPayment;
        this.principal = principal;
        this.interest = interest;
        this.remainingBalance = remainingBalance;
    }

    public int getMonth() {
        return month;
    }

    public double getTotalPayment() {
        return totalPayment;
    }

    public double getPrincipal() {
        return principal;
    }

    public double getInterest() {
        return interest;
    }

    public double getRemainingBalance() {
        return remainingBalance;
    }

}
