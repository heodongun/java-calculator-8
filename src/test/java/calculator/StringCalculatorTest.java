package calculator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 2단계: 기본 구분자로 분리 및 합산
 */
class StringCalculatorTest {

    private final StringCalculator calculator = new StringCalculator();

    @Test
    @DisplayName("빈/공백 입력이면 0을 반환한다")
    void returnsZeroWhenBlank() {
        assertEquals(0, calculator.add(""));
        assertEquals(0, calculator.add("   "));
        assertEquals(0, calculator.add(null));
    }

    @Test
    @DisplayName("쉼표로 구분된 숫자를 합산한다")
    void sumsCommaSeparatedNumbers() {
        assertEquals(3, calculator.add("1,2"));
        assertEquals(6, calculator.add("1,2,3"));
    }

    @Test
    @DisplayName("콜론으로 구분된 숫자를 합산한다")
    void sumsColonSeparatedNumbers() {
        assertEquals(6, calculator.add("1:2:3"));
    }

    @Test
    @DisplayName("쉼표와 콜론이 섞여 있어도 합산한다")
    void sumsMixedDelimiters() {
        assertEquals(6, calculator.add("1,2:3"));
    }

    @Test
    @DisplayName("공백이 포함되어도 잘 합산한다")
    void trimsWhitespaceAroundTokens() {
        assertEquals(6, calculator.add(" 1 , 2 : 3 "));
    }
}