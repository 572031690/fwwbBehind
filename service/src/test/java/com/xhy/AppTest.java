package com.xhy;

import static org.junit.Assert.assertTrue;

import com.xhy.domain.User;
import com.xhy.service.UserServise;
import com.xhy.service.impl.UserServiceimpl;
import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
   @Test
     public void shouldAnswerWithTrue()
    {
//        UserServise userServise= new UserServiceimpl();
//        List<User> list =userServise.findalluser();
//        for(User user :list){
//            System.out.println(user);
//        }
        Date date = new Date();
        System.out.println(date);
    }
}
