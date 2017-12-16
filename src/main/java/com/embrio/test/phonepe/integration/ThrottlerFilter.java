package com.embrio.test.phonepe.integration;

import com.embrio.test.phonepe.config.Specialization;
import com.embrio.test.phonepe.exceptions.ThrottlerLimitBreachedException;
import com.embrio.test.phonepe.throttler.Request;
import com.embrio.test.phonepe.throttler.Throttler;
import com.embrio.test.phonepe.throttler.ThrottlerFactory;
import com.embrio.test.phonepe.throttler.ThrottlerImpl;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Enumeration;

public class ThrottlerFilter implements Filter{
    private ThrottlerFactory factory = new ThrottlerFactory();
    private Throttler throttler;
    private String clientHeader;

    public ThrottlerFilter() throws Exception{
         this("X-Client-Id");
    }

    public ThrottlerFilter(String name) throws Exception{
        clientHeader = name;
        throttler = factory.defaultThrottler();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String client = httpRequest.getHeader(clientHeader);

        try {
            LocalDateTime time = LocalDateTime.now();
            if (client != null && !client.isEmpty()) {
                String method = httpRequest.getMethod();
                String api = httpRequest.getContextPath();

                Request req = new Request();
                req.setClient(client);
                req.setRequestTime(time);
                req.setApi(api);
                req.setMethod(method);
                throttler.throttle(req);
            }

            //doFilter
            chain.doFilter(httpRequest, response);
        } catch (ThrottlerLimitBreachedException ex) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.sendError(429, ex.getMessage());
        }
    }

    @Override
    public void destroy() {

    }
}
