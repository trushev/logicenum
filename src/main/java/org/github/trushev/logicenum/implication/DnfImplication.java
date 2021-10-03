package org.github.trushev.logicenum.implication;

import static java.util.Collections.*;
import static org.github.trushev.logicenum.formula.Formula.*;

import java.util.*;
import java.util.stream.Stream;
import org.github.trushev.logicenum.formula.*;

public class DnfImplication implements Implication {

    @Override
    public Formula ex(final Formula f, final Formula... attrs) {
        return toDnf(f, Set.of(attrs));
    }

    private Formula toDnf(final Formula f, final Collection<Formula> attrs) {
        final Collection<Formula> operands;
        return switch (f) {
            case final And ignored -> {
                operands = f.operands().toList();
                final var firstDnf = toDnf(first(operands), attrs);
                final var restDnf = toDnf(and(rest(operands)), attrs);
                final var conjuncts = combinedConjuncts(disjunctions(firstDnf), disjunctions(restDnf), attrs);
                yield or(conjuncts);
            }
            case final Or ignored -> {
                operands = f.operands().toList();
                yield or(toDnfs(operands, attrs));
            }
            case final Not not -> {
                final var arg = operand(not);
                operands = arg.operands().toList();
                yield switch (arg) {
                    case final And ignored -> toDnf(or(not(operands)), attrs);
                    case final Or ignored -> toDnf(and(not(operands)), attrs);
                    case final Not ignored -> toDnf(first(operands), attrs);
                    default -> allowableOrTrue(not, attrs);
                };
            }
            default -> allowableOrTrue(f, attrs);
        };
    }

    private Collection<Formula> combinedConjuncts(
        final Collection<Formula> leftConjuncts,
        final Collection<Formula> rightConjuncts,
        final Collection<Formula> attrs
    ) {
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
        return fs
            .stream()
            .flatMap(
                f -> {
                    final var dnf = toDnf(f, attrs);
                    if (dnf instanceof Or) {
                        return dnf.operands();
                    } else {
                        return Stream.of(dnf);
                    }
                }
            )
            .toList();
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
