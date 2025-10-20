package calculator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * 4단계: 입력 유효성 검사
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
    @DisplayName("형식 오류: 커스텀 구분자 헤더에 줄바꿈이 없으면 예외")
    void throwsWhenCustomHeaderMissingNewline() {
        assertThrows(IllegalArgumentException.class, () -> calculator.add("//;1;2;3"));
    }

    @Test
    @DisplayName("형식 오류: 커스텀 구분자가 한 문자가 아니면 예외")
    void throwsWhenCustomDelimiterNotSingleChar() {
        assertThrows(IllegalArgumentException.class, () -> calculator.add("//;;\n1;2;3"));
        assertThrows(IllegalArgumentException.class, () -> calculator.add("//\n1;2;3"));
    }

    @Test
    @DisplayName("빈 토큰이 있으면 예외")
    void throwsWhenEmptyTokenPresent() {
        assertThrows(IllegalArgumentException.class, () -> calculator.add("1,,2"));
        assertThrows(IllegalArgumentException.class, () -> calculator.add("//;\n1;;2"));
        assertThrows(IllegalArgumentException.class, () -> calculator.add("1,:2"));
        assertThrows(IllegalArgumentException.class, () -> calculator.add(" , "));
    }

    @Test
    @DisplayName("숫자가 아닌 값이 있으면 예외")
    void throwsWhenNonNumericToken() {
        assertThrows(IllegalArgumentException.class, () -> calculator.add("1,a"));
        assertThrows(IllegalArgumentException.class, () -> calculator.add("//;\n1;X;3"));
    }

    @Test
    @DisplayName("음수가 포함되면 예외")
    void throwsWhenNegativeNumberPresent() {
        assertThrows(IllegalArgumentException.class, () -> calculator.add("1,-2"));
        assertThrows(IllegalArgumentException.class, () -> calculator.add("//;\n1;-2;3"));
    }
}