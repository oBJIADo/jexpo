package ru.tsystems.jirexpo.structure.impl

import ru.tsystems.jirexpo.structure.api.Expression

abstract class AbstractExpression<Sense, Sign>(
        protected var first: Sense,
        protected var second: Sense,
        protected var sign: Sign
) : Expression<Sense, Sign> {

    override fun earnFirst(): Sense = first
    override fun earnSecond(): Sense = second
    override fun earnSign(): Sign = sign
    protected fun fillFirst(first: Sense) {
        this.first = first
    }

    protected fun fillSecond(second: Sense) {
        this.second = second
    }

    protected fun fillSign(sign: Sign) {
        this.sign = sign
    }
}
