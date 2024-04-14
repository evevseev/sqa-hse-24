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

### Ошибка 3
[**Результат выолнения**](tests_output/03)

```
== Java Exception: java.lang.StringIndexOutOfBoundsException: Index 1 out of bounds for length 1
        at java.base/jdk.internal.util.Preconditions$1.apply(Preconditions.java:55)
        at java.base/jdk.internal.util.Preconditions$1.apply(Preconditions.java:52)
        at java.base/jdk.internal.util.Preconditions$4.apply(Preconditions.java:213)
        at java.base/jdk.internal.util.Preconditions$4.apply(Preconditions.java:210)
        at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:98)
        at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
        at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
        at java.base/java.lang.String.checkIndex(String.java:4832)
        at java.base/java.lang.StringLatin1.charAt(StringLatin1.java:46)
        at java.base/java.lang.String.charAt(String.java:1555)
        at calc.Calc.calculate(Calc.java:131)
        at ru.hse.CalcFuzzingTarget.fuzzerTestOneInput(CalcFuzzingTarget.java:22)
== libFuzzer crashing input ==
MS: 6 InsertByte-Custom-ChangeBinInt-Custom-ChangeByte-Custom-; base unit: 55b0bd069e2422eb3c8d9683f6e99768063f543f
0x2d,0x37,0x5e,0x33,
```

#### Исправление
В `switch (sTmp.charAt(1)) {` в `calculate` указан неверный индекс. Должен быть `0`.

```java
        switch (sTmp.charAt(0)) {
```

### Ошибка 4
[**Результат выолнения**](tests_output/04)

```
== Java Exception: java.lang.UnsupportedOperationException: TODO: Не забыть реализовать оператор !
        at calc.Calc.calculate(Calc.java:151)
        at ru.hse.CalcFuzzingTarget.fuzzerTestOneInput(CalcFuzzingTarget.java:22)
== libFuzzer crashing input ==
MS: 6 CrossOver-Custom-ShuffleBytes-Custom-ChangeByte-Custom-; base unit: 3c9d4bf8b1eb4c7d608aee7805d8e0c594a2f26e
0x31,0x2a,0x31,0x32,0x2d,0x32,0x32,0x2d,0x28,0x32,0x21,0x2d,0x28,0x32,0x2d,
```

#### Исправление
Учитывая, то что в подсказке сказано `Поддерживаются цифры, операции +,-,*,/,^,% и приоритеты в виде скобок ( и )`, операция `!` не поддерживается. Необходимо убрать остатки этой операции и попросить разработчика полноценно ее реализовать.

Убрать из функции `calculate` следующие строки:

```java
    case '!':
        throw new UnsupportedOperationException("TODO: Не забыть реализовать оператор !");
```