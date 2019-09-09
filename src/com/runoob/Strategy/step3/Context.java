package com.runoob.Strategy.step3;

import com.runoob.Strategy.step1.Strategy;

/**
 * @author yinxing
 * @date 2019/9/9
 */

public class Context {

    private Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public int excuteStrategy(int num1, int num2){
        return strategy.doOperation(num1,num2);
    }
}
