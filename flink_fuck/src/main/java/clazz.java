/**
 * @AUTHOR: Maynard
 * @DATE: 2023/05/05 17:54
 **/

public class clazz {
    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> aClass = Class.forName("clazz.dog");
        System.out.println(aClass);
    }

    class dog{
         {
            System.out.println("dog");
        }
    }
}
