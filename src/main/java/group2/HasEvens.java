package group2;

import static com.github.pambrose.common.util.ArrayUtils.arrayPrint;

public class HasEvens {

    public static boolean[] hasEvens(int[] vals) {
        boolean[] results = new boolean[vals.length];

        for (int i = 0; i < vals.length; i++)
            results[i] = vals[i] % 2 == 0;

        return results;
    }

    public static void main(String[] args) {
        arrayPrint(hasEvens(new int[]{5, 4, 5}));
        arrayPrint(hasEvens(new int[]{4, 6, 8}));
        arrayPrint(hasEvens(new int[]{5}));
        arrayPrint(hasEvens(new int[]{6}));
    }
}
