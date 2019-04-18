package ru.tsystems.jirexpo.components.impl


import org.junit.Assert.assertEquals
import org.junit.Test
import ru.tsystems.jirexpo.structure.impl.EqualitySign
import ru.tsystems.jirexpo.structure.impl.ExpressionImpl
import ru.tsystems.jirexpo.structure.impl.LogicalSign


class ExpressionImplWorkerImplTest {

    private val EQ = EqualitySign.Equal
    private val NEQ = EqualitySign.NotEqual
    private val LIKE = EqualitySign.Like

    private val OR = LogicalSign.Or
    private val NOR = LogicalSign.OrNot
    private val AND = LogicalSign.And
    private val NAND = LogicalSign.AndNot

    @Test
    fun expressionToNativeQuery() {
        val expressionImpl: ExpressionImpl = ExpressionImpl(
                "f1",
                EQ,
                "v1",
                OR,
                ExpressionImpl(
                        "f2",
                        NEQ,
                        "v2",
                        AND,
                        ExpressionImpl(
                                "f3",
                                LIKE,
                                "v3",
                                NOR,
                                ExpressionImpl(
                                        "f4",
                                        EQ,
                                        "v4",
                                        NAND,
                                        ExpressionImpl(
                                                "f5",
                                                EQ,
                                                "v5"
                                        )
                                )
                        )
                )
        )

        val actualQuery = expressionToNativeQuery(expressionImpl)
        val expectedQuery = "f1 = v1 OR f2 <> v2 AND f3 LIKE v3 OR NOT f4 = v4 AND NOT f5 = v5"
        assertEquals(expectedQuery, actualQuery)
    }
}
