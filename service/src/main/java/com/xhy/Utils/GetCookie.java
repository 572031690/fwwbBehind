package com.xhy.Utils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetCookie extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        List<Cookie> cookies1 = new ArrayList<>();
        if(cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                cookies1.add(cookie);
            }
            req.setAttribute("cookie", cookies1);
        }
        else
        {
            req.setAttribute("error","cookie为空");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
