package MyPratice.ThreadDemo;

/**
 * @AUTHOR: Maynard
 * @DATE: 2023/07/26 15:14
 **/

public class MyThread extends Thread{
    public MyThread(String name){
        super(name);
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("新的线程:" + getName() + i);
        }
    }
}
