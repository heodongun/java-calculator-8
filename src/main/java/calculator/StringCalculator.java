package calculator;

import java.util.Arrays;

/**
 * 문자열 덧셈 계산기
 * 단계 2: 기본 구분자 쉼표(,)와 콜론(:)로 숫자를 분리해 합산한다.
 * - 빈/공백 입력이면 0 반환
 * - "1,2" -> 3, "1,2,3" -> 6, "1,2:3" -> 6
 * - 커스텀 구분자와 유효성 검사는 이후 단계에서 구현
 */
public class StringCalculator {

    public int add(String input) {
        if (isBlank(input)) {
            return 0;
        }

        // 기본 구분자: 쉼표, 콜론
        String[] tokens = input.split("[,:]");

        // 공백 트림 후 빈 토큰은 제외, 양수만 합산
        return Arrays.stream(tokens)
                .map(String::trim)
                .filter(token -> !token.isEmpty())
                .mapToInt(Integer::parseInt)
                .sum();
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
