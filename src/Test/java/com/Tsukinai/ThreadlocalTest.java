package com.Tsukinai;

import org.junit.jupiter.api.Test;

public class ThreadlocalTest {

    @Test
    public void testThreadLocal() {
        //提供一个ThreadLocal对象
        ThreadLocal tl = new ThreadLocal();

        //开启两个线程
        new Thread(() -> {
            //设置值
            tl.set("mt");
            //获取值
            System.out.println(Thread.currentThread().getName() + tl.get());
            System.out.println(Thread.currentThread().getName() + tl.get());
            System.out.println(Thread.currentThread().getName() + tl.get());
        }, "mt").start();

        new Thread(() -> {
            //设置值
            tl.set("女贼");
            //获取值
            System.out.println(Thread.currentThread().getName() + tl.get());
            System.out.println(Thread.currentThread().getName() + tl.get());
            System.out.println(Thread.currentThread().getName() + tl.get());
        }, "女贼").start();


    }
}
