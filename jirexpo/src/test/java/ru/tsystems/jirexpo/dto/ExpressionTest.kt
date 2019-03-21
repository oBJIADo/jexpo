package ru.tsystems.jirexpo.dto

import org.junit.Assert.*
import org.junit.Test

class ExpressionTest {

    @Test
    fun size() {
        val expression: Expression = initExpressionWithSize(5)
        assertEquals(5, expression.size())
    }

    @Test
    fun equalTest() {
        val expression = initExpressionWithSize(4)
        val expression2 = initExpressionWithSize(4)
        assertEquals(expression, expression2)
    }

    @Test
    fun toStringTest(){
        val expression: Expression = initExpressionWithSize(5)
        val expectedString: String = "fieldName0=fieldValue0&" +
                "fieldName1=fieldValue1&" +
                "fieldName2=fieldValue2&" +
                "fieldName3=fieldValue3&" +
                "fieldName4=fieldValue4"
        assertEquals(expectedString, expression.toString())
    }

    @Test
    fun buildSimpleTest(){
        val expectedString: String = "fieldName0=fieldValue0&" +
                "fieldName1=fieldValue1&" +
                "fieldName2=fieldValue2&" +
                "fieldName3=fieldValue3&" +
                "fieldName4=fieldValue4"
        val actual: Expression = Expression.build(expectedString)
        val expected = initExpressionWithSize(5)
        assertEquals(expected, actual)
        assertEquals(expectedString, actual.toString())
    }

    @Test
    fun buildSimpleTest2(){
        val expected = initExpressionWithSize(5)
        val actual: Expression = Expression.build(expected.toString())
        assertEquals(expected, actual)
        assertEquals(expected.toString(), actual.toString())
    }


    @Test
    fun buildWithSpecSymbols(){
        val fieldName = "fieldName/=0//"
        val expectedFieldName = "fieldName=0/"
        val fieldValue = "fieldV/!alue/~0"
        val expectedFieldValue = "fieldV!alue~0"
        val expression = "$fieldName=$fieldValue"
        val exp = Expression.build(expression)
        assertEquals(expectedFieldName, exp.fieldName)
        assertEquals(expectedFieldValue, exp.fieldValue)
        assertEquals(EqualitySign.Equal, exp.equalitySign)
        assertEquals(expression, exp.toString())
    }

    fun initExpressionWithSize(size: Int, ranodomOperations: Boolean = false): Expression {
        var expression: Expression = makeSimpleExpression(size - 1, EqualitySign.Equal)
        for (i in size - 2 downTo 0 step 1) {
            expression = makeSimpleExpression(i, EqualitySign.Equal, LogicalSign.And, expression)
        }

        return expression
    }

    fun makeSimpleExpression(
        num: Int,
        equalitySign: EqualitySign,
        logicalSign: LogicalSign? = null,
        expression: Expression? = null
    ): Expression {
        return if (logicalSign == null || expression == null) {
            Expression("fieldName$num", equalitySign, "fieldValue$num")
        } else {
            Expression("fieldName$num", equalitySign, "fieldValue$num", logicalSign, expression)
        }
    }
}
