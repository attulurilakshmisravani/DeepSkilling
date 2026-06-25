public class FinancialForecasting {

    // Recursive method
    public static double futureValue(double presentValue,
                                     double growthRate,
                                     int years) {

        if (years == 0) {
            return presentValue;
        }

        return futureValue(presentValue,
                           growthRate,
                           years - 1) * (1 + growthRate);
    }

    public static void main(String[] args) {

        double presentValue = 10000;
        double growthRate = 0.10; // 10%
        int years = 5;

        double result = futureValue(
                presentValue,
                growthRate,
                years);

        System.out.println("Future Value after "
                + years + " years = " + result);
    }
}