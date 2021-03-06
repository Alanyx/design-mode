package com.runoob.commandPattern.step3;

import com.runoob.commandPattern.step1.Order;
import com.runoob.commandPattern.step2.Stock;

/**
 * Created by yinxing on 2018/8/21.
 */
public class SellStock implements Order {

    private Stock abcStock;

    public SellStock(Stock abcStock) {
        this.abcStock = abcStock;
    }

    @Override
    public void execute() {
        abcStock.sell();
    }
}
