package ru.tsystems.jirexpo.structure

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.tsystems.jirexpo.structure.impl.EqualitySign
import ru.tsystems.jirexpo.structure.impl.ExpressionImpl
import ru.tsystems.jirexpo.structure.impl.LogicalSign

class ExpressionImplTest {

    @Test
    fun size() {
        val expressionImpl: ExpressionImpl = initExpressionWithSize(5)
        assertEquals(5, expressionImpl.size())
    }

    @Test
    fun equalTest() {
        val expression = initExpressionWithSize(4)
        val expression2 = initExpressionWithSize(4)
        assertEquals(expression, expression2)
    }

    @Test
    fun toStringTest() {
        val expressionImpl: ExpressionImpl = initExpressionWithSize(5)
        val expectedString: String = "fieldName0=fieldValue0&" +
                "fieldName1=fieldValue1&" +
                "fieldName2=fieldValue2&" +
                "fieldName3=fieldValue3&" +
                "fieldName4=fieldValue4"
        assertEquals(expectedString, expressionImpl.toString())
    }

    @Test
    fun buildSimpleTest() {
        val expectedString: String = "fieldName0=fieldValue0&" +
                "fieldName1=fieldValue1&" +
                "fieldName2=fieldValue2&" +
                "fieldName3=fieldValue3&" +
                "fieldName4=fieldValue4"
        val actual: ExpressionImpl = ExpressionImpl.build(expectedString)
        val expected = initExpressionWithSize(5)
        assertEquals(expected, actual)
        assertEquals(expectedString, actual.toString())
    }

    @Test
    fun buildSimpleTest2() {
        val expected = initExpressionWithSize(5)
        val actual: ExpressionImpl = ExpressionImpl.build(expected.toString())
        assertEquals(expected, actual)
        assertEquals(expected.toString(), actual.toString())
    }


    @Test
    fun buildWithSpecSymbols() {
        val fieldName = "fieldName/=0//"
        val expectedFieldName = "fieldName=0/"
        val fieldValue = "fieldV/!alue/~0"
        val expectedFieldValue = "fieldV!alue~0"
        val expression = "$fieldName=$fieldValue"
        val exp = ExpressionImpl.build(expression)
        assertEquals(expectedFieldName, exp.fieldName)
        assertEquals(expectedFieldValue, exp.fieldValue)
        assertEquals(EqualitySign.Equal, exp.equalitySign)
        assertEquals(expression, exp.toString())
    }

    @Test
    fun buildWithSpecSymbols2() {
        val fieldName = "/s/d/f/g//////"
        val expectedFieldName = "/s/d/f/g///"
        val fieldValue = "/a/v/b/z/a/w/e/r/%/#/@/-/_///="
        val expectedFieldValue = "/a/v/b/z/a/w/e/r/%/#/@/-/_/="
        val expression = "$fieldName=$fieldValue"
        val exp = ExpressionImpl.build(expression)
        assertEquals(expectedFieldName, exp.fieldName)
        assertEquals(expectedFieldValue, exp.fieldValue)
        assertEquals(EqualitySign.Equal, exp.equalitySign)
        val trueFieldName = "//s//d//f//g//////"
        val trueFieldValue = "//a//v//b//z//a//w//e//r//%//#//@//-//_///="
        val expectedToString: String = ""
        assertEquals("$trueFieldName=$trueFieldValue", exp.toString())
        assertEquals(expectedFieldValue, ExpressionImpl.build(exp.toString()).fieldValue)
        assertEquals(expectedFieldName, ExpressionImpl.build(exp.toString()).fieldName)
    }

    fun initExpressionWithSize(size: Int, ranodomOperations: Boolean = false): ExpressionImpl {
        var expressionImpl: ExpressionImpl = makeSimpleExpression(size - 1, EqualitySign.Equal)
        for (i in size - 2 downTo 0 step 1) {
            expressionImpl = makeSimpleExpression(i, EqualitySign.Equal, LogicalSign.And, expressionImpl)
        }

        return expressionImpl
    }

    fun makeSimpleExpression(
            num: Int,
            equalitySign: EqualitySign,
            logicalSign: LogicalSign? = null,
            expressionImpl: ExpressionImpl? = null
    ): ExpressionImpl {
        return if (logicalSign == null || expressionImpl == null) {
            ExpressionImpl("fieldName$num", equalitySign, "fieldValue$num")
        } else {
            ExpressionImpl(
                    "fieldName$num",
                    equalitySign,
                    "fieldValue$num",
                    logicalSign,
                    expressionImpl
            )
        }
    }
}
