package ru.tsystems.jirexpo.dto

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.tsystems.jirexpo.structure.impl.EqualitySign
import ru.tsystems.jirexpo.structure.impl.ExpressionImpl
import ru.tsystems.jirexpo.structure.impl.IllegalExpressionException
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
        val expectedString: String = "[fieldName0] = [fieldValue0] & " +
                "[fieldName1] = [fieldValue1] & " +
                "[fieldName2] = [fieldValue2] & " +
                "[fieldName3] = [fieldValue3] & " +
                "[fieldName4] = [fieldValue4]"
        assertEquals(expectedString, expressionImpl.toString())
    }

    @Test
    fun buildSimpleTest() {
        val expectedString: String = "[fieldName0] = [fieldValue0] & " +
                "[fieldName1] = [fieldValue1] & " +
                "[fieldName2] = [fieldValue2] & " +
                "[fieldName3] = [fieldValue3] & " +
                "[fieldName4] = [fieldValue4]"
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
        val fieldName = "  fieldN ame/\n=0//  "
        val fieldValue = "  fi eldV/!alue/~0  "
        val expression = "   [$fieldName]   \n=\n    [$fieldValue]    "
        val exp = ExpressionImpl.build(expression)
        assertEquals(fieldName, exp.fieldName)
        assertEquals(fieldValue, exp.fieldValue)
        assertEquals(EqualitySign.Equal, exp.equalitySign)
        assertEquals("[$fieldName] = [$fieldValue]", exp.toString())
    }

    @Test(expected = IllegalExpressionException::class)
    fun buildInvalidExpression() {
        val exp = "[aa] [bb]"
        ExpressionImpl.build(exp)
    }

    @Test(expected = IllegalExpressionException::class)
    fun buildInvalidExpression1() {
        val exp = "[aa]  &a [bb]"
        ExpressionImpl.build(exp)
    }

    @Test(expected = IllegalExpressionException::class)
    fun buildInvalidExpression2() {
        val exp = "[aa] & \n"
        ExpressionImpl.build(exp)
    }

    @Test(expected = IllegalExpressionException::class)
    fun buildInvalidExpression3() {
        val exp = "[aa] & [bb] &"
        ExpressionImpl.build(exp)
    }

    @Test(expected = IllegalExpressionException::class)
    fun buildInvalidExpression4() {
        val exp = "[aa] & [bb] *"
        ExpressionImpl.build(exp)
    }

    @Test(expected = IllegalExpressionException::class)
    fun buildInvalidExpression5() {
        val exp = "[aa] & [[bb]]"
        ExpressionImpl.build(exp)
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
            ExpressionImpl("fieldName$num", equalitySign, "fieldValue$num", logicalSign, expressionImpl)
        }
    }
}
