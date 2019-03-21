package ru.tsystems.jirexpo.components.api

import java.beans.Expression

interface ExpressionWorker{
    fun buildExpression(string: String): Expression
    fun expressionToNativeQuery(expression: Expression): String
}