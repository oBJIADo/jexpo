package ru.tsystems.jirexpo.structure.impl

abstract class AbsractValueExpression<ValType>(
        first: ValType,
        second: ValType,
        sign: EqualitySign
) : AbstractExpression<ValType, EqualitySign>(first, second, sign)



