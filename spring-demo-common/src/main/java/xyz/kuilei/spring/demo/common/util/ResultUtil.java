package xyz.kuilei.spring.demo.common.util;

import xyz.kuilei.spring.demo.common.constant.ResultCodeEnum;
import xyz.kuilei.spring.demo.common.bean.ResultBean;

import javax.annotation.Nonnull;

public class ResultUtil {
    /**
     * 操作成功
     */
    @Nonnull
    public static <T> ResultBean<T> success(@Nonnull ResultCodeEnum codeEnum) {
        return new ResultBean<>(true, codeEnum.getCode(), codeEnum.getValue());
    }

    @Nonnull
    public static <T> ResultBean<T> success(@Nonnull ResultCodeEnum codeEnum, T result) {
        return new ResultBean<>(true, codeEnum.getCode(), codeEnum.getValue(), result);
    }

    @Nonnull
    public static <T> ResultBean<T> success(Integer code, String message) {
        return new ResultBean<>(true, code, message);
    }

    @Nonnull
    public static <T> ResultBean<T> success(Integer code, String message, T result) {
        return new ResultBean<>(true, code, message, result);
    }

    /**
     * 操作失败
     */
    @Nonnull
    public static <T> ResultBean<T> failure(@Nonnull ResultCodeEnum codeEnum) {
        return new ResultBean<>(false, codeEnum.getCode(), codeEnum.getValue());
    }

    @Nonnull
    public static <T> ResultBean<T> failure(@Nonnull ResultCodeEnum codeEnum, T result) {
        return new ResultBean<>(false, codeEnum.getCode(), codeEnum.getValue(), result);
    }

    @Nonnull
    public static <T> ResultBean<T> failure(Integer code, String message) {
        return new ResultBean<>(false, code, message);
    }

    @Nonnull
    public static <T> ResultBean<T> failure(Integer code, String message, T result) {
        return new ResultBean<>(false, code, message, result);
    }

}
