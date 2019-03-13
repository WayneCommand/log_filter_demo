package com.shenlan.log_filter_demo.filter;


import com.shenlan.log_filter_demo.utils.RequestWrapper;
import com.shenlan.log_filter_demo.utils.ResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.UUID;

public class APILogFilter extends OncePerRequestFilter {

    private final static Logger LOG = LoggerFactory.getLogger(APILogFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        httpServletRequest = new RequestWrapper( httpServletRequest);
        httpServletResponse = new ResponseWrapper( httpServletResponse);
        try {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } finally {
            String uri = httpServletRequest.getRequestURI();
            String id = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
            logRequest((RequestWrapper) httpServletRequest,uri,id);
            logResponse((ResponseWrapper) httpServletResponse, uri,id);
        }
    }

    private void logRequest(final RequestWrapper request, String uri, String id) {
        StringBuilder msg = new StringBuilder();
        try {
            msg.append("APILog ");
            msg.append("RequestURI[" + uri + "] ");
            msg.append("id=" + id + " ");
            if (isMultipart(request)) {
                msg.append("param = 'file'");
                LOG.info(msg.toString());
                return;
            }
            Enumeration<String> parameterNames = request.getParameterNames();
            msg.append("param = '");
            while (parameterNames.hasMoreElements()) {
                String name = parameterNames.nextElement();
                msg.append(name + " = ");
                msg.append(request.getParameter(name) + " ");
            }
            msg.append("' ");
            msg.append("body = ");
            msg.append("'" + request.getBodyString(request) + "'");
            LOG.info(msg.toString());
        } catch (IOException e) {
            LOG.error("APILogFilter logRequest error", e);
        }
    }

    private void logResponse(final ResponseWrapper response, String uri, String id) {
        StringBuilder msg = new StringBuilder();
        try {
            msg.append("APILog ");
            msg.append("RequestURI[" + uri + "] ");
            msg.append("id=" + id + " ");
            msg.append("status=" + response.getStatus() + " ");
            msg.append("ResponseBody = ");
            msg.append("'" + new String(response.toByteArray(), response.getCharacterEncoding()) + "'");
        } catch (UnsupportedEncodingException e) {
            LOG.error("APILogFilter logRequest error", e);
        }
        LOG.info(msg.toString());
    }

    private boolean isMultipart(final RequestWrapper request) {
        return request.getContentType() != null && request.getContentType().startsWith("multipart/form-data");
    }
}
