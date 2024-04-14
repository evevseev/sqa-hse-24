import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Crash_9f8d69cccaff49f764099b6b28e27ee0fd903a40 {
    static final String base64Bytes = String.join("", "rO0ABXNyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAABdwQAAAABdAAELTdeM3g=");

    public static void main(String[] args) throws Throwable {
        Crash_9f8d69cccaff49f764099b6b28e27ee0fd903a40.class.getClassLoader().setDefaultAssertionStatus(true);
        try {
            Method fuzzerInitialize = ru.hse.CalcFuzzingTarget.class.getMethod("fuzzerInitialize");
            fuzzerInitialize.invoke(null);
        } catch (NoSuchMethodException ignored) {
            try {
                Method fuzzerInitialize = ru.hse.CalcFuzzingTarget.class.getMethod("fuzzerInitialize", String[].class);
                fuzzerInitialize.invoke(null, (Object) args);
            } catch (NoSuchMethodException ignored1) {
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                System.exit(1);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            System.exit(1);
        }
        com.code_intelligence.jazzer.api.CannedFuzzedDataProvider input = new com.code_intelligence.jazzer.api.CannedFuzzedDataProvider(base64Bytes);
        ru.hse.CalcFuzzingTarget.fuzzerTestOneInput(input);
    }
}