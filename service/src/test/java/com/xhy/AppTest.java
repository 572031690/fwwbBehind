package com.xhy;

import static org.junit.Assert.assertTrue;

import com.xhy.domain.User;
import com.xhy.service.NeedService;
import com.xhy.service.UserServise;
import com.xhy.service.impl.NeedServiceimpl;
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
        NeedService needService = new NeedServiceimpl();
    }
}
