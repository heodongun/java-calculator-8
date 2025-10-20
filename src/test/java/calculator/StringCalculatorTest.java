package calculator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 1단계: 빈/공백 입력이면 0을 반환하는 동작을 검증한다.
 */
class StringCalculatorTest {

    private final StringCalculator calculator = new StringCalculator();

    @Test
    @DisplayName("null 입력이면 0을 반환한다")
    void returnsZeroWhenInputIsNull() {
        int result = calculator.add(null);
        assertEquals(0, result);
    }

    @Test
    @DisplayName("빈 문자열 입력이면 0을 반환한다")
    void returnsZeroWhenInputIsEmptyString() {
        int result = calculator.add("");
        assertEquals(0, result);
    }

    @Test
    @DisplayName("공백만 있는 문자열 입력이면 0을 반환한다")
    void returnsZeroWhenInputIsWhitespaceOnly() {
        int result = calculator.add("   \t\n  ");
        assertEquals(0, result);
    }

    @Test
    @DisplayName("기타 입력은 1단계에서 아직 미구현이므로 0을 반환한다")
    void returnsZeroForNonBlankInputInStep1() {
        int result = calculator.add("1");
        assertEquals(0, result);
    }
}
