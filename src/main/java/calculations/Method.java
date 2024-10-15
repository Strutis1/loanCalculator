//jau buvo naudojamas strategy elgsenos modelis


package calculations;

public abstract class Method {
    protected double totalAmount;
    protected int totalMonths;
    protected double annualInterestRate;

    public Method(double totalAmount, int totalMonths, double annualInterestRate) {
        this.totalAmount = totalAmount;
        this.totalMonths = totalMonths;
        this.annualInterestRate = annualInterestRate;
    }

    public abstract double calculateMonthlyPayment(int month);

    public abstract double calculateInterestPayment(int month);

    public abstract double calculatePrincipalPayment(int month);


    public double getMonthlyInterestRate() {
        return annualInterestRate / 100 / 12;
    }

    public abstract double getRemainingAmount(int month);

}
