package calculations;

public class Annuity extends Method{
    Annuity(double totalAmount, int years, int months, double annualInterestRate){
        super(totalAmount, years, months, annualInterestRate);
    }

    @Override
    public double calculateMonthlyPayment() {
        double monthlyRate = getMonthlyInterestRate();
        return (totalAmount * monthlyRate * Math.pow((1 + monthlyRate),totalAmount))
                / Math.pow((1 + monthlyRate), totalMonths) - 1;
    }

    @Override
    public double calculateInterestPayment(int month) {
        double monthlyRate = getMonthlyInterestRate();
        // The remaining principal decreases each month, so the interest decreases
        double remainingAmount = totalAmount * (Math.pow(1 + monthlyRate, totalMonths - month) - 1) /
                (Math.pow(1 + monthlyRate, totalMonths) - 1);
        return remainingAmount * monthlyRate;
    }
}
