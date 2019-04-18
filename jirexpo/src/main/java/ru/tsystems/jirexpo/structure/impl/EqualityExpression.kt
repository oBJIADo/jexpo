package ru.tsystems.jirexpo.structure.impl

class EqualityExpression(
        name: String,
        value: String,
        sign: EqualitySign
) : AbsractValueExpression<String>(name, value, sign)