import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.*;
import java.util.*;
import java.text.DecimalFormat;

public class polyfit {
    public static int features = 4;
    public static void main(String[] args) {
        List<String> list = pretreatment("data/Advertising.csv");
        double[][] X = new double[list.size()][features];
        double[] Y = new double[list.size()];
        String s = "";

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

        //octave = pinv(XT*X)*XT*Y
        double[] octave = {};
        double[][] XT = new double[features][list.size()];
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < features; j++) {
                XT[j][i] = X[i][j];
            }
        }
        double[][] XXT = multip(XT, X);
//        XXT = pinv(XXT);
        XXT = multip(XXT, XT);

//        for (int i = 0; i < features; i++) {
//            for (int j = 0; j < features; j++) {
//                System.out.print(XXT[i][j]+"   ");
//            }
//            System.out.print("\n");
//        }
        double[][] test = {{1,4,7}, {3,0,5}, {-1,9,11}};

        System.out.println(getAlgebra(test, 2,3));


    }
    //计算代数余子式
    public static double[][] getAlgebra(double[][] data, int h, int v) {
        int H = data.length;
        int V = data[0].length;
        double[][] temp = new double[H-1][V-1];
        for (int i = 0; i < H-1; i++) {
            if (i < h-1) {
                for (int j = 0; j < V-1; j++) {
                    if (j < v-1) {
                        temp[i][j] = data[i][j];
                    } else {
                        temp[i][j] = data[i][j+1];
                    }
                }
            } else {
                for (int j = 0; j < V-1; j++) {
                    if (j < v-1) {
                        temp[i][j] = data[i+1][j];
                    } else {
                        temp[i][j] = data[i+1][j+1];
                    }
                }
            }
        }
        return temp;
    }

    public static double getHL2(double[][] data) {
        double num1 = data[0][0]*data[1][1];
        double num2 = -data[0][1]*data[1][0];

        return num1+num2;
    }
    public static double getHL3(double[][] data) {
        double num1 = data[]
    }

    //矩阵的逆
    public static double[][] pinv(double[][] X) {
        double[][] temp = {{}};

        return temp;
    }

    //计算行列式的值
    public static double[][] getNum(double[][] X) {

    }

    //伴随矩阵
    public static double[][] getBy(double[][] X, int h, int w) {
        double[][] temp = new double[h][w];

        return temp;
    }

    //矩阵乘积
    public static double[][] multip(double[][] X, double[][] Y) {
        double[][] temp = new double[X.length][Y[0].length];
        for (int i = 0; i < X.length; i++) {
            for (int j = 0; j < Y[0].length; j++) {
                double sum = 0;
                for (int k = 0; k < Y.length; k++) {
                    sum += X[i][k]*Y[k][j];
                }
                temp[i][j] = sum;
            }
        }
        return temp;
    }

    //数据预处理
    public static List<String> pretreatment(String pathname) {
        List<String> list = new ArrayList<String>();
        try {
            File file = new File(pathname);
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
        list.remove(0);
        return list;
    }
}
