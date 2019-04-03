package ru.tsystems.jirexpo.components.impl

import ru.tsystems.jirexpo.structure.EqualitySign
import ru.tsystems.jirexpo.structure.Expression
import ru.tsystems.jirexpo.structure.LogicalSign

fun buildExpression(string: String): Expression {
    return Expression.build(string)
}

fun expressionToNativeQuery(expression: Expression): String {
    val nativeQuery = StringBuilder()
    nativeQuery.append(expression.fieldName)
    nativeQuery.append(' ')
    nativeQuery.append(getSqlFromEqSign(expression.equalitySign))
    nativeQuery.append(' ')
    nativeQuery.append(expression.fieldValue)
    if (expression.logicalSign != null && expression.next != null) {
        nativeQuery.append(' ')
        nativeQuery.append(getSqlFromLgSign(expression.logicalSign))
        nativeQuery.append(' ')
        nativeQuery.append(expressionToNativeQuery(expression.next))
    }
    return nativeQuery.toString()
}

private fun getSqlFromEqSign(equalitySign: EqualitySign) =
        when (equalitySign) {
            EqualitySign.Equal -> "="
            EqualitySign.NotEqual -> "<>"
            EqualitySign.Like -> "LIKE"
        }

private fun getSqlFromLgSign(logicalSign: LogicalSign) =
        when (logicalSign) {
            LogicalSign.And -> "AND"
            LogicalSign.Or -> "OR"
            LogicalSign.OrNot -> "OR NOT"
            LogicalSign.AndNot -> "AND NOT"
        }
