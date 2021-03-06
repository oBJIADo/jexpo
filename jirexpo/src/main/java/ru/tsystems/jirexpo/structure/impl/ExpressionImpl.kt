package ru.tsystems.jirexpo.structure.impl

import ru.tsystems.jirexpo.structure.exception.IllegalExpressionException

open class ExpressionImpl(
        val fieldName: String,
        val equalitySign: EqualitySign,
        val fieldValue: String,
        val logicalSign: LogicalSign? = null,
        val next: ExpressionImpl? = null
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
        var exp: ExpressionImpl? = this
        var size = 0
        while (exp != null) {
            exp = exp.next
            size++
        }

        return size
    }

    override fun toString(): String {
        val expression = StringBuilder()
        var exp: ExpressionImpl? = this
        var ls: LogicalSign?
        while (exp != null) {
            expression.append("[${exp.fieldName}]")
            expression.append(' ')
            expression.append(exp.equalitySign.operationSymbol)
            expression.append(' ')
            expression.append("[${exp.fieldValue}]")

            ls = exp.logicalSign
            if (ls != null) {
                expression.append(' ')
                expression.append(ls.logicalSymbol)
                expression.append(' ')
            }

            exp = exp.next
        }
        return expression.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ExpressionImpl

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

    companion object {
        protected val OPEN_BRACE = '['
        protected val CLOSE_BRACE = ']'
        protected val SPECIAL_SYMBOLS: List<Char> =
                LogicalSign.values().map { it.logicalSymbol }.plus(EqualitySign.values().map { it.operationSymbol })

        fun build(expression: String): ExpressionImpl {
            var openBraceIndex = findNextPartIndex(expression, 0)
            throwBuilderException(CAUSE_OPEN_BRACE, expression[openBraceIndex] != OPEN_BRACE)

            var closeBraceIndex = findCloseBrace(expression, openBraceIndex)
            throwBuilderException(CAUSE_OPEN_BRACE, closeBraceIndex == -1 || closeBraceIndex + 2 >= expression.length)

            val fieldName: String = expression.substring(openBraceIndex + 1, closeBraceIndex)

            var signIndex = findNextPartIndex(expression, closeBraceIndex + 1)

            val eqSign: EqualitySign? = EqualitySign.getBySymbol(expression[signIndex])
            throwBuilderException(CAUSE_EQ_SIGN, eqSign == null)

            openBraceIndex = findNextPartIndex(expression, signIndex + 1)
            closeBraceIndex = findCloseBrace(expression, openBraceIndex)
            throwBuilderException(CAUSE_OPEN_BRACE, closeBraceIndex == -1)

            val fieldValue = expression.substring(openBraceIndex + 1, closeBraceIndex)

            signIndex = findNextPartIndex(expression, closeBraceIndex + 1)
            return if (signIndex == -1) {
                ExpressionImpl(fieldName, eqSign
                        ?: throw mapException(), fieldValue)
            } else {
                val logicalSign: LogicalSign? = LogicalSign.getBySymbol(expression[signIndex])
                throwBuilderException(CAUSE_LOGIC_SIGN, logicalSign == null)
                throwBuilderException("", signIndex + 1 == expression.length)
                ExpressionImpl(
                        fieldName,
                        eqSign ?: throw mapException(),
                        fieldValue,
                        logicalSign,
                        build(expression.substring(signIndex + 1))
                )
            }

        }

        //todo
        private fun findCloseBrace(str: String, startIndex: Int): Int = str.indexOf(CLOSE_BRACE, startIndex)
//            var index = startIndex
//            while (index != -1 && index < str.length){
//                index = str.indexOf(CLOSE_BRACE, index)
//                if(index != -1 && str[index - 1] != '/'){
//                    return index
//                }
//                index++
//            }
//            return -1
//        }

        fun findNextPartIndex(str: String, startIndex: Int): Int {
            var index: Int = startIndex
            while (index < str.length) {
                if (!str[index].isWhitespace()) {
                    return index
                }
                index++
            }
            return -1
        }

        private val NEVER_CALLS = "never-calls"
        private val CAUSE_OPEN_BRACE = "open-brace"
        private val CAUSE_CLOSE_BRACE = "close-brace"
        private val CAUSE_EQ_SIGN = "eq-sign"
        private val CAUSE_LOGIC_SIGN = "logic-sign"
        private fun throwBuilderException(cause: String, isThrown: Boolean = true) {
            if (isThrown) {
                throw mapException(cause)
            }
        }

        private fun mapException(cause: String = NEVER_CALLS): IllegalExpressionException {
            return when (cause) {
                CAUSE_OPEN_BRACE -> IllegalExpressionException("Invalid expression string. Missed open brace '$OPEN_BRACE'.")
                CAUSE_CLOSE_BRACE -> IllegalExpressionException("Invalid expression string. Missed close brace '$CLOSE_BRACE'.")
                CAUSE_EQ_SIGN -> IllegalExpressionException("Invalid expression string. No Equality sign. Should be one of ${EqualitySign.operationSymbolsAsString()}")
                CAUSE_LOGIC_SIGN -> IllegalExpressionException("Invalid expression string. No Logical sign. Should be one of ${LogicalSign.logicalSymbolsAsString()}")
                NEVER_CALLS -> throw IllegalExpressionException("Never calls")
                else -> IllegalExpressionException("Invalid expression string.")
            }
        }

    }
}





