package com.company.project.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @author chenyin
 */
@ControllerAdvice
@Slf4j
public class BizExceptionHandler {

//    @ExceptionHandler(Exception.class)
//    public BaseResult handleBizException(Exception e) {
//        BaseResult result = new BaseResult();
//        if (e instanceof BizException) {
//            BizException bizException = (BizException) e;
//            result.setCode(bizException.getErrorCode());
//            result.setMsg(e.getMessage());
//            log.error(result.toString());
//        } else {
//            e.printStackTrace();
//            //将系统异常以打印出来
//            log.error("系统异常", e);
//            result.setCode(ErrorCode.RESULT_ERROR.getErrorCode());
//            result.setMsg(ErrorCode.RESULT_ERROR.getErrorMsg());
//        }
//        return result;
//    }
}
