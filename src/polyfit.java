import java.io.*;
import java.util.*;

public class polyfit {
    public static int features = 4;
    public static void main(String[] args) {
        List<String> list = readData("data/Advertising.csv");
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

        X = pretreatment(X);
        Y = pretreatment(Y);

        //octave = pinv(XT*X)*XT*Y
        double[] octave = {};
        double[][] XT = new double[features][list.size()];
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < features; j++) {
                XT[j][i] = X[i][j];
            }
        }
        double[][] XXT = multip(XT, X);
        XXT = multip((pinv(XXT)), XT);
        octave = multip(XXT, Y);

        for (int i = 0; i < features; i++) {
            System.out.print(octave[i]+"  ");
        }
    }

    //返回第h行第v列的代数余子式
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

    //计算行列式的值
    public static double getNum(double[][] data) {
        double temp = 0;
        double[] num = new double[data.length];

        if (data.length == 2) {
            return data[0][0]*data[1][1]-data[0][1]*data[1][0];
        }

        for (int i = 0; i < data.length; i++) {
            num[i] = data[0][i]*getNum(getAlgebra(data,1,i+1));
            if ((i+2)%2 == 1) {
                num[i] = -num[i];
            }
        }

        for (int i = 0; i < num.length; i++) {
            temp += num[i];
        }
        return temp;
    }

    //矩阵的逆
    public static double[][] pinv(double[][] data) {
        int h = data.length;
        int v = data[0].length;
        double[][] temp = new double[h][v];
        double num = getNum(data);

        for (int i = 0; i < h; i++) {
            for (int j =0 ; j < v; j++) {
                temp[i][j] = getNum(getAlgebra(data,i+1,j+1));
                if ((i+j+2)%2 == 1) {
                    temp[i][j] = -temp[i][j];
                }
                temp[i][j] /= num;
            }
        }
        return temp;
    }

    //矩阵乘积
    public static double[][] multip(double[][] X, double[][] Y) {
        double[][] temp = new double[X.length][Y[0].length];
        if (X[0].length != Y.length) {
            return temp;
        }
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
    public static double[] multip(double[][] X, double[] Y) {
        double[] temp = new double[Y.length];
        if (X[0].length != Y.length) {
            return temp;
        }
        for (int i = 0; i < X.length; i++) {
            double sum = 0;
            for (int j = 0; j < Y.length; j++) {
                sum += X[i][j]*Y[j];
            }
            temp[i] = sum;
        }
        return temp;
    }

    //数据读取
    public static List<String> readData(String pathname) {
        List<String> list = new ArrayList<String>();
        try {
            File file = new File(pathname);
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), "gbk");
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineText = null;

                while ((lineText = bufferedReader.readLine()) != null) {
//                    if (list.size() <= 100) {
                        list.add(lineText);
//                    }
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

    //数据归一化
    public static double[][] pretreatment(double[][] data) {
        int h = data.length;
        int v = data[0].length;
        double maxNum = max(data);
        double minNum = min(data);
        for (int i = 0; i < h; i++)
            for (int j = 0; j < v; j++)
                data[i][j] = (data[i][j]-minNum)/(maxNum-minNum);
        return data;
    }
    public static double[] pretreatment(double[] data) {
        int h = data.length;
        double maxNum = max(data);
        double minNum = min(data);
        for (int i = 0; i < h; i++)
            data[i] = (data[i]-minNum)/(maxNum-minNum);
        return data;
    }

    public static double max(double[][] data) {
        int h = data.length;
        int v = data[0].length;
        double maxNum = data[0][0];

        for (int i = 0; i < h; i++)
            for (int j = 0; j < v; j++)
                if (maxNum < data[i][j])
                    maxNum = data[i][j];
        return maxNum;
    }

    public static double max(double[] data) {
        int h = data.length;
        double maxNum = data[0];
        for (int i = 0; i < h; i++)
            if (maxNum < data[i])
                maxNum = data[i];
        return maxNum;
    }

    public static double min(double[][] data) {
        int h = data.length;
        int v = data[0].length;
        double minNum = data[0][0];

        for (int i = 0; i < h; i++)
            for (int j = 0; j < v; j++)
                if (minNum > data[i][j])
                    minNum = data[i][j];
        return minNum;
    }
    public static double min(double[] data) {
        int h = data.length;
        double minNum = data[0];
        for (int i = 0; i < h; i++)
            if (minNum > data[i])
                minNum = data[i];
        return minNum;
    }
}