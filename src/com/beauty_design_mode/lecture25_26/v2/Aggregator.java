package com.beauty_design_mode.lecture25_26.v2;

import com.beauty_design_mode.lecture25_26.v2.model.RequestInfo;
import com.beauty_design_mode.lecture25_26.v2.model.RequestStat;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 聚合器
 * <p>
 * review 分析:
 * 只有一个静态函数,负责各种统计数据的计算。当需要扩展新的统计功能的时候，需要修改 aggregate() 函数代码，
 * 并且一旦越来越多的统计功能添加进来之后，这个函数的代码量会持续增加，可读性、可维护性就变差了。
 * 所以，这个类的设计可能存在职责不够单一、不易扩展等问题，需要在之后的版本中，对其结构做优化。
 *
 * @author Alan Yin
 * @date 2020/10/14
 */

public class Aggregator {

    public static RequestStat aggregate(List<RequestInfo> requestInfos, long durationInMills) {
        double maxRespTime = Double.MIN_EXPONENT;
        double minRespTime = Double.MAX_VALUE;
        double avgRespTime = -1;
        double p999RespTime = -1;
        double p99RespTime = -1;
        double sumRespTime = 0;
        long count = 0;

        for (RequestInfo requestInfo : requestInfos) {
            ++count;
            double respTime = requestInfo.getResponseTime();
            if (maxRespTime < respTime) {
                maxRespTime = respTime;
            }
            if (minRespTime > respTime) {
                minRespTime = respTime;
            }
            sumRespTime += respTime;
        }
        if (count != 0) {
            avgRespTime = sumRespTime / count;
        }

        long tps = (count / (durationInMills * 1000));
        Collections.sort(requestInfos, new Comparator<RequestInfo>() {
            @Override
            public int compare(RequestInfo o1, RequestInfo o2) {
                double diff = o1.getResponseTime() - o2.getResponseTime();
                if (diff > 0.0) {
                    return 1;
                } else if (diff < 0.0) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        int idx999 = (int) (count * 0.999);
        int idx99 = (int) (count * 0.99);
        if (count != 0) {
            p999RespTime = requestInfos.get(idx999).getResponseTime();
            p99RespTime = requestInfos.get(idx99).getResponseTime();
        }

        RequestStat requestStat = new RequestStat();
        requestStat.setMaxResponseTime(maxRespTime);
        requestStat.setMinResponseTime(minRespTime);
        requestStat.setAvgResponseTime(avgRespTime);
        requestStat.setP999ResponseTime(p999RespTime);
        requestStat.setP99ResponseTime(p99RespTime);
        requestStat.setCount(count);
        requestStat.setTps(tps);
        return requestStat;
    }

}
