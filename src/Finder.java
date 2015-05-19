import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Finder {

    public static boolean checkRegExp(String userNameString){
        Pattern p = Pattern.compile("^" +
                "(.*([A-ZА-Я].+){2}.*/|.*)" +
                "(.*(/d){1,2}.*/|.*)" +
                "(.*(/+/d)((.*/d.*){10}).*/|.*)" +
                "(.*[a-zA-Z]{2,}.*@.*[a-zA-Z]{2,}.*/..*((ru)|(com)).*/|.*)");
        Matcher m = p.matcher(userNameString);
        //System.out.println(m.toMatchResult().toString());
        return m.matches();
    }

    public static void main(String[] args){
        System.out.println("Results of checking:");

        System.out.println(checkRegExp("Иван Иванов|27|+7 (999) 0001111|" +
                "example@yandex.ru"));
    }

}
