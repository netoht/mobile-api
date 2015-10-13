package com.github.netoht.mobile.util.log;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.lang3.StringUtils.stripAccents;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

public class LogEvent {

    private final Map<String, Object> params = new LinkedHashMap<>();

    private LogEvent() {
    }

    public static LogEvent create(String message) {
        LogEvent result = new LogEvent();
        result.params.put("message", message);
        return result;
    }

    public LogEvent put(String param, Object value) {
        this.params.put(param, value);
        return this;
    }

    public LogEvent put(Throwable e) {
        params.put("stackTrace", ExceptionUtils.getStackTrace(e));
        return this;
    }

    @Override
    public String toString() {
        String result = new String(stripAccents(params.toString()).getBytes(), UTF_8);
        return StringUtils.substring(result, 1, result.length() - 1);
    }
}
