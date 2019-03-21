package ru.tsystems.jirexpo.dto

open class Expression(
    val fieldName: String,
    val equalitySign: EqualitySign,
    val fieldValue: String,
    val logicalSign: LogicalSign? = null,
    val next: Expression? = null
//) : Iterator<Expression> {
) {
    init {
        if ((logicalSign == null && next != null) || (logicalSign != null && next == null)) {
            throw IllegalExpressionException("Wrong expression sequence.")
        }
        if (fieldName.isEmpty() || fieldValue.isEmpty()) {
            throw IllegalExpressionException()
        }
    }

    fun size(): Int {
        var exp: Expression? = this
        var size = 0
        while (exp != null) {
            exp = exp.next
            size++
        }

        return size
    }

    override fun toString(): String {
        var expression = StringBuilder()
        var exp: Expression? = this
        while (exp != null) {
            expression.append(Expression.hideSyms(exp.fieldName))
            expression.append(exp.equalitySign.operationSymbol)
            expression.append(Expression.hideSyms(exp.fieldValue))
            expression.append(exp.logicalSign?.logicalSymbol ?: "")
            exp = exp.next
        }
        return expression.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Expression

        if (fieldName != other.fieldName) return false
        if (equalitySign != other.equalitySign) return false
        if (fieldValue != other.fieldValue) return false
        if (logicalSign != other.logicalSign) return false
        if (next != other.next) return false

        return true
    }

    override fun hashCode(): Int {
        var result = fieldName.hashCode()
        result = 31 * result + equalitySign.hashCode()
        result = 31 * result + fieldValue.hashCode()
        result = 31 * result + (logicalSign?.hashCode() ?: 0)
        result = 31 * result + (next?.hashCode() ?: 0)
        return result
    }


//    /**
//     * Returns `true` if the iteration has more elements.
//     */
//    override fun hasNext(): Boolean {
//        return next != null
//    }
//
//    /**
//     * Returns the next element in the iteration.
//     */
//    override fun next(): Expression {
//        return next ?: throw IndexOutOfBoundsException("Next element of the Expression not exist.")
//    }

    companion object {
        /**
         * when in the text we have:    /s => / + s (s an example of not specific symbol)
         *                              /! => !
         *                              /= => =
         *                              /~ => ~
         *                              // => /
         *                              /&& => &&
         *                              /!& => !&
         *                              /|| => ||
         *                              /!| => !|
         */
        protected val HIDEN_SYM = '/'
        protected val SPECIAL_SYMBOLS: List<Char> =
            LogicalSign.values().map { it.logicalSymbol }.plus(EqualitySign.values().map { it.operationSymbol })

        //todo: simplyfi
        fun build(expression: String): Expression {
            var firstIndex = 0
            var lastIndex = findWordEnd(expression, firstIndex)

            val fieldName: String = unhideSyms(expression.substring(firstIndex, lastIndex + 1))

            val equalitySign: EqualitySign = EqualitySign.getBySymbol(expression[lastIndex + 1])
                ?: throw IllegalExpressionException("Cannot build expression from the String: $expression. Equality sign at index ${lastIndex + 1} not founded!")

            firstIndex = lastIndex + 2
            lastIndex = findWordEnd(expression, firstIndex)

            if (lastIndex == expression.length) { //todo: remove crutch
                lastIndex--
            }

            val fieldValue: String = unhideSyms(expression.substring(firstIndex, lastIndex + 1))

            val logicalSign: LogicalSign? = if (lastIndex + 1 == expression.length) {
                null
            } else {
                LogicalSign.getBySymbol(expression[lastIndex + 1])
            }

            return if (logicalSign == null) {
                Expression(fieldName, equalitySign, fieldValue, logicalSign, null)
            } else {
                Expression(
                    fieldName,
                    equalitySign,
                    fieldValue,
                    logicalSign,
                    Expression.build(expression.substring(lastIndex + 2))
                )
            }
        }

        protected fun unhideSyms(str: String): String {
            val newStr: StringBuilder = StringBuilder()
            var index: Int = 0
            while (index < str.length) {
                if (index != str.length - 1 && str[index] == HIDEN_SYM && (isSpecialSymbol(str[index + 1]) || str[index + 1] == HIDEN_SYM)) {
                    newStr.append(str[index + 1])
                    index += 2
                } else {
                    newStr.append(str[index])
                    index++
                }
            }
            return newStr.toString()
        }

        protected fun hideSyms(str: String): String {
            val newStr = StringBuilder()
            var index = 0
            while (index < str.length) {
                if (isSpecialSymbol(str[index]) || str[index] == HIDEN_SYM) {
                    newStr.append(HIDEN_SYM)
                    newStr.append(str[index])
                } else {
                    newStr.append(str[index])
                }
                index++
            }
            return newStr.toString()
        }

        protected fun isSpecialSymbol(char: Char) = SPECIAL_SYMBOLS.contains(char)

        protected fun findWordEnd(str: String, startIndex: Int): Int {
            var index = startIndex + 1
            while (index < str.length) {
                if (isOperation(str, index)) {
                    return index - 1
                }
                index++
            }
            return index
        }

        protected fun isOperation(str: String, startIndex: Int): Boolean {
            if (str.isEmpty() || startIndex >= str.length) {
                return false
            }

            var countOfHSym: Int = 0
            var i = startIndex - 1
            while (i >= 0 && str[i] == HIDEN_SYM) {
                countOfHSym++
                i--
            }
            return isSpecialSymbol(str[startIndex]) && countOfHSym % 2 == 0
        }
    }
}

enum class EqualitySign(val operationSymbol: Char, val operationWord: String) {
    NotEqual('!', "NTEQ"),
    Equal('=', "EQ"),
    Like('~', "LIKE");

    companion object {
        fun getBySymbol(symbol: Char): EqualitySign? {
            for (sign in EqualitySign.values()) {
                if (sign.operationSymbol == symbol) {
                    return sign
                }
            }
            return null
        }
    }
}

enum class LogicalSign(val logicalSymbol: Char, val logicalWord: String) {
    And('&', "AND"),
    Or('|', "OR"),
    NotOr('+', "NOR"),
    NotAnd('*', "NAND");

    companion object {
        fun getBySymbol(symbol: Char): LogicalSign? {
            for (sign in LogicalSign.values()) {
                if (symbol == sign.logicalSymbol) {
                    return sign
                }
            }
            return null
        }
    }
}

class IllegalExpressionException(message: String) : Exception("Cannot build expression! $message") {
    constructor() : this("Unknown reason.")
}