package three_sum;

import java.util.*;

/**
 * Created by Allan Wang on 2017-11-01.
 *
 * The following solution passed the test with a runtime of 194ms
 */
public class ThreeSum {

    public static void main(String[] args) {
        int[] data = {-1, 0, 1, 2, -1, -4};
        List<List<Integer>> result = new ThreeSum().threeSum(data);
        System.out.println(result.toString());
    }

    public List<List<Integer>> threeSum(int[] nums) {
        int zeroCount = 0;
        Set<Integer> negativeSet = new HashSet<>();
        Set<Integer> positiveSet = new HashSet<>();
        Set<Integer> negativeDups = new HashSet<>();
        Set<Integer> positiveDups = new HashSet<>();
        for (int i : nums) {
            if (i == 0) zeroCount++;
            else if (i < 0) {
                if (!negativeSet.add(i)) negativeDups.add(i);
            } else {
                if (!positiveSet.add(i)) positiveDups.add(i);
            }
        }
        List<List<Integer>> output = new ArrayList<>();

        if (!positiveSet.isEmpty() && !negativeSet.isEmpty()) {
            List<Integer> negativeData = new ArrayList<>(negativeSet);
            Collections.sort(negativeData);
            List<Integer> positiveData = new ArrayList<>(positiveSet);
            Collections.sort(positiveData);

            getTriples(negativeData, -positiveData.get(positiveData.size() - 1), negativeDups, positiveSet, output);
            getTriples(positiveData, -negativeData.get(0), positiveDups, negativeSet, output);

        /*
         * Get results with one zero
         */
            if (zeroCount > 0)
                for (int i : positiveData) {
                    if (negativeSet.contains(-i)) {
                        add(output, -i, 0, i);
                    }
                }
        }
        /*
         * Get result with three zeroes
         */
        if (zeroCount >= 3)
            add(output, 0, 0, 0);

        return output;
    }

    private void getTriples(List<Integer> data, int bound, Set<Integer> dupSet, Set<Integer> sumSet, List<List<Integer>> output) {
        ListIterator<Integer> mainIter = data.listIterator();
        boolean isNegative = bound < 0;
        while (mainIter.hasNext()) {
            int i = mainIter.next();
            if (isNegative && bound > i) continue;
            else if (!isNegative && bound < i) continue;
            if (dupSet.contains(i)) {
                int sum = -i - i;
                if (sumSet.contains(sum))
                    add(output, sum, i, i);
            }
            ListIterator<Integer> subIter = data.listIterator(mainIter.nextIndex());
            while (subIter.hasNext()) {
                int j = subIter.next();
                int sum = -i - j;
                if (sumSet.contains(sum))
                    add(output, sum, i, j);
            }
        }
    }

    private void add(List<List<Integer>> output, int i, int j, int k) {
        List<Integer> result = new ArrayList<>();
        result.add(i);
        result.add(j);
        result.add(k);
        output.add(result);
    }

    private static void p(String s, Object... args) {
        System.out.println(String.format(s, args));
    }

}
