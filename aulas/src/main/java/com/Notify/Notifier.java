package com.Notify;

public class Notifier implements Runnable {
    private Message msg;

    public Notifier(Message msg) {
        this.msg = msg;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println(name + " started");
        try {
            Thread.sleep(1000);
            synchronized (msg) {
                msg.setMsg(name + " Notifier work done");
                // msg.notify()
                msg.notifyAll();
            }
            System.out.println(name + "waiting to get notified at time: " + System.currentTimeMillis());
            msg.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
