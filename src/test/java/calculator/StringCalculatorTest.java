package calculator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 3단계: 커스텀 구분자 지원
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
    @DisplayName("쉼표와 콜론 구분자를 사용해 합산한다")
    void sumsDefaultDelimiters() {
        assertEquals(3, calculator.add("1,2"));
        assertEquals(6, calculator.add("1,2,3"));
        assertEquals(6, calculator.add("1,2:3"));
        assertEquals(6, calculator.add(" 1 , 2 : 3 "));
    }

    @Test
    @DisplayName("커스텀 구분자 한 문자로 분리해 합산한다")
    void sumsWithCustomDelimiter() {
        assertEquals(6, calculator.add("//;\n1;2;3"));
    }

    @Test
    @DisplayName("특수 문자(역슬래시, 하이픈)도 구분자로 처리한다")
    void sumsWithSpecialCharCustomDelimiter() {
        assertEquals(6, calculator.add("//-\n1-2-3"));
        assertEquals(6, calculator.add("//\\\n1\\2\\3"));
    }
}