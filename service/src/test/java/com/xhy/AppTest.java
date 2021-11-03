package com.xhy;

import static org.junit.Assert.assertTrue;

import com.xhy.domain.User;
import com.xhy.service.NeedService;
import com.xhy.service.UserServise;
import com.xhy.service.impl.NeedServiceimpl;
import com.xhy.service.impl.UserServiceimpl;
import org.junit.Test;

import java.text.SimpleDateFormat;
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
        StringBuilder stringBuilderuser = new StringBuilder();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(new Date());
        String[] split = format.split("-");
        String s = stringBuilderuser.append("ZNKC" + split[0]+split[1]+split[2]).toString();
        System.out.println("s:"+s);
    }
}
