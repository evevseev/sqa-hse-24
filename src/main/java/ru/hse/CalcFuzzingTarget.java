package ru.hse;

import calc.Calc;
import calc.CalcException;
import com.code_intelligence.jazzer.api.FuzzedDataProvider;

public class CalcFuzzingTarget {
    public static void fuzzerTestOneInput(FuzzedDataProvider data){
        Calc calc = new Calc();
        String opnstr = "", s = ?;
        try {
            opnstr = calc.opn(s);
        }catch(Exception ce){//используем opn как фильтр, оставляем только корректные выражения
        }
        ?
        //считаем, отсеивая лишнее
    }
}
