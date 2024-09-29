package calculations;

public abstract class Method {
    protected double totalAmount;   // The loan amount
    protected int totalMonths;    // The loan term in months
    protected double annualInterestRate;

    // Constructor for initializing common fields
    public Method(double totalAmount, int years, int months, double annualInterestRate) {
        this.totalAmount = totalAmount;
        this.totalMonths = years * 12 + months;
        this.annualInterestRate = annualInterestRate;
    }

    public abstract double calculateMonthlyPayment();

    public abstract double calculateInterestPayment(int month);

    public double getTotalAmount() {
        return totalAmount;
    }

    public int getTotalMonths() {
        return totalMonths;
    }

    public double getMonthlyInterestRate() {
        return annualInterestRate / 100 / 12;
    }
}
