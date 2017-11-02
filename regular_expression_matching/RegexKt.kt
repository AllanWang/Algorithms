package regular_expression_matching

/**
 * Created by Allan Wang on 2017-10-27.
 */
object RegexKt {

    fun isMatch(input: String, regex: String): Boolean {
        if (regex.isEmpty()) return input.isEmpty()
        val firstMatch = input.isNotEmpty() && regex[0].regexMatches(input[0])
        val isWildcard = regex.length >= 2 && regex[1] == '*'
        if (isWildcard) {
            val data = arrayOf(input, regex)
            if (!trim(data)) return false
            val (i, r) = data
            return isMatch(i, r.substring(2)) || (firstMatch && isMatch(i.substring(1), r))
        }
        return firstMatch && isMatch(input.substring(1), regex.substring(1))
    }

    private fun Char.regexMatches(input: Char) = this == '.' || this == input

    /**
     * Given that wildcards will greatly increase runtime,
     * we shall trim the end to check for matches before we continue
     */
    private fun trim(data: Array<String>): Boolean {
        var (i, r) = data
        while (i.isNotEmpty() && r.isNotEmpty()) {
            if (r.last().regexMatches(i.last())) {
                i = i.substring(0, i.length - 2)
                r = r.substring(0, r.length - 2)
            } else if (i.last() == '*') {
                data[0] = i
                data[1] = r
                return true
            } else {
                return false
            }
        }
        data[0] = i
        data[1] = r
        return true
    }
}