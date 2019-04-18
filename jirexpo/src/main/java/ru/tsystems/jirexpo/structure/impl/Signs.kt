package ru.tsystems.jirexpo.structure.impl

enum class EqualitySign(val operationSymbol: Char, val operationWord: String) {
    NotEqual('!', "NTEQ"),
    Equal('=', "EQ"),
    Like('~', "LIKE");

    companion object {
        fun operationSymbolsAsString(): String = EqualitySign.values().joinToString { it.operationSymbol.toString() }

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
    OrNot('+', "NOR"),
    AndNot('*', "NAND");

    companion object {
        fun logicalSymbolsAsString(): String = LogicalSign.values().joinToString { it.logicalSymbol.toString() }

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