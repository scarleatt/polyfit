import java.text.DecimalFormat;
import java.util.Random;

public class polyfit {
    private final static int n = 20;
    public static void main(String[] args) {
        Random random = new Random();
        double[] x = new double[n];
        double[] y = new double[n];

        for (int i = 0; i < n; i++) {
            x[i] = Double.valueOf(Math.floor(random.nextDouble()*(99-1)));
            y[i] = Double.valueOf(Math.floor(random.nextDouble()*(999-1)));
        }

        estimate(x, y, x.length);
    }

    /*解方程组
    * a + b*sum(x) = sum(y)
    * a*sum(x) + b*sum(x^2) = sum(x*y);
    * */
    public static void estimate(double[] x, double[] y, int n) {
        double b = getB(x, y);
        double a = getA(x, y, b);
        DecimalFormat df = new DecimalFormat("#,##0.00");
        System.out.println("y="+df.format(a)+"x+"+df.format(b));
    }

    public static double getA(double[] x, double[] y, double b) {
        double n = x.length;
        return (sum(y)-sum(x)*b)/n;
    }

    public static double getB(double[] x, double[] y) {
        double n = x.length;
        return (sum(x)*sum(y)-n*pSum(x,y))/(Math.pow(sum(x),2)-n*sqSum(x));
    }

    public static double sum(double[] ds) {
        double  s = 0;
        for (double d:ds)
            s = s+d;
        return s;
    }

    //计算平方和
    public static double sqSum(double[] ds) {
        double s = 0;
        for (double d:ds)
            s = s + Math.pow(d, 2);
        return s;
    }

    //计算sum(x*y)
    public static double pSum(double[] x, double[] y) {
        double s = 0;
        for (int i = 0; i < x.length; i++)
            s = s + x[i] * y[i];
        return s;
    }
}
