package org.github.logicenum.dnf;

import org.github.logicenum.formula.*;

import java.util.*;

import static java.util.Collections.*;
import static org.github.logicenum.formula.Formula.*;

public class DnfAlgorithm {

    public Formula toDnf(final Formula f, final Formula... attrs) {
        return toDnf(f, Set.of(attrs));
    }

    public Formula toDnf(final Formula f, final Collection<Formula> attrs) {
        final Collection<Formula> operands;
        if (f instanceof And) {
                operands = f.operands();
                final var firstDnf = toDnf(first(operands), attrs);
                final var restDnf = toDnf(and(rest(operands)), attrs);
                final var conjuncts = combinedConjuncts(disjunctions(firstDnf), disjunctions(restDnf), attrs);
                return or(conjuncts);
        }
        if (f instanceof Or) {
            operands = f.operands();
            return or(toDnfs(operands, attrs));
        }
        if (f instanceof Not not) {
            final var arg = operands(not);
            if (arg instanceof And) {
                operands = arg.operands();
                return toDnf(or(not(operands)), attrs);
            }
            if (arg instanceof Or) {
                operands = arg.operands();
                return toDnf(and(not(operands)), attrs);
            }
            if (arg instanceof Not) {
                return toDnf(first(operands(arg)), attrs);
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
            final boolean leftIsAllowable = attrs.contains(left);
            for (final Formula right : rightConjuncts) {
                final boolean rightIsAllowable = attrs.contains(right);
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

    private Collection<Formula> toDnfs(final Collection<Formula> nodes, final Collection<Formula> attrs) {
        final List<Formula> list = new ArrayList<>();
        for (final Formula node : nodes) {
            final Formula dnf = toDnf(node, attrs);
            if (dnf instanceof Or) {
                list.addAll(operands(dnf));
            } else {
                list.add(dnf);
            }
        }
        return unmodifiableList(list);
    }

    private Formula allowableOrTrue(final Formula f, final Collection<Formula> attrs) {
        if (attrs.contains(f)) {
            return f;
        } else {
            return Const.True;
        }
    }
}
