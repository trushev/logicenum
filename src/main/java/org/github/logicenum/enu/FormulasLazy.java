package org.github.logicenum.enu;

import org.github.logicenum.formula.Formula;

import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

final class FormulasLazy implements Formulas {

    private final Map<Integer, Set<Formula>> formulas;

    FormulasLazy() {
        this.formulas = new HashMap<>();
    }

    @Override
    public Stream<Formula> enumeration(final Formula... vars) {
        this.formulas.put(1, Set.of(vars));
        final var iterator = new InnerIterator(this.formulas);
        return Stream.iterate(iterator, Iterator::hasNext, UnaryOperator.identity())
                .map(Iterator::next);
    }

    @Override
    public Iterator<Formula> lazyEnumeration(final Formula... vars) {
        this.formulas.put(1, Set.of(vars));
        return new InnerIterator(this.formulas);
    }

    /**
     * Надо помнить текущий шаг: not, or, and
     * Внутри шага надо помнить текущую длину формулы
     *
     *
     * Для генерации следующих not нужно помнить длину на предыдущем шаге
     * Общий принцип такой -- генерация формулы под целевую длину n
     * Для not:
     * достаточно помнить предыдущую длину и сгенерировать отрицание от формулы
     *
     * Для n = 4:
     * i = 1, 2
     * j1 = 2
     * j2 = 1
     * j = n - i - 1
     *
     * n - 2 + (n - n + 2 - 1) + 1 = n - 2 + n - n + 2 - 1 + 1 = n
     * Для or, and:
     * фиксируем i = 1, 2, ..., n-2
     * вычисляем j(i) = n - i
     *
     */
    private static final class InnerIterator implements Iterator<Formula> {

        private final Map<Integer, Set<Formula>> formulas;
        private final State stateNot;
        private final State stateOr;
        private final State stateAnd;

        private final Set<Formula> notFormulas;
        private final Set<Formula> orFormulas;
        private final Set<Formula> andFormulas;

        private State activeState;
        private Set<Formula> activeFormulas;

        public InnerIterator(final Map<Integer, Set<Formula>> formulas) {
            this.formulas = formulas;
            this.stateNot = new StateNot(this.formulas);
            this.stateOr = new StateOr(this.formulas);
            this.stateAnd = new StateAnd(this.formulas);
            this.notFormulas = new HashSet<>();
            this.orFormulas = new HashSet<>();
            this.andFormulas = new HashSet<>();
            this.activeState = this.stateNot;
            this.activeFormulas = this.notFormulas;
        }

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public Formula next() {
            if (this.activeState.hasNext()) {
                final var f = this.activeState.next();
                this.activeFormulas.add(f);
                return f;
            } else {
                final State nextActiveState;
                final Set<Formula> nextActiveFormulas;
                if (this.activeState instanceof StateNot) {
                    nextActiveState = this.stateOr;
                    nextActiveFormulas = this.orFormulas;
                } else if (this.activeState instanceof StateOr) {
                    nextActiveState = this.stateAnd;
                    nextActiveFormulas = this.andFormulas;
                } else if (this.activeState instanceof StateAnd) {
                    nextActiveState = this.stateNot;
                    nextActiveFormulas = this.notFormulas;
                    this.formulas
                            .get(this.stateNot.length())
                            .addAll(this.notFormulas);
                    this.formulas
                            .get(this.stateOr.length())
                            .addAll(this.orFormulas);
                    this.formulas
                            .get(this.stateAnd.length())
                            .addAll(this.andFormulas);
                } else {
                    throw new IllegalStateException(this.activeState.toString());
                }
                this.activeFormulas.clear();
                this.activeFormulas = nextActiveFormulas;
                nextActiveState.step();
                this.activeState = nextActiveState;
                final var f = this.activeState.next();
                this.activeFormulas.add(f);
                return f;
            }
        }
    }

    private interface State extends Iterator<Formula> {
        void step();
        int length();
    }

    private static abstract class AbstractState implements State {

        private final Map<Integer, Set<Formula>> formulas;
        private int length;

        protected AbstractState(final Map<Integer, Set<Formula>> formulas) {
            this.formulas = formulas;
            this.length = 1;
        }

        protected Map<Integer, Set<Formula>> formulas() {
            return this.formulas;
        }

        @Override
        public int length() {
            return this.length;
        }

        abstract Formula nextElem();

        @Override
        public Formula next() {
            if (hasNext()) {
                return nextElem();
            }
            throw new IllegalStateException();
        }

        @Override
        public void step() {
            this.length++;
        }
    }

    private static final class StateNot extends AbstractState {

        private Iterator<Formula> iterator;

        StateNot(final Map<Integer, Set<Formula>> formulas) {
            super(formulas);
            this.iterator = formulas.get(length()).iterator();
        }

        @Override
        protected Formula nextElem() {
            return this.iterator.next().not();
        }

        @Override
        public boolean hasNext() {
            return this.iterator.hasNext();
        }

        @Override
        public void step() {
            super.step();
            this.iterator = formulas().get(length()).iterator();
        }
    }

    private static abstract class AbstractBiState extends AbstractState {

        private Iterator<Formula> iterator1;
        private Iterator<Formula> iterator2;

        protected AbstractBiState(final Map<Integer, Set<Formula>> formulas) {
            super(formulas);
            this.iterator1 = formulas().get(length()).iterator();
            this.iterator2 = formulas().get(length()).iterator();
        }

        protected Iterator<Formula> iterator1() {
            return this.iterator1;
        }

        protected Iterator<Formula> iterator2() {
            return this.iterator2;
        }

        @Override
        public boolean hasNext() {
            return this.iterator1.hasNext() && this.iterator2.hasNext();
        }

        @Override
        public void step() {
            super.step();
            var formulas = formulas().computeIfAbsent(length(), k -> new HashSet<>());
            this.iterator1 = formulas.iterator();
            this.iterator2 = formulas.iterator();
        }
    }

    private static final class StateOr extends AbstractBiState {

        StateOr(final Map<Integer, Set<Formula>> formulas) {
            super(formulas);
        }

        @Override
        protected Formula nextElem() {
            return iterator1().next().or(iterator2().next());
        }
    }

    private static final class StateAnd extends AbstractBiState {

        StateAnd(final Map<Integer, Set<Formula>> formulas) {
            super(formulas);
        }

        @Override
        protected Formula nextElem() {
            return iterator1().next().and(iterator2().next());
        }
    }
}
