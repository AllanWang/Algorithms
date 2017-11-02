package regular_expression_matching;

/**
 * This implementation passed leetcode's tests
 * It uses recursion, with slightly better runtime as it will
 * check both ends of the strings before dealing with wildcards
 *
 * I was unaware of the runtime barrier, so this might not have been necessary
 * to pass the tests had I just disabled loging.
 */
class Regex {

    public static void main(String[] args) {
        test();
        test2();
    }

    private static boolean DEBUG = true;

    private static void test() {
        DEBUG = false;
        test("aa", "a", false);
        test("aa", "aa", true);
        test("aaa", "aa", false);
        test("aa", "a*", true);
        test("aaaa", ".*", true);
        test("ab", ".*", true);
        test("aab", "c*a*b", true);
        test("aab", "c*a*aab", true);
        test("ab", ".*c", false);
        test("aaa", "ab*a", false);
        test("a", "ab*", true);
        test("aaaaaaaaaab", "a*a*a*a*a*a*c", false);
    }

    private static void test2() {
        test("ccababcccbbaaabbaa", "ab*.a*.*.*a*a*.*a*", false);
    }

    private static void test(String input, String regex, boolean expected) {
        boolean actual = isMatch(input, regex);
        System.out.println(
                String.format("isMatch(%s, %s) = %b; \t expected %b", input, regex, actual, expected));
        if (actual != expected)
            System.out.println("\tFailed");
    }

    private static boolean isMatch(String input, char regex) {
        return !input.isEmpty() && isMatch(input.charAt(0), regex);
    }

    private static boolean isMatch(char input, char regex) {
        return regex == '.' || input == regex;
    }

    private static boolean isMatch(String input, String regex) {
        return isMatch(input, regex, '\0');
    }

    private static boolean trimTail(String[] data) {
        String input = data[0];
        String regex = data[1];
        while (!input.isEmpty() && !regex.isEmpty()) {
            char r = regex.charAt(regex.length() - 1);
            char i = input.charAt(input.length() - 1);
            if (r == '*') {
                data[0] = input;
                data[1] = regex;
                return true;
            }
            if (isMatch(i, r)) {
                input = input.substring(0, input.length() - 1);
                regex = regex.substring(0, regex.length() - 1);
            } else
                return false;
        }
        //let the main function handle it
        data[0] = input;
        data[1] = regex;
        return true;
    }

    public static boolean isMatch(String input, String regex, char cache) {
        while (!input.isEmpty() && !regex.isEmpty()) {
            debug("input %s, regex %s", input, regex);
            char r = regex.charAt(0);
            if (r == '*') {
                if (cache == '\0')
                    throw new IllegalArgumentException("Regex cannot start with *");
                /*
                 * At this point, we may choose to validate the wildcard,
                 * but given that this increases our lookup exponentially,
                 * let's validate the tail first
                 */
                String[] data = {input, regex};
                if (!trimTail(data)) return false;
                input = data[0];
                regex = data[1];

                if (isMatch(input, cache))
                    if (isMatch(input.substring(1), regex.substring(1), cache)
                            || isMatch(input.substring(1), regex, cache)) return true;
                regex = regex.substring(1);
            } else {
                if (!isMatch(input, r)) {
                    if (regex.length() >= 2 && regex.charAt(1) == '*') {
                        regex = regex.substring(2);
                    } else {
                        debug("Fail by mismatch");
                        return false;
                    }
                } else {
                    cache = r;
                    if (regex.length() < 2 || regex.charAt(1) != '*')
                        input = input.substring(1);
                    regex = regex.substring(1);
                }
            }
        }
        if (regex.isEmpty()) return input.isEmpty();
        if (regex.charAt(0) == '*' && cache != '\0')
            regex = regex.substring(1);
        while (regex.length() >= 2 && regex.charAt(1) == '*')
            regex = regex.substring(2);
        return regex.isEmpty();
    }

    private static void debug(String s, Object... args) {
        if (DEBUG) System.out.println(String.format(s, args));
    }

}
