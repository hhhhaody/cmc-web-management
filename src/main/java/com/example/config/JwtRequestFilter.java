package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 这个类主要用于解析和验证 HTTP 请求头中的 JWT 令牌，并从中提取用户名。如果令牌无效或不包含用户名，则会抛出异常。如果一切正常，则会继续执行后续的过滤器或目标资源。
 */

// JwtRequestFilter类实现了Filter接口，用于拦截和处理JWT相关的HTTP请求
public class JwtRequestFilter implements Filter {

    @Autowired
    private JwtUtil jwtUtil;

    // doFilter方法用于实际的请求处理
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 将ServletRequest和ServletResponse对象转为HttpServletRequest和HttpServletResponse对象
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 获取请求头中的"Authorization"字段
        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        // 如果Authorization请求头不为空，并且以"Bearer"开头
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            // 截取JWT令牌部分
            jwtToken = requestTokenHeader.substring(7);
            try {
                // 从JWT令牌中解析出用户名
                username = jwtUtil.getUsernameFromToken(jwtToken);
            } catch (Exception e) {
                // 如果JWT令牌无效，则抛出异常
                throw new ServletException("Invalid token");
            }
        }

        // 如果用户名为空，即JWT令牌未包含有效的用户名信息，则抛出异常
        if (username == null) {
            throw new ServletException("JWT Token does not begin with Bearer String");
        }

        // 调用后续的过滤器或目标资源（如果存在）
        filterChain.doFilter(request, response);
    }
}
