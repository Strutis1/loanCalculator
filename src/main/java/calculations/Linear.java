package calculations;

public class Linear extends Method{
    Linear(double totalAmount, int years, int months, double annualInterestRate){
        super(totalAmount, years, months, annualInterestRate);
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

    public double calculateTotalMonthlyPayment(int month) {
        double principalPayment = calculateMonthlyPayment();
        double interestPayment = calculateInterestPayment(month);
        return principalPayment + interestPayment;
    }


}
