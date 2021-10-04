package org.github.trushev.logicenum.implication;

import static java.util.Collections.*;
import static org.github.trushev.logicenum.formula.Formula.*;

import java.util.*;
import java.util.stream.Stream;
import org.github.trushev.logicenum.formula.*;

public final class DnfImplication implements Implication {

    @Override
    public Formula imply(Formula f, Formula... attrs) {
        return toDnf(f, Set.of(attrs));
    }

    private Formula toDnf(Formula f, Collection<Formula> attrs) {
        Collection<Formula> operands;
        return switch (f) {
            case And and -> {
                operands = and.operands().toList();
                var firstDnf = toDnf(first(operands), attrs);
                var restDnf = toDnf(and(rest(operands)), attrs);
                var conjuncts = combinedConjuncts(disjunctions(firstDnf), disjunctions(restDnf), attrs);
                yield or(conjuncts);
            }
            case Or or -> {
                operands = or.operands().toList();
                yield or(toDnfs(operands, attrs));
            }
            case Not not -> {
                var arg = operand(not);
                if (arg instanceof And || arg instanceof Or || arg instanceof Not) {
                    operands = arg.operands().toList();
                    var ff = switch (arg) {
                        case And ignored -> or(not(operands));
                        case Or ignored -> and(not(operands));
                        case Not ignored -> first(operands);
                        default -> throw new AssertionError();
                    };
                    yield toDnf(ff, attrs);
                } else {
                    yield allowableOrTrue(not, attrs);
                }
            }
            default -> allowableOrTrue(f, attrs);
        };
    }

    private Collection<Formula> combinedConjuncts(
        Collection<Formula> leftConjuncts,
        Collection<Formula> rightConjuncts,
        Collection<Formula> attrs
    ) {
        List<Formula> conjuncts = new ArrayList<>();
        for (Formula left : leftConjuncts) {
            boolean leftIsAllowable = left.consistsOnly(attrs);
            for (Formula right : rightConjuncts) {
                boolean rightIsAllowable = right.consistsOnly(attrs);
                if (leftIsAllowable && rightIsAllowable) {
                    conjuncts.add(and(Arrays.asList(left, right)));
                } else if (leftIsAllowable) {
                    conjuncts.add(left);
                } else if (rightIsAllowable) {
                    conjuncts.add(right);
                }
            }
        }
        if (conjuncts.isEmpty()) {
            return singletonList(Const.True);
        }
        return unmodifiableList(conjuncts);
    }

    private Collection<Formula> toDnfs(Collection<Formula> fs, Collection<Formula> attrs) {
        return fs
            .stream()
            .flatMap(
                f -> {
                    var dnf = toDnf(f, attrs);
                    if (dnf instanceof Or) {
                        return dnf.operands();
                    } else {
                        return Stream.of(dnf);
                    }
                }
            )
            .toList();
    }

    private Formula allowableOrTrue(Formula f, Collection<Formula> attrs) {
        if (f.consistsOnly(attrs)) {
            return f;
        } else {
            return Const.True;
        }
    }

    private Collection<Formula> disjunctions(Formula f) {
        if (f instanceof Or) {
            return f.operands().toList();
        } else {
            return Collections.singletonList(f);
        }
    }
}
