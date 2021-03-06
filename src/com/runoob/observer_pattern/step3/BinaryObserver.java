package com.runoob.observerPattern.step3;

/**
 * Created by yinxing on 2018/8/10.
 */

import com.runoob.observerPattern.step1.Subject;
import com.runoob.observerPattern.step2.Observer;

/**
 * 步骤 3
 * 创建实体观察者类
 */
public class BinaryObserver extends Observer {

    public BinaryObserver(Subject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        System.out.println("Binary String:" + Integer.toBinaryString(subject.getState()));
    }
}
