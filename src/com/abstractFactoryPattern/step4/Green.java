package com.abstractFactoryPattern.step4;

import com.abstractFactoryPattern.step3.Color;

/**
 * Created by yinxing on 2018/7/31.
 */
public class Green implements Color {
    @Override
    public void fill() {
        System.out.println("Inside Green fill method().");
    }
}
