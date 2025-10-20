package calculator;

import java.util.Arrays;

/**
 * 문자열 덧셈 계산기
 * 1단계: 입력이 비어있거나 공백만이면 0을 반환한다.
 * 2단계: 기본 구분자 쉼표(,)와 콜론(:)으로 숫자를 분리해 합산한다.
 * 3단계: 커스텀 구분자 형식 "//{구분자}\n{숫자들}"을 지원한다(구분자 한 문자).
 * 4단계: 유효성 검증을 추가한다.
 *   - 커스텀 구분자 헤더 형식 오류 시 IllegalArgumentException
 *   - 비숫자 토큰, 빈 토큰(공백만 등) 발견 시 IllegalArgumentException
 *   - 음수 발견 시 IllegalArgumentException
 */
public class StringCalculator {

    public int add(String input) {
        // 빈 문자열/공백만 입력이면 0반환
        if (isBlank(input)) {
            return 0;
        }

        // 커스텀 구분자 헤더가 있는 경우 처리
        if (startsWithCustomHeader(input)) {
            int newlineIdx = input.indexOf('\n');
            validateCustomHeader(input, newlineIdx);           // 헤더 형식 검증
            char customDelimiter = parseCustomDelimiter(input, newlineIdx); // 한 문자 구분자
            String numbersPart = input.substring(newlineIdx + 1);
            return sumValidatedTokens(splitByCustomDelimiter(numbersPart, customDelimiter));
        }

        // 기본 구분자(쉼표, 콜론)으로 분리 후 검증/합산
        return sumValidatedTokens(input.split("[,:]"));
    }

    // null이거나 트림 후 빈 문자열인지 확인
    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    // 입력이 커스텀 헤더 시작 패턴인지 확인
    private boolean startsWithCustomHeader(String s) {
        return s.startsWith("//");
    }

    // 커스텀 헤더 형식 검증: '//' 다음에 최소 1문자 구분자, 그리고 '\n'이 존재해야 한다.
    private void validateCustomHeader(String s, int newlineIdx) {
        // 줄바꿈 문자가 반드시 존재해야 한다.
        if (newlineIdx == -1) {
            throw new IllegalArgumentException("커스텀 구분자 헤더에 줄바꿈(\\n)이 없습니다.");
        }
        // '//' 바로 뒤부터 줄바꿈 직전까지의 길이가 정확히 1이어야 한다(요구사항: 한 문자 구분자).
        int headerLength = newlineIdx - 2;
        if (headerLength != 1) {
            throw new IllegalArgumentException("커스텀 구분자는 한 문자여야 합니다.");
        }
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

    // 토큰별 유효성 검증 후 합산한다.
    private int sumValidatedTokens(String[] tokens) {
        return Arrays.stream(tokens)
                .map(String::trim)
                .peek(this::validateNonEmptyToken)  // 빈 토큰 검증
                .mapToInt(this::parsePositiveInt)   // 숫자 검증 및 음수 검증
                .sum();
    }

    // 공백 제거 후 빈 토큰이면 형식 오류
    private void validateNonEmptyToken(String token) {
        if (token.isEmpty()) {
            throw new IllegalArgumentException("빈 토큰이 포함되어 있습니다.");
        }
    }

    // 정수 파싱 및 음수 검증
    private int parsePositiveInt(String token) {
        // 숫자 형식 검증(정수)
        int value;
        try {
            value = Integer.parseInt(token);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자가 아닌 값이 포함되어 있습니다: " + token);
        }
        // 음수는 허용하지 않음
        if (value < 0) {
            throw new IllegalArgumentException("음수는 허용되지 않습니다: " + value);
        }
        return value;
    }
}