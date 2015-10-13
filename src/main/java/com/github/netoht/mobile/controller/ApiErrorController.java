package com.github.netoht.mobile.controller;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
public class ApiErrorController implements ErrorController {

    @Value("${error.path:/error}")
    private String errorPath;
    private final ErrorAttributes errorAttributes;
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final static String SESSION_ID_ATTRIBUTE = "javax.servlet.request.ssl_session_id";
    private final static String STATUS_CODE_ATTRIBUTE = "javax.servlet.error.status_code";

    public ApiErrorController() {
        this(new DefaultErrorAttributes());
    }

    public ApiErrorController(ErrorAttributes errorAttributes) {
        Assert.notNull(errorAttributes, "ErrorAttributes must not be null");
        this.errorAttributes = errorAttributes;
    }

    @Override
    public String getErrorPath() {
        return this.errorPath;
    }

    @RequestMapping({ "${error.path:/error}" })
    @ResponseBody
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        Map<String, Object> body = errorAttributes.getErrorAttributes(requestAttributes, true);
        HttpStatus status = getStatus(request);
        Object sessionId = request.getAttribute(SESSION_ID_ATTRIBUTE);
        Throwable exception = this.errorAttributes.getError(requestAttributes);
        Object stackTrace = (exception == null) ? null : ExceptionUtils.getStackTrace(exception);
        Object error = body.get("error");

        // TODO - adicionar o LogEvent e diminuir essa classe
        log.error("statusCode={}, error={}, sessionId={}, path={}, exception={}, message={}, trace={}", body.get("status"), body.get("error"), sessionId, body.get("path"), body.get("exception"), ExceptionUtils.getMessage(exception), stackTrace);

        body.clear();

        if (status.is5xxServerError()) {
            body.put("message", "Sistema indisponível no momento");
        } else {
            String message = StringUtils.substringAfter(ExceptionUtils.getMessage(exception), ":");
            body.put("message", StringUtils.isBlank(message) ? error : message);
        }

        return new ResponseEntity<Map<String, Object>>(body, status);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute(STATUS_CODE_ATTRIBUTE);
        if (statusCode != null) {
            try {
                return HttpStatus.valueOf(statusCode.intValue());
            } catch (Exception ex) {
            }
        }
        return INTERNAL_SERVER_ERROR;
    }
}