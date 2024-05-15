package xyz.kuilei.spring.demo.common.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 通用返回结果
 */
@Getter
@Setter
@ToString
public class ResultBean<T> {
    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 信息
     */
    private String message;

    /**
     * 返回结果
     */
    private T result;

    /**
     * 执行时间
     */
    private Long spendTime;

    public ResultBean(Boolean success, Integer code, String message, T result) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public ResultBean(Boolean success, Integer code, String message) {
        this(success, code, message, null);
    }
}
