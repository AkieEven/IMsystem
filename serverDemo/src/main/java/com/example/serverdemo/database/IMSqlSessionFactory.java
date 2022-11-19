package com.example.serverdemo.database;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class IMSqlSessionFactory {
    private static SqlSessionFactory INSTANCE;

    private IMSqlSessionFactory() {
    }

    public static SqlSessionFactory getInstance() {
        if (INSTANCE == null) {
            synchronized (IMSqlSessionFactory.class) {
                if (INSTANCE == null) {
                    try {
                        InputStream inputStream = IMSqlSessionFactory.class.getClassLoader().getResourceAsStream("MyBatisConfig.xml");
                        INSTANCE = new SqlSessionFactoryBuilder().build(inputStream);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return INSTANCE;
    }

    public static SqlSession getSession() {
        return IMSqlSessionFactory.getInstance().openSession();
    }
}
