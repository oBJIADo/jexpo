package ru.tsystems.jirexpo.components.impl

import ru.tsystems.jirexpo.structure.impl.EqualitySign
import ru.tsystems.jirexpo.structure.impl.ExpressionImpl
import ru.tsystems.jirexpo.structure.impl.LogicalSign

fun buildExpression(string: String): ExpressionImpl {
    return ExpressionImpl.build(string)
}

fun expressionToNativeQuery(expressionImpl: ExpressionImpl): String {
    return buildSqlQuery(expressionImpl)
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

private fun buildSqlQuery(expressionImpl: ExpressionImpl): String{

    return ""
}

private fun selectWorkers(parameters: Map<String, String>): String{
    val name = parameters.get()
}

private fun selectFeature(titleValue: String, natureType: String) =
        """
select f.id
    from FEATURE as f
    where f.TITLE like $titleValue
    and f.NATURE = (select n.id from NATURE as n where n.TITLE = $natureType)
        """