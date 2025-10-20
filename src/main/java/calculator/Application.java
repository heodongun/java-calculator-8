package calculator;

import camp.nextstep.edu.missionutils.Console;

/**
 * 우테코 프리코스 문자열 덧셈 계산기
 * 1단계: 빈 입력/공백 입력이면 0을 출력한다.
 * - 입력: Console.readLine()
 * - 출력: "결과 : {합}" 형식
 * - 아직 구분자 처리, 커스텀 구분자, 유효성 검사는 구현하지 않음
 */
public class Application {
    public static void main(String[] args) {
        System.out.println("덧셈할 문자열을 입력해 주세요.");
        String input = Console.readLine();

        StringCalculator calculator = new StringCalculator();
        int sum = calculator.add(input);

        System.out.println("결과 : " + sum);
    }
}
