package xyz.kuilei.spring.demo.common.config;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.kuilei.spring.demo.common.bean.ResultBean;
import xyz.kuilei.spring.demo.common.constant.ResultCodeEnum;

import javax.annotation.Nonnull;
import java.util.Iterator;

/**
 * @author JiaKun Xu, 2023-06-14 14:58
 */
@RestControllerAdvice
public class DemoExceptionHandler {
    /**
     * 针对 @RequestBody @Validated vo 对象
     * Content-Type: application/json
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultBean handleValidateException(MethodArgumentNotValidException e) {
        return toBean(e.getBindingResult());
    }

    /**
     * 针对 @Validated vo 对象
     * Content-Type: multipart/form-data
     */
    @ExceptionHandler(BindException.class)
    public ResultBean handleException(BindException e) {
        return toBean(e.getBindingResult());
    }

    @Nonnull
    private static ResultBean toBean(@Nonnull BindingResult result) {
        return new ResultBean(
                false,
                ResultCodeEnum.ERROR_VALIDATE.getCode(),
                ResultCodeEnum.ERROR_VALIDATE.getValue() + errorsToString(result)
        );
    }

    @Nonnull
    private static String errorsToString(@Nonnull BindingResult result) {
        StringBuilder sb = new StringBuilder();

        if (result.hasErrors()) {
            Iterator<ObjectError> errItr = result.getAllErrors().iterator();

            while (errItr.hasNext()) {
                sb.append(errItr.next().getDefaultMessage());

                if (errItr.hasNext()) {
                    sb.append("；");
                }
            }
        }

        return sb.toString();
    }
}
