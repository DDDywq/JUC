package org.example;
import java.util.concurrent.*;
/*
* 用户购买商品下单成功后，系统会给用户发送各种消息提示用户『购买成功』
* 比如发送邮件、微信公众号消息、短信等。
* 所有的消息都发送成功后，在后台记录一条消息表示成功。
* 如果使用单线程操作也是可以的，但效率比较低
* 使用多线程的时候会遇到子线程消息还没发送完，主线程可能已经执行完，显示所有消息已发送的情况
* 这时候使用CountDownLatch就可以解决这种问题
* */
public class CountDownLatchDemo {

    public static void main(String[] atgs) throws InterruptedException{
        System.out.println("main thread:Success to place an orde");

        int iCount =3;
        CountDownLatch countDownLatch = new CountDownLatch(iCount);

        Executor executor = Executors.newFixedThreadPool(iCount);
        executor.execute(new MessageTask("email", countDownLatch));
        executor.execute(new MessageTask("wechat", countDownLatch));
        executor.execute(new MessageTask("sms" , countDownLatch));

        //主线程阻塞，等待所有子线程发完消息
        countDownLatch.await();
        //所有子线程已经发完消息，计数器为0，主线程恢复
        System.out.println("main thread: all message has benn sent");
    }

    static  class MessageTask implements Runnable{
        private  String messageName;
        private  CountDownLatch countDownLatch;

        public  MessageTask(String messageName,CountDownLatch countDownLatch){
            this.messageName = messageName;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try{
                //线程发送消息
                System.out.println("Send " + messageName);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            } finally {
                //发送完消息计数器减1
                countDownLatch.countDown();
            }
        }
    }
}
