package com.mdframework.exception;

import com.mdframework.common.constant.HttpStatus;
import com.mdframework.common.core.domain.AjaxResult;
import com.mdframework.common.exception.*;
import com.mdframework.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 全局异常处理器
 * 
 * @author mdframework
 */
@RestControllerAdvice
public class GlobalExceptionHandler
{
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 基础异常
     */
    @ExceptionHandler(BaseException.class)
    public AjaxResult baseException(BaseException e)
    {
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(CustomException.class)
    public AjaxResult businessException(CustomException e)
    {
        if (StringUtils.isNull(e.getCode()))
        {
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public AjaxResult handlerNoFoundException(Exception e)
    {
        log.error(e.getMessage(), e);
        return AjaxResult.error(HttpStatus.NOT_FOUND, "路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(Exception.class)
    public AjaxResult handleException(Exception e)
    {
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public AjaxResult validatedBindException(BindException e)
    {
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return AjaxResult.error(HttpStatus.BAD_REQUEST,message);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object validExceptionHandler(MethodArgumentNotValidException e)
    {
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return AjaxResult.error(HttpStatus.BAD_REQUEST,message);
    }

    /**
     * 参数异常
     */
    @ExceptionHandler(ParamException.class)
    public Object paramExceptionHandler(ParamException e)
    {
        String message = e.getMessage();
        return AjaxResult.error(HttpStatus.BAD_REQUEST,message);
    }

    /**
     * 数据不存在异常
     */
    @ExceptionHandler(DataNotFoundException.class)
    public Object dataNotFoundExceptionHandler(DataNotFoundException e)
    {
        String message = e.getMessage();
        return AjaxResult.error(HttpStatus.NOT_FOUND,message);
    }

    /**
     * 数据不存在异常
     */
    @ExceptionHandler(DataExistException.class)
    public Object dataExistExceptionHandler(DataExistException e)
    {
        String message = e.getMessage();
        return AjaxResult.error(HttpStatus.CONFLICT,message);
    }

    /**
     * 演示模式异常
     */
    @ExceptionHandler(DemoModeException.class)
    public AjaxResult demoModeException(DemoModeException e)
    {
        return AjaxResult.error("演示模式，不允许操作");
    }
}
