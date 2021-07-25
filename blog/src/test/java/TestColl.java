import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class TestColl {
    @Test
    public void test1() {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("1", 1);
        map.put("2", 2);
        map.put("3", 3);
        map.put("4", 4);
        map.put("5", 5);
        System.out.println(map);
    }
    public static int getInt() {
        int a = 10;
        try {
            System.out.println(a / 0);
            a = 20;
//            return a;
        } catch (ArithmeticException e) {
            a = 30;
            return a;
            /*
             * return a 在程序执行到这一步的时候，这里不是return a 而是 return 30；这个返回路径就形成了
             * 但是呢，它发现后面还有finally，所以继续执行finally的内容，a=40
             * 再次回到以前的路径,继续走return 30，形成返回路径之后，这里的a就不是a变量了，而是常量30
             */
        } finally {
            a = 40;
            return a;
        }
    }
    @Test
    public void test2() {
        System.out.println(getInt());
    }
}
