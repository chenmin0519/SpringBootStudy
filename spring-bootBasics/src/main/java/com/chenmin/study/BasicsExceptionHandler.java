package com.chenmin.study;


import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Order(1)
public class BasicsExceptionHandler extends ResponseEntityExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleExceptions(Exception ex, WebRequest request) {
        logger.error(ex.getMessage(), ex);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        HttpHeaders headers = new HttpHeaders();
//        ApiResultModel<Object> resp = new ApiResultModel<>();
//        resp.setRet(ApiResultModel.RET_ERROR);
//        if (ex instanceof RawErrorCodeException) {
//            resp.setErrMsg(JSONObject.toJSONString(((RawErrorCodeException) ex).getErrorCode()));
//        } else {
//            resp.setErrMsg(JSONObject.toJSONString(CoreErrors.SYS_ERR.toString()));
//        }
        Map<String,Object> resp = new HashMap<>();
        resp.put("ret",-1);
        resp.put("success",false);
        resp.put("code",500);
        resp.put("data","服务异常");
        String bodyOfResponse = JSONObject.toJSONString(resp);
		headers.add("Content-Type", "application/json;charset=UTF-8");
        return handleExceptionInternal(ex, bodyOfResponse, headers, status, request);
    }

}
