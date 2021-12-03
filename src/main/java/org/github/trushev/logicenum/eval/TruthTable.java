package org.github.trushev.logicenum.eval;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.github.trushev.logicenum.formula.Const;
import org.github.trushev.logicenum.formula.Formula;

public interface TruthTable {
    Stream<Formula> formulas();
    Stream<Formula> vars();
    // TODO: mb List<Const> -> Row
    Stream<List<Const>> rows();

    default boolean empty() {
        return rows().findAny().isEmpty();
    }

    default Stream<String> header() {
        var count = (int) formulas().count();
        Stream<String> formulas;
        if (count == 1) {
            formulas = Stream.of("f");
        } else {
            formulas = IntStream.range(0, count).mapToObj(i -> "f" + i);
        }
        return Stream.concat(vars().map(Object::toString), formulas);
    }
}
