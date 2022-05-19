import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws InputException {
        Scanner in = new Scanner(System.in);
        System.out.println("Ввод данных:");
        System.out.println("\"" + calc(in.nextLine()) + "\"");
    }

    public static String calc(String inputString) throws InputException {
        if (inputString.isEmpty()) {
            throw new InputException("Ошибка ввода! Пустая строка.");
        }
        String inString = inputString;
        String left = null, right = null, oper = null;
        int rNum = 0, i = 0;
        char charOp = 0;

        Pattern patt = Pattern.compile("\"[^\"]{1,10}\"");
        Matcher matc = patt.matcher(inString);

        while (matc.find()){
            i = ++i;
            if (i == 1) {
                left = matc.group();
                inString = inString.replaceFirst(left,"");
                left = left.replaceAll("\"","");}
            else if (i == 2) {
                right = matc.group();
                inString = inString.replace(right,"");
                right = right.replaceAll("\"","");}
        }

        Pattern pattOp = Pattern.compile("^\s+[*+/-]\s+");
        Matcher matcOp = pattOp.matcher(inString);

        while (matcOp.find()){
            oper = matcOp.group();
            inString = inString.replace(oper,"");
            oper = oper.replaceAll(" ","");
            charOp = oper.charAt(0);
        }

        if (inString != "") {
            try {
                rNum = Integer.parseInt(inString);
                inString = inString.replace(inString,"");
            }
            catch (NumberFormatException nfe) {
                throw new InputException("Ошибка ввода! Неверный формат ввода данных.");
            }
        }
        if (rNum >= 1 & rNum > 10) {
            throw new InputException("Ошибка ввода! Неверный формат ввода данных.");}
        if (((charOp =='+' | charOp =='-') & rNum != 0) | ((charOp =='*' | charOp =='/') & rNum == 0)) {
            throw new InputException("Ошибка ввода! Неверный формат ввода данных.");
        }

        switch (charOp) {
            case '+':
                inputString = left + right;
                break;
            case '-':
                inputString = left.replaceFirst(right,"");
                break;
            case '*':
                for (int n = 0; n < rNum; n++) {
                    inString = inString + left;
                    inputString = inString;
                }
                break;
            case '/':
                int n = left.length() / rNum;
                inputString = left.substring(0, n);
                break;
        }
        if (inputString.length() > 40){
            inputString = inputString.substring(0, 40) + "...";
        }

        return inputString;
    }
}
class InputException extends Exception {
    public InputException(String message) {super(message);}
}