package org.github.trushev.logicenum.eval;

import static java.util.stream.Collectors.joining;

public final class CsvTruthTable {

    private final TruthTable truthTable;
    private final String separator;
    private final boolean asNumber;

    public CsvTruthTable(TruthTable truthTable, String separator, boolean asNumber) {
        this.truthTable = truthTable;
        this.separator = separator;
        this.asNumber = asNumber;
    }

    public CsvTruthTable(TruthTable truthTable) {
        this(truthTable, ", ", true);
    }

    @Override
    public String toString() {
        var sb = new StringBuilder(
            truthTable.vars().stream().map(Object::toString).collect(joining(separator))
        );
        if (!sb.isEmpty()) {
            sb.append(separator);
        }
        var header = sb.append("f").toString();
        var ls = "\n";
        var table =
            truthTable.rows()
                .map(
                    r ->
                        r
                            .stream()
                            .map(
                                v -> {
                                    if (asNumber) return v.asNumber(); else return v;
                                }
                            )
                            .map(Object::toString)
                            .collect(joining(separator))
                )
                .collect(joining(ls));
        return header + ls + table;
    }
}
