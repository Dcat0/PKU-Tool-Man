package edu.pku.PKUToolMan.Interceptor;

import com.fasterxml.jackson.databind.util.JSONPObject;
import edu.pku.PKUToolMan.Utils.Result;
import edu.pku.PKUToolMan.Utils.TokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
* 目前的Interception旨在拦截写在header里的token
* 但后端目前传回前端状态码没写在header里，而是写在json里
* 可能需要之后商量之后修改
*
* thanks to
* https://blog.csdn.net/songfei_dream/article/details/103335575?utm_medium=distribute.pc_relevant.none-task-blog-title-2&spm=1001.2101.3001.4242
* */

@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request.getRequestURI());

        if(request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        String token = request.getHeader("token");
        System.out.println(token);
        System.out.flush();
        if(token != null) {
            boolean result = TokenUtil.verify(token);
            if(result) {
                return true;
            }
        }
        try {
            response.getWriter().write(Result.AUTH_ERROR().toJSONString());
            System.out.println("INTERCEPTION: token error");
            System.out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.sendError(500);
            return false;
        }
        return false;
    }
}
