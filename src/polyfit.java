import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.*;
import java.util.*;
import java.text.DecimalFormat;

public class polyfit {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        try {
            File file = new File("data/Advertising.csv");
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), "gbk");
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineText = null;

                while ((lineText = bufferedReader.readLine()) != null) {
                    list.add(lineText);
                }
                bufferedReader.close();
                read.close();
            } else {
                System.out.println("file not exists.");
            }
        } catch (IOException e) {
            System.out.println("error happened when read file.");
            e.printStackTrace();
        }

        double[][] data = new double[list.size()][4];
        String s = "";
        list.remove(0);
        for (int l = 0; l < list.size(); l++) {
            s = list.get(l);
            s = s.substring(s.indexOf(",") + 1);
            list.set(l, s);
        }
        for (int i = 0; i < list.size(); i++) {
            String[] lineArr = list.get(i).split(",");
            for (int j = 0; j < 4; j++) {
                s = lineArr[j];
                data[i][j] = Double.parseDouble(s);
            }
        }

        double[] x = new double[list.size()];
        double[] y = new double[list.size()];

        try {
            for (int i = 0; i < list.size(); i++) {
                x[i] = data[i][0];
                y[i] = data[i][1];
            }
        } catch (ArrayIndexOutOfBoundsException a) {
            System.out.println("array error");
        }

        estimate(x, y);
    }

    /* 解方程组
    * a + b*sum(x) = sum(y)
    * a*sum(x) + b*sum(x^2) = sum(x*y);
    * */
    public static void estimate(double[] x, double[] y) {
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
