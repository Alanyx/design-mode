package com.commandPattern.step5;

/**
 * Created by yinxing on 2018/8/21.
 */

import com.commandPattern.step2.Stock;
import com.commandPattern.step3.BuyStock;
import com.commandPattern.step3.SellStock;
import com.commandPattern.step4.Broker;

/**
 * 步骤 5
 * 使用 Broker 类来接受并执行命令。
 */
public class CommandPatternDemo {
    public static void main(String[] args) {
        Stock abcStock = new Stock();

        BuyStock buyStockOrder = new BuyStock(abcStock);
        SellStock sellStockOrder = new SellStock(abcStock);

        Broker broker = new Broker();
        broker.takeOrder(buyStockOrder);
        broker.takeOrder(sellStockOrder);

        broker.placeOrders();
    }
}
