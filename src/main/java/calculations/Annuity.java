package calculations;

public class Annuity extends Method {
    private final double monthlyPayment;

    public Annuity(double totalAmount, int totalMonths, double annualInterestRate) {
        super(totalAmount, totalMonths, annualInterestRate);
        this.monthlyPayment = calculateMonthlyPayment(0);
    }

    @Override
    public double calculateMonthlyPayment(int month) {
        double monthlyRate = getMonthlyInterestRate();
        return totalAmount * (monthlyRate * Math.pow(1 + monthlyRate, totalMonths))
                / (Math.pow(1 + monthlyRate, totalMonths) - 1);
    }

    @Override
    public double calculateInterestPayment(int month) {
        double remainingAmount = getRemainingAmount(month - 1);
        double monthlyRate = getMonthlyInterestRate();
        return remainingAmount * monthlyRate;
    }

    @Override
    public double getRemainingAmount(int month) {
        double monthlyRate = getMonthlyInterestRate();
        return totalAmount * (Math.pow(1 + monthlyRate, totalMonths) - Math.pow(1 + monthlyRate, month))
                / (Math.pow(1 + monthlyRate, totalMonths) - 1);
    }

    @Override
    public double calculatePrincipalPayment(int month) {
        double interestPayment = calculateInterestPayment(month);
        return monthlyPayment - interestPayment;
    }

    public double getMonthlyPayment(){
        return monthlyPayment;
    }
}
