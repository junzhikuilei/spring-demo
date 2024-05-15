package xyz.kuilei.spring.demo.common.constant;

/**
 * 结果代码枚举
 */
public enum ResultCodeEnum {
    /**
     * 操作成功
     */
    SUCCESS(2000, "操作成功！"),
    SUCCESS_CREATE(2001, "创建成功！"),
    SUCCESS_DELETE(2002, "删除成功！"),
    SUCCESS_UPDATE(2003, "更新成功！"),
    SUCCESS_QUERY(2004, "查询成功！"),
    /**
     * 操作失败
     */
    ERROR(4000, "操作失败！"),
    ERROR_CREATE(4001, "创建失败！"),
    ERROR_DELETE(4002, "删除失败！"),
    ERROR_UPDATE(4003, "更新失败！"),
    ERROR_QUERY(4004, "查询失败！"),
    /**
     * 参数校验失败
     */
    ERROR_VALIDATE(4100, "参数校验失败！");

    private final int code;
    private final String value;

    ResultCodeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
