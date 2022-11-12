package statistiques;

import static java.lang.System.out;
import java.util.Scanner;

public class Statistiques {

    private static final Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
//        float[] list = {0,1,2,3,2,3,3,4,4,3,4,3,4,4,4,4,6,5,5,6,5,5,5,5,5,5};
        int n = 0;
        do {
            out.print("Entrer la taile de la liste: ");
            n = scan.nextInt();
        } while (n <= 0);

        float[] list = new float[n];
        out.println("Entrer les valeurs: ");
        for (int i = 0; i < n; i++) {
            list[i] = scan.nextFloat();
        }
        System.out.println("What do you need ? ");
        out.println("1 ---> Moyenne");
        out.println("2 ---> Variance");
        out.println("3 ---> Ecart-type");
        out.println("4 ---> Mediane");
        out.println("5 ---> Mode");
        out.println("6 ---> All");
        int x = scan.nextInt();
        switch (x) {
            case 1 ->
                out.println("Moyenne : " + calcMoyenne(list));
            case 2 ->
                out.println("Variance : " + calcVariance(list));
            case 3 ->
                out.println("Ecart-type : " + calcEcartType(list));
            case 4 ->
                out.println("Mediane: " + calcMediane(list));
            case 5 ->
                out.println("Mode: " + calcMode(list));
            default -> {
                out.println("Moyenne : " + calcMoyenne(list));
                out.println("Variance : " + calcVariance(list));
                out.println("Ecart-type : " + calcEcartType(list));
                out.println("Mediane: " + calcMediane(list));
                out.println("Mode: " + calcMode(list));
            }
        }
    }

    public static float calcMoyenne(float[] list) {
        float m = 0;
        for (int i = 0; i < list.length; i++) {
            m = m + list[i];
        }
        return m / list.length;
    }

    public static float calcVariance(float[] list) {
        float v = 0;
        for (int i = 0; i < list.length; i++) {
            v = v + list[i] * list[i];
        }
        v = v / list.length;
        float m = calcMoyenne(list);
        return v - m * m;
    }

    public static float calcEcartType(float[] list) {
        return (float) Math.sqrt(calcVariance(list));
    }

    public static float calcMediane(float[] list) {
        triFusion(list, 0, list.length - 1);
        if (list.length % 2 == 0) {
            int med = list.length / 2;
            return (list[med] + list[med - 1]) / 2;
        } else {
            return list[(list.length - 1) / 2];
        }
    }

    public static float calcMode(float[] list) {
        triFusion(list, 0, list.length - 1);
        int n = 1;
        for (int i = 1; i < list.length; i++) {
            if (list[i] != list[i - 1]) {
                n = n + 1;
            }
        }
        float[] list2 = new float[n];
        float[] list3 = new float[n];

        list2[0] = list[0];
        int j = 1;
        for (int i = 1; i < list.length; i++) {
            if (list[i] != list[i - 1]) {
                list2[j] = list[i];
                j = j + 1;
            }
        }

        for (int i = 0; i < list.length; i++) {
            int index = rechercheDichotomique(list2, 0, list2.length - 1, list[i]);
            if (index != -1) {
                list3[index] = list3[index] + 1;
            }
        }
        int max = 0;
        for (int i = 1; i < list3.length; i++) {
            if (list3[i] > list[max]) {
                max = i;
            }
        }
        return list2[max];
    }

    public static void fusion(float[] list, int l, int m, int r) {
        float[] L = new float[m - l + 1];
        float[] R = new float[r - m];
        for (int i = 0; i < L.length; i++) {
            L[i] = list[l + i];
        }
        for (int i = 0; i < R.length; i++) {
            R[i] = list[m + 1 + i];
        }
        int i = 0, j = 0, k = l;
        while (i < L.length && j < R.length) {
            if (L[i] <= R[j]) {
                list[k] = L[i];
                i = i + 1;
            } else {
                list[k] = R[j];
                j = j + 1;
            }
            k = k + 1;
        }
        while (i < L.length) {
            list[k] = L[i];
            i = i + 1;
            k = k + 1;
        }
        while (j < R.length) {
            list[k] = R[j];
            j = j + 1;
            k = k + 1;
        }
    }

    public static void triFusion(float[] list, int l, int r) {
        if (l < r) {
            int m = l + (r - l) / 2;
            triFusion(list, l, m);
            triFusion(list, m + 1, r);
            fusion(list, l, m, r);
        }
    }

    public static int rechercheDichotomique(float[] list, int l, int r, float x) {
        if (r >= l) {
            int m = l + (r - l) / 2;
            if (list[m] == x) {
                return m;
            }
            if (list[m] > x) {
                return rechercheDichotomique(list, l, m - 1, x);
            }
            return rechercheDichotomique(list, m + 1, r, x);
        }
        return -1;
    }
}
