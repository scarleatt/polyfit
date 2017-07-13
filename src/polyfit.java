import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.*;
import java.util.*;
import java.text.DecimalFormat;

public class polyfit {
    public static int features = 4;
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

        double[][] X = new double[list.size()][features];
        double[] Y = new double[list.size()];
        String s = "";
        list.remove(0);
        for (int l = 0; l < list.size(); l++) {
            s = list.get(l);
            s = s.substring(s.indexOf(",") + 1);
            list.set(l, s);
        }
        for (int i = 0; i < list.size(); i++) {
            String[] lineArr = list.get(i).split(",");
            for (int j = 0; j < features; j++) {
                s = lineArr[j];
                X[i][0] = 1;
                X[i][j] = Double.parseDouble(s);
                Y[i] = Double.parseDouble(lineArr[3]);
            }
        }

        double[] octave = {};
        double[][] XT = new double[features][list.size()];
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < features; j++) {
                XT[j][i] = X[i][j];
            }
        }
        double[][] XXT = multip(XT, X, features, features, list.size());
//        XXT = pinv(XXT);
//        XXT = multip(XXT, XT, features, list.size(), features);

        System.out.println(list.size());

        for (int i = 0; i < features; i++) {
            for (int j = 0; j < features; j++) {
                System.out.print(XXT[i][j]+"   ");
            }
            System.out.print("\n");
        }

    }

    //矩阵乘积
    public static double[][] multip(double[][] X, double[][] Y, int xlen, int ylen, int len) {
        double[][] temp = new double[xlen][ylen];

        for (int i = 0; i < xlen; i++) {
            for (int j = 0; j < ylen; j++) {
                double sum = 0;
                for (int k = 0; k < len; k++) {
                    sum += X[i][k]*Y[k][j];
                }
                temp[i][j] = sum;
            }
        }

        return temp;
    }

    //矩阵的逆
    public static double[][] pinv(double[][] X) {
        double[][] temp = {{}};

        return temp;
    }

}
