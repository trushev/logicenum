package org.github.trushev.logicenum.implication;

import org.github.trushev.logicenum.formula.*;

import java.util.*;
import java.util.stream.Stream;

import static java.util.Collections.*;
import static org.github.trushev.logicenum.formula.Formula.*;

public class DnfImplication implements Implication {

    @Override
    public Formula ex(final Formula f, final Formula... attrs) {
        return toDnf(f, Set.of(attrs));
    }

    private Formula toDnf(final Formula f, final Collection<Formula> attrs) {
        final Collection<Formula> operands;
        if (f instanceof And) {
                operands = f.operands().toList();
                final var firstDnf = toDnf(first(operands), attrs);
                final var restDnf = toDnf(and(rest(operands)), attrs);
                final var conjuncts = combinedConjuncts(
                        disjunctions(firstDnf),
                        disjunctions(restDnf),
                        attrs
                );
                return or(conjuncts);
        }
        if (f instanceof Or) {
            operands = f.operands().toList();
            return or(toDnfs(operands, attrs));
        }
        if (f instanceof Not not) {
            final var arg = operand(not);
            if (arg instanceof And) {
                operands = arg.operands().toList();
                return toDnf(or(not(operands)), attrs);
            }
            if (arg instanceof Or) {
                operands = arg.operands().toList();
                return toDnf(and(not(operands)), attrs);
            }
            if (arg instanceof Not) {
                return toDnf(first(arg.operands().toList()), attrs);
            }
            return allowableOrTrue(not, attrs);
        }
        return allowableOrTrue(f, attrs);
    }

    private Collection<Formula> combinedConjuncts(
            final Collection<Formula> leftConjuncts,
            final Collection<Formula> rightConjuncts,
            final Collection<Formula> attrs) {

        final List<Formula> conjuncts = new ArrayList<>();
        for (final Formula left : leftConjuncts) {
            final boolean leftIsAllowable = left.consistsOnly(attrs);
            for (final Formula right : rightConjuncts) {
                final boolean rightIsAllowable = right.consistsOnly(attrs);
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

    private Collection<Formula> toDnfs(final Collection<Formula> fs, final Collection<Formula> attrs) {
        return fs.stream().flatMap(f -> {
            final var dnf = toDnf(f, attrs);
            if (dnf instanceof Or) {
                return dnf.operands();
            } else {
                return Stream.of(dnf);
            }
        }).toList();
    }

    private Formula allowableOrTrue(final Formula f, final Collection<Formula> attrs) {
        if (f.consistsOnly(attrs)) {
            return f;
        } else {
            return Const.True;
        }
    }

    private Collection<Formula> disjunctions(final Formula f) {
        if (f instanceof Or) {
            return f.operands().toList();
        } else {
            return Collections.singletonList(f);
        }
    }
}
