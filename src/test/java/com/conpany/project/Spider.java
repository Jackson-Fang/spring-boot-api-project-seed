package com.conpany.project;

import com.alibaba.fastjson.JSON;
import com.company.project.dto.DealDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: chenyin
 * @date: 2018/12/15 下午12:27
 */
public class Spider extends Tester {
    private static final String FETCH_URL = "http://odds.500.com/fenxi/touzhu-731062.shtml";
    /**
     * 超过200W 提醒
     */
    private static final Double ALARM_DEAL_MONEY = 1000000D;

    public static void main(String[] args) {
        try {
            Document document = Jsoup.connect(FETCH_URL).get();
            Elements container = document.getElementsByClass("pub_table pl_table_data  bif-yab");

            Document containerDoc = Jsoup.parse(container.toString());
            Elements tableContainer = containerDoc.select("table");
            if (tableContainer == null || tableContainer.size() < 2) {
                return;
            }
            Element targetTable = tableContainer.get(1);
            Elements trs = targetTable.select("tr");
            if (trs == null || trs.size() == 0) {
                return;
            }
            List<DealDto> alarmList = new ArrayList<>();
            for(int i=0;i<trs.size();i++) {
                Boolean needAlarm = false;

                Element trElement = trs.get(i);
                Elements tds = trElement.select("td");
                if (tds == null || tds.size() == 0) {
                    continue;
                }
                DealDto dealDto = new DealDto();
                for (int j = 0; j < tds.size(); j++) {
                    Element td = tds.get(j);
                    String text = td.text();
                    switch (j) {
                        case 0:
                            dealDto.setZonghe(text);
                            break;
                        case 1:
                            dealDto.setAttribute(text);
                            break;
                        case 2:
                            Double dealMoney = Double.valueOf(text);
                            if (dealMoney > ALARM_DEAL_MONEY) {
                                needAlarm = true;
                            }
                            dealDto.setDealMoney(dealMoney);
                            break;
                        case 3:
                            dealDto.setDealTime(text);
                            break;
                        default:
                            dealDto.setDealPercent(text);
                            break;
                    }
                }
                if (needAlarm) {
                    alarmList.add(dealDto);
                }
                System.out.println(JSON.toJSONString(dealDto));
            }
            if (!alarmList.isEmpty()) {
                for (DealDto dealDto : alarmList) {
                    StringBuilder message = new StringBuilder();
                    message.append("有机会来啦!\n");
                    message.append("成交量:");
                    message.append(dealDto.getDealMoney());
                    message.append(",");
                    message.append("成交时间:");
                    message.append(dealDto.getDealTime());
                    System.out.println(message.toString());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
