package ru.tsystems.jirexpo.structure.api

interface Expression<Sense, Sign>{
    public fun earnFirst(): Sense
    public fun earnSecond(): Sense
    public fun earnSign(): Sign
}

/*

    [a]=[a]
    [a]=[a] & [b]=[b]
    [a]=[a] & [b]=[b] | [c]=[c]
    [c]=[c] | [a]=[a] & [b]=[b]

 */

/*

    ([a]=[a] & [b]=[b]) & ([a]=[a] & [b]=[b])
                        |
                      LogExp
                        /\
          LogExp                LogExp
            /\
     EqExp       EqExp
      /\           /\
    Val Val      Val Val
 */