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

        double remainingPrincipal = totalAmount - (totalAmount * (month - 1) / totalMonths);

        return remainingPrincipal * monthlyRate;
    }

    @Override
    public double getRemainingAmount(int month) {
        return totalAmount * (1 - (month - 1) / (double) totalMonths);
    }

    public double calculateTotalMonthlyPayment(int month) {
        double principalPayment = calculateMonthlyPayment();
        double interestPayment = calculateInterestPayment(month);
        return principalPayment + interestPayment;
    }


}
