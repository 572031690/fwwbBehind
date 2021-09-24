package com.xhy;
import com.xhy.Mapper.*;
import com.xhy.domain.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    InputStream inputStream =null;
    SqlSession sqlSession =null;
    @Before
    public void init(){
        try {
            inputStream = Resources.getResourceAsStream("mybatis-config.xml");
            SqlSessionFactoryBuilder builder =new SqlSessionFactoryBuilder();
            SqlSessionFactory sessionFactory =builder.build(inputStream);
            sqlSession = sessionFactory.openSession();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testbatis() {


        BuyMapper departmentMapper = sqlSession.getMapper(BuyMapper.class);
        Buy buy =new Buy();


    }

    @Test
    public void testfindbyid() {


        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        User user = userMapper.findbyid(10);





    }

    @Test
    public void testsave() {



    }

    @After
    public void destroy() {


        try {
            sqlSession.commit();
            sqlSession.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
