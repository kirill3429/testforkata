import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String result = calc(sc.nextLine());
        System.out.println(result);
        sc.close();
    }

    public static String calc(String input){
        String[] inputElements = input.trim().split((" "));
        verifyInput(inputElements);
        boolean isRoman = inputElements[0].matches("[IXCVL]+");
        int a;
        int b;
        int result = 0;

        if (isRoman){
            a = romeToArabic(inputElements[0]);
            b = romeToArabic(inputElements[2]);
        }
        else {
            a = Integer.parseInt(inputElements[0]);
            b = Integer.parseInt(inputElements[2]);
        }

        if (a < 0 || b < 0 || a > 10 || b > 10 ){
            throw new IllegalArgumentException("Операнды должны быть в диапазоне от 1 до 10 включительно");
        }


        switch (inputElements[1]) {
            case "+" -> result = a + b;
            case "-" -> result = a - b;
            case "/" -> result = a / b;
            case "*" -> result = a * b;
        }

        if (isRoman){
            if (result > 0) {
                return arabicToRome(result);
            } else {
                throw new IllegalArgumentException("В римской системе нет отрицательных чисел и нуля");
            }
        }


        return Integer.toString(result);
    }

    static void verifyInput(String[] inputElements) throws IllegalArgumentException {
        if (inputElements.length != 3) {
            throw new IllegalArgumentException("Неверный формат выражения.");
        }
        if (inputElements[0].matches("[0-9]+") && inputElements[2].matches("[IXCVL]+")){
            throw  new IllegalArgumentException("Выражение содержит разные формы записи чисел");
        }
        if (inputElements[2].matches("[0-9]+") && inputElements[0].matches("[IXCVL]+")){
            throw  new IllegalArgumentException("Выражение содержит разные формы записи чисел");
        }
        if (inputElements[0].matches("[^IXCVL\\d]+") || inputElements[2].matches("[^IXCVL\\d]+")){
            throw  new IllegalArgumentException("Выражение содержит некорректные числа");
        }

        if (inputElements[0].contains(",") || inputElements[2].contains(",")){
            throw new IllegalArgumentException(("Калькулятор работает только с целыми числами"));
        }
        if (inputElements[0].contains(".") || inputElements[2].contains(".")){
            throw new IllegalArgumentException(("Калькулятор работает только с целыми числами"));
        }
        if (inputElements[1].matches("[^-+/*]")){
            throw new IllegalArgumentException("Разрешенные операции: -+/*");
        }
    }



    static int romeToArabic(String input) {
        String inputDigit = input.toUpperCase();

        RomanDigit[] romanDigits = RomanDigit.values();

        int result = 0;
        int i = romanDigits.length - 1;

        while (inputDigit.length() > 0) {
            RomanDigit symbol = romanDigits[i];
            if (inputDigit.startsWith(symbol.name())) {
                result += symbol.getValue();
                inputDigit = inputDigit.substring(symbol.name().length());
            } else {
                i--;
                if (i < 0) throw new IllegalArgumentException(input + " невозможно конвертировать");
            }
        }
        if (inputDigit.length() > 0) {
            throw new IllegalArgumentException(input + " невозможно конвертировать");
        }
        return result;
    }

    static String arabicToRome(int number){
        StringBuilder sb = new StringBuilder();
        RomanDigit[] romanDigits = RomanDigit.values();
        int i = romanDigits.length - 1;

        while (number > 0){
            RomanDigit currentSymbol = romanDigits[i];
            if (currentSymbol.getValue() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i--;
            }
        }

        return sb.toString();
    }
}

enum RomanDigit {
    I(1), IV(4), V(5), IX(9), X(10), XL(40), L(50), XC(90), C(100);
    int value;

    RomanDigit(int value) {
        this.value = value;
    }

    int getValue() {
        return value;
    }

}
