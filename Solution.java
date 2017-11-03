public class Solution {
    public static void main(String[] args) {
        int[] data = {159, 157, 139, 51, 98, 71, 4, 125, 48, 125, 64, 4, 105, 79, 136, 169, 113, 13, 95, 88, 190, 5, 148, 17, 152, 20, 196, 141, 35, 42, 188, 147, 199, 127, 198, 49, 150, 154, 175, 199, 80, 191, 3, 137, 22, 92, 58, 87, 57, 153, 175, 199, 110, 75, 16, 62, 96, 12, 3, 83, 55, 144, 30, 6, 23, 28, 56, 174, 183, 183, 173, 15, 126, 128, 104, 148, 172, 163, 35, 181, 68, 162, 181, 179, 37, 197, 193, 85, 10, 197, 169, 17, 141, 199, 175, 164, 180, 183, 90, 115};
        int result = new Solution().maxArea(data);
        System.out.println(result);
    }

    public int maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int max = 0;
        while (left < right) {
            max = Math.max(max, Math.min(height[left], height[right]) * (right - left));
            if (height[left] < height[right]) left++;
            else right--;
        }
        return max;
    }

    private static void p(String s, int... i) {
        StringBuilder b = new StringBuilder().append(s);
        for (int ii : i)
            b.append(" ").append(ii);
        System.out.println(b.toString());
    }
}
