package com.linjingc.authenticationqq.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据源
 */
public class InMemoryQQDatabase {

    public static Map<String, QQAccount> database;

    static {
        database = new HashMap<>();
        database.put("250577914", QQAccount.builder().qq("250577914").nickName("鱼非渔").level("54").build());
        database.put("920129126", QQAccount.builder().qq("920129126").nickName("下一秒升华").level("31").build());

        QQAccount qqAccount1 = database.get("250577914");
        qqAccount1.setFans(new ArrayList<>());
        for (int i = 0; i < 5; i++) {
            qqAccount1.getFans().add(QQAccount.builder().qq("1000000" + i).nickName("fan" + i).level(i + "").build());
        }

        QQAccount qqAccount2 = database.get("920129126");
        qqAccount2.setFans(new ArrayList<>());
        for (int i = 0; i < 3; i++) {
            qqAccount2.getFans().add(QQAccount.builder().qq("2000000" + i).nickName("fan" + i).level(i + "").build());
        }
    }

}
