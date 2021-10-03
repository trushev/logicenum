### Three-valued logic

* true (1)
* false (0)
* unknown (1/2)

### Create a formula
```java
import static org.github.trushev.logicenum.formula.Formula.*;

var a = var("a");
var b = var("b");
var f = and(a, b, or(not(a), isNull(b)));
System.out.println(f);
```
```
(a & b & (!a | ?b))
```

### Print truth table

```java
var table = new CsvTruthTable(new TruthTable(f));
System.out.println(table);
```
```
a, b, f
0.0, 0.0, 0.0
0.5, 0.0, 0.0
1.0, 0.0, 0.0
0.0, 0.5, 0.0
0.5, 0.5, 0.5
1.0, 0.5, 0.5
0.0, 1.0, 0.0
0.5, 1.0, 0.5
1.0, 1.0, 0.0
```
