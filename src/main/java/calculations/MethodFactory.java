
//panaudojame factory Kūrimo modeli
package calculations;

public class MethodFactory {

    public static Method methodCreator (boolean isLinear, double totalAmount, int totalMonths, double annualInterestRate) {
        if (isLinear) {
            return new Linear(totalAmount, totalMonths, annualInterestRate);
        } else {
            return new Annuity(totalAmount, totalMonths, annualInterestRate);
        }
    }
}
