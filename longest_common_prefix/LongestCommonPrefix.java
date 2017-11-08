package longest_common_prefix;

public class LongestCommonPrefix {

    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) return "";
        String first = strs[0];
        int len = first.length();
        for (int i = 0; i < len; i++) {
            char c = first.charAt(i);
            for (String str : strs)
                if (str.length() <= i || str.charAt(i) != c)
                    return first.substring(0, i);
        }
        return strs[0];
    }

}
