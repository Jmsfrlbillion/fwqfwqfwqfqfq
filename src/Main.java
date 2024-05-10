import java.util.Scanner;
import java.util.TreeMap;

 class Calculator {
    private static final TreeMap<Character, Integer> romanToArabicMap = new TreeMap<>();
    private static final TreeMap<Integer, String> arabicToRomanMap = new TreeMap<>();

    static {
        romanToArabicMap.put('I', 1);
        romanToArabicMap.put('V', 5);
        romanToArabicMap.put('X', 10);
        romanToArabicMap.put('L', 50);
        romanToArabicMap.put('C', 100);
        romanToArabicMap.put('D', 500);
        romanToArabicMap.put('M', 1000);

        arabicToRomanMap.put(1000, "M");
        arabicToRomanMap.put(900, "CM");
        arabicToRomanMap.put(500, "D");
        arabicToRomanMap.put(400, "CD");
        arabicToRomanMap.put(100, "C");
        arabicToRomanMap.put(90, "XC");
        arabicToRomanMap.put(50, "L");
        arabicToRomanMap.put(40, "XL");
        arabicToRomanMap.put(10, "X");
        arabicToRomanMap.put(9, "IX");
        arabicToRomanMap.put(5, "V");
        arabicToRomanMap.put(4, "IV");
        arabicToRomanMap.put(1, "I");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Введите операцию например, 5 + 3 или V +V, или 'Q' для выхода: ");
            String input = scanner.nextLine().trim();
;
            // Проверка на выход из программы
            if ("Q".equalsIgnoreCase(input)) {
                System.out.println("Выход из программы.");
                break;
            }

            try {
                String[] parts = input.split("\\s+");
                if (parts.length != 3) {
                    throw new IllegalArgumentException("Неверно нужно ввводить A + B либо римскими V + V");
                }

                String operand1 = parts[0];
                String operation = parts[1];
                String operand2 = parts[2];

                boolean isRoman = operand1.matches("^[IVXLCDM]+$") && operand2.matches("^[IVXLCDM]+$");
                boolean isArabic = operand1.matches("^[0-9]+$") && operand2.matches("^[0-9]+$");

                if (!isRoman && !isArabic) {
                    throw new IllegalArgumentException("Числа должны быть или все римскими, или все арабскими.");
                }

                int num1 = isRoman ? romanToArabic(operand1) : Integer.parseInt(operand1);
                int num2 = isRoman ? romanToArabic(operand2) : Integer.parseInt(operand2);

                if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
                    throw new IllegalArgumentException("Числа должны быть от 1 до 10 включительно.");
                }

                int result = calculate(operation, num1, num2);

                if (isRoman) {
                    if (result < 1) {
                        throw new IllegalArgumentException("Результат операции с римскими числами меньше единицы.");
                    }
                    System.out.println("Результат: " + arabicToRoman(result));
                } else {
                    System.out.println("Результат: " + result);
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

     private static int romanToArabic(String roman) {
         int result = 0;
         int prevValue = 0;
         for (int i = roman.length() - 1; i >= 0; i--) {
             int value = romanToArabicMap.get(roman.charAt(i));
             if (i < roman.length() - 1 && value < romanToArabicMap.get(roman.charAt(i + 1))) {
                 result -= value;
             } else {
                 result += value;
             }
         }
         return result;
     }


     private static String arabicToRoman(int number) {
        if (number <= 0) {
            throw new IllegalArgumentException("Римские числа не могут быть меньше или равны нулю.");
        }
        int l = arabicToRomanMap.floorKey(number);
        if (number == l) {
            return arabicToRomanMap.get(number);
        }
        return arabicToRomanMap.get(l) + arabicToRoman(number - l);
    }

    private static int calculate(String operation, int num1, int num2) {
        return switch (operation) {
            case "+" -> num1 + num2;
            case "-" -> num1 - num2;
            case "*" -> num1 * num2;
            case "/" -> num1 / num2;
            default -> throw new IllegalArgumentException("Недопустимя операция: " + operation);
        };
    }
}
