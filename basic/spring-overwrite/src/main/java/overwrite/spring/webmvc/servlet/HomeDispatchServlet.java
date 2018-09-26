package overwrite.spring.webmvc.servlet;

import overwrite.spring.context.HomeApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author jam
 * @description
 * @create 2018-09-25
 **/
public class HomeDispatchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        // init applicationContext
        HomeApplicationContext context = new HomeApplicationContext(config.getInitParameter("contextConfigLocation"));
        System.out.println("init");
    }
}
