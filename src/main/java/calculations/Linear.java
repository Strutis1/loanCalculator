package calculations;

public class Linear extends Method{
    public Linear(double totalAmount, int totalMonths, double annualInterestRate){
        super(totalAmount, totalMonths, annualInterestRate);
    }

    @Override
    public double calculateMonthlyPayment() {
        return totalAmount / totalMonths;
    }

    @Override
    public double calculateInterestPayment(int month) {
        double monthlyRate = getMonthlyInterestRate();

        double remainingAmount = totalAmount - (totalAmount * (month - 1) / totalMonths);

        return remainingAmount * monthlyRate;
    }

    @Override
    public double getRemainingAmount(int month) {
        return totalAmount * (1 - (month - 1) / (double) totalMonths);
    }

    @Override
    public double calculatePrincipalPayment(int month) {
        double totalPayment = calculateMonthlyPayment();
        double interestPayment = calculateInterestPayment(month);
        return totalPayment + interestPayment;
    }


}
