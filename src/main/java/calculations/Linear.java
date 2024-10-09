package calculations;

public class Linear extends Method {

    private final double principalPayment;

    public Linear(double totalAmount, int totalMonths, double annualInterestRate) {
        super(totalAmount, totalMonths, annualInterestRate);
        this.principalPayment = totalAmount / totalMonths;
    }

    @Override
    public double calculateMonthlyPayment(int month) {
        return principalPayment + calculateInterestPayment(month);
    }

    @Override
    public double calculateInterestPayment(int month) {
        double monthlyRate = getMonthlyInterestRate();
        double remainingAmount = getRemainingAmount(month - 1);
        return remainingAmount * monthlyRate;
    }

    @Override
    public double getRemainingAmount(int month) {
        return totalAmount * (1 - (month - 1) / (double) totalMonths);
    }

    @Override
    public double calculatePrincipalPayment(int month) {
        return principalPayment;
    }
}
