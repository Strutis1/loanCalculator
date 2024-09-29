package calculations;

public class Annuity extends Method{
    Annuity(double totalAmount, int totalMonths, double annualInterestRate){
        super(totalAmount, totalMonths, annualInterestRate);
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
        double remainingAmount = totalAmount * (Math.pow(1 + monthlyRate, totalMonths - month) - 1) /
                (Math.pow(1 + monthlyRate, totalMonths) - 1);
        return remainingAmount * monthlyRate;
    }

    @Override
    public double getRemainingAmount(int month) {
        double interestRatePerMonth = getMonthlyInterestRate();
        double monthlyPayment = calculateMonthlyPayment();

        double remainingAmount = totalAmount * Math.pow(1 + interestRatePerMonth, month)
                - (monthlyPayment * (Math.pow(1 + interestRatePerMonth, month) - 1)) / interestRatePerMonth;
        return remainingAmount;
    }


}
