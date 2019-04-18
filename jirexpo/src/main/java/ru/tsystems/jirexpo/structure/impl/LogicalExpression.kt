package ru.tsystems.jirexpo.structure.impl

class LogicalExpression<Expr>(
        first: Expr,
        second: Expr,
        sign: LogicalSign
) : AbstractExpression<Expr, LogicalSign>(first, second, sign)

