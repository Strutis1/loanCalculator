package calculations;

public class DefermentDecorator extends Method {
    Method method;
    int deferStart;
    int deferEnd;
    int deferDuration;

    public DefermentDecorator(Method method, int deferStart, int deferEnd, int deferDuration) {
        super(method.totalAmount, method.totalMonths, method.annualInterestRate);
        this.method = method;
        this.deferStart = deferStart;
        this.deferEnd = deferEnd;
        this.deferDuration = deferDuration;
    }

    @Override
    public double calculateMonthlyPayment(int month) {
        if (month >= deferStart && month <= deferEnd){
            return 0;
        }
        else if (month > deferEnd){
            return method.calculateMonthlyPayment(month - deferDuration);
        }
        return method.calculateMonthlyPayment(month);
    }

    @Override
    public double calculateInterestPayment(int month) {
        if (month >= deferStart && month <= deferEnd){
            return 0;
        }
        else if (month > deferEnd){
            return method.calculateInterestPayment(month - deferDuration);
        }
        return method.calculateInterestPayment(month);
    }

    @Override
    public double calculatePrincipalPayment(int month) {
        if (month >= deferStart && month <= deferEnd){
            return 0;
        }
        else if (month > deferEnd){
            return method.calculatePrincipalPayment(month - deferDuration);
        }
        return method.calculatePrincipalPayment(month);
    }

    @Override
    public double getRemainingAmount(int month) {
        if (month >= deferStart && month <= deferEnd){
            return method.getRemainingAmount(deferStart);
        }
        else if (month > deferEnd){
            return method.getRemainingAmount(month - deferDuration);
        }
        return method.getRemainingAmount(month);
    }
}
