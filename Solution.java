import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiConsumer;

/**
 * Created by Allan Wang on 2017-11-08.
 */
public class Solution {

    private static class BigBoolArray {

        final long size;
        private final boolean[][] data;
        private long trueCount;

        private BigBoolArray(long size) {
            this.size = size;
            int slots = (int) Math.ceil(((double) size) / Integer.MAX_VALUE);
            System.out.println("slot size " + slots);
            data = new boolean[slots][];
            for (int i = 0; size > 0; i++) {
                long chunk = Math.min(size, Integer.MAX_VALUE);
                size -= chunk;
                System.out.println("size " + size);
                data[i] = new boolean[(int) chunk];
                Arrays.fill(data[i], true);
            }
            trueCount = this.size;
        }

        private boolean get(long index) {
            if (index < 0L || index >= size) return false;
            long section = index / Integer.MAX_VALUE;
            int trueIndex = (int) (index - (section * Integer.MAX_VALUE));
            return data[(int) section][trueIndex];
        }

        private void set(long index, boolean value) {
            if (index < 0L || index >= size) return;
            long section = index / Integer.MAX_VALUE;
            int trueIndex = (int) (index - (section * Integer.MAX_VALUE));
            if (data[(int) section][trueIndex] == value) return;
            data[(int) section][trueIndex] = value;
            if (value) trueCount++;
            else trueCount--;
        }

        public long getTrueCount() {
            return trueCount;
        }

        public void iterate(BiConsumer<Long, Boolean> consumer) {
            long index = 0;
            for (boolean[] array : data)
                for (boolean val : array) {
                    consumer.accept(index, val);
                    index++;
                }
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        long n = scan.nextLong();
        long k = scan.nextLong();
        System.out.println(getSquareFreeNumberCount(n, k));
    }

    private static long getSquareFreeNumberCount(long n, long k) {
        if (n <= 0 || k <= 0)
            return 0;
        int root = (int) Math.pow(n, (1.0 / k));
//        List<Long> primes = findPrimes(root);
        BigBoolArray isPowerFree = new BigBoolArray(n + 1);
        return 5L;
//        for (long prime : primes) {
//            double powD = Math.pow(prime, k);
//            int bound = (int) (n / powD);
//            int pow = (int) powD;
//            for (int i = 1; i <= bound; i++)
//                isPowerFree.set(i * pow, false);
//        }
//        return isPowerFree.getTrueCount() - 1; //-1 as 0 is true
    }

    private static List<Long> findPrimes(long n) {
        BigBoolArray isPrime = new BigBoolArray(n + 1);
        long nsqrt = (long) Math.sqrt(n);
        // mark non-primes <= n using Sieve of Eratosthenes
        for (long factor = 2; factor <= nsqrt; factor++)
            // if factor is prime, then mark multiples of factor as nonprime
            if (isPrime.get(factor))
                // start at factor as anything under factor^2 would have another factor that has already been considered
                for (long j = factor; factor * j <= n; j++)
                    isPrime.set(factor * j, false);
        List<Long> primes = new ArrayList<>();
        isPrime.iterate((index, val) -> {
            if (val && index >= 2)
                primes.add(index);
        });
//        System.out.println(primes);
        return primes;
    }
}
