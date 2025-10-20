package calculator;

import java.util.Arrays;

/**
 * 문자열 덧셈 계산기
 * 1단계: 입력이 비어있거나 공백만이면 0을 반환한다.
 * 2단계: 기본 구분자 쉼표(,)와 콜론(:)으로 숫자를 분리해 합산한다.
 * 3단계: 커스텀 구분자 형식 "//{구분자}\n{숫자들}"을 지원한다(구분자 한 문자).
 * 유효성 검증(음수, 비숫자, 형식 오류)은 다음 단계에서 강화한다.
 */
public class StringCalculator {

    public int add(String input) {
        // 빈 문자열/공백만 입력이면 0 반환
        if (isBlank(input)) {
            return 0;
        }

        // 커스텀 구분자 헤더가 있는지 확인
        if (isCustomDelimiter(input)) {
            int newlineIdx = input.indexOf('\n');
            char customDelimiter = parseCustomDelimiter(input, newlineIdx); // '//'와 '\n' 사이 한 문자
            String numbersPart = input.substring(newlineIdx + 1);          // 본문(숫자들) 추출
            return sumTokens(splitByCustomDelimiter(numbersPart, customDelimiter));
        }

        // 기본 구분자(쉼표, 콜론)으로 분리 후 합산
        return sumTokens(input.split("[,:]"));
    }

    // null이거나 트림 후 빈 문자열인지 확인
    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    // 입력이 "//{구분자}\n" 형식을 따르는지 확인(구분자 최소 한 문자)
    private boolean isCustomDelimiter(String s) {
        return s.startsWith("//") && s.indexOf('\n') > 2;
    }

    // '//'와 첫 줄바꿈 사이의 첫 문자를 커스텀 구분자로 사용
    private char parseCustomDelimiter(String s, int newlineIdx) {
        String header = s.substring(2, newlineIdx);
        return header.charAt(0);
    }

    // 커스텀 구분자로 숫자 본문을 분리(정규식 문자 클래스용 이스케이프 처리)
    private String[] splitByCustomDelimiter(String numbersPart, char delimiter) {
        String regex = "[" + escapeForCharClass(delimiter) + "]";
        return numbersPart.split(regex);
    }

    // 정규식 문자 클래스([...])에서 특수 문자를 안전하게 이스케이프
    private String escapeForCharClass(char c) {
        return switch (c) {
            case '\\' -> "\\\\";
            case '^' -> "\\^";
            case '-' -> "\\-";
            case ']' -> "\\]";
            default -> String.valueOf(c);
        };
    }

    // 토큰 트림 → 빈 토큰 제외 → 정수 파싱 → 합산 (검증은 다음 단계에서 강화)
    private int sumTokens(String[] tokens) {
        return Arrays.stream(tokens)
                .map(String::trim)
                .filter(token -> !token.isEmpty())
                .mapToInt(Integer::parseInt)
                .sum();
    }
}