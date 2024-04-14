package ru.hse;

import calc.Calc;
import calc.CalcException;
import com.code_intelligence.jazzer.api.FuzzedDataProvider;

import java.util.Random;

public class CalcFuzzingTarget {
    private static final Random random = new Random();

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        Calc calc = new Calc();
        String opnstr = "", s = data.consumeString(random.nextInt(1024));
        try {
            opnstr = calc.opn(s);
        } catch (Exception ignored) {//используем opn как фильтр, оставляем только корректные выражения
            return;
        }

        try {
            calc.calculate(opnstr);
        } catch (CalcException ignored) {
        }
    }
}
