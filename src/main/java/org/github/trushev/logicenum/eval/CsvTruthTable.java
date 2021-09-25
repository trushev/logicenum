package org.github.trushev.logicenum.eval;

import static java.util.stream.Collectors.joining;

public final class CsvTruthTable {

    private final TruthTable truthTable;
    private final String separator;
    private final boolean asNumber;

    public CsvTruthTable(final TruthTable truthTable, final String separator, final boolean asNumber) {
        this.truthTable = truthTable;
        this.separator = separator;
        this.asNumber = asNumber;
    }

    public CsvTruthTable(final TruthTable truthTable) {
        this(truthTable, ", ", true);
    }

    @Override
    public String toString() {
        final var sb = new StringBuilder(
            this.truthTable.vars().stream().map(Object::toString).collect(joining(this.separator))
        );
        if (!sb.isEmpty()) {
            sb.append(this.separator);
        }
        final var header = sb.append("f").toString();
        final var ls = "\n";
        final var table =
            this.truthTable.rows()
                .map(
                    r ->
                        r
                            .stream()
                            .map(
                                v -> {
                                    if (this.asNumber) return v.asNumber(); else return v;
                                }
                            )
                            .map(Object::toString)
                            .collect(joining(this.separator))
                )
                .collect(joining(ls));
        return header + ls + table;
    }
}
