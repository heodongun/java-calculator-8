package calculator;

/**
 * 1단계: 입력 문자열이 null, 빈 문자열("") 또는 공백만이면 0을 반환한다.
 * 앞으로의 단계에서는 구분자 처리와 유효성 검사를 이 클래스에 확장할 수 있다.
 */
public class StringCalculator {

    /**
     * 요구사항 1: 빈/공백 입력이면 0 반환.
     * 현재 단계에서는 다른 입력에 대한 계산은 아직 미구현이므로 0만 반환한다.
     */
    public int add(String input) {
        if (isBlank(input)) {
            return 0;
        }
        // 1단계에서는 다른 케이스 미구현. 다음 단계에서 구분자/합산 로직 추가 예정.
        return 0;
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
