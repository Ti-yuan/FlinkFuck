package MyPratice.ThreadDemo;

/**
 * @AUTHOR: Maynard
 * @DATE: 2023/07/26 15:17
 **/

public class MyThreadTest {
    public static void main(String[] args) throws InterruptedException {
        MyThread mt = new MyThread("NewThread");
        mt.start();
        for (int i = 0; i < 10; i++) {
            System.out.println("main线程" + i);
            Thread.sleep(1000);
        }
    }
}
