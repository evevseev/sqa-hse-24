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

