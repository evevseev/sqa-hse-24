# Домашняя работа №6
## Евсеев Евгений — БПИ212

### Запуск

```bash
./gradlew jar && ./jazzer --agent_path=libs/jazzer_standalone.jar --cp=libs/jazzer_standalone.jar --cp=build/libs/FuzzingProject-1.0-SNAPSHOT.jar --target_class=ru.hse.CalcFuzzingTarget > stdout.txt
```

## Отчет

### Ошибка 1 
[**Результат выолнения**](tests_output/01)

```
== Java Exception: java.util.NoSuchElementException
	at java.base/java.util.ArrayDeque.removeFirst(ArrayDeque.java:361)
	at java.base/java.util.ArrayDeque.pop(ArrayDeque.java:592)
	at calc.Calc.calculate(Calc.java:164)
	at ru.hse.CalcFuzzingTarget.fuzzerTestOneInput(CalcFuzzingTarget.java:22)
== libFuzzer crashing input ==
MS: 0 ; base unit: 0000000000000000000000000000000000000000
```

#### Исправление
Перед циклом `while` в `calculate` необходимо добавить првоверку на наличие входных данных. Иначе при попытке достать результат, случается .pop() по пустому стеку, так как туда нам нечего было положить.

```java
        if (!st.hasMoreTokens()) {
            throw new CalcException("Нет данных для расчета");
        }
```

### Ошибка 2
[**Результат выолнения**](tests_output/02)

```
== Java Exception: java.lang.NumberFormatException: For input string: "u"
        at java.base/jdk.internal.math.FloatingDecimal.readJavaFormatString(FloatingDecimal.java:2054)
        at java.base/jdk.internal.math.FloatingDecimal.parseDouble(FloatingDecimal.java:110)
        at java.base/java.lang.Double.parseDouble(Double.java:792)
        at calc.Calc.calculate(Calc.java:157)
        at ru.hse.CalcFuzzingTarget.fuzzerTestOneInput(CalcFuzzingTarget.java:22)
== libFuzzer crashing input ==
MS: 2 InsertByte-Custom-; base unit: adc83b19e793491b1c6ea0fd8b46cd9f32e592fc
0x75,0xa,
```

#### Исправление
`dA = Double.parseDouble(sTmp);` в `calculate` необходимо обернуть в блок `try-catch` для обработки исключения `NumberFormatException`.

```java
        try {
            dA = Double.parseDouble(sTmp);
            stack.push(dA);
        } catch (NumberFormatException e) {
            throw new CalcException("Ошибка преобразования числа " + sTmp);
        }
```
