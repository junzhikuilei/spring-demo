package xyz.kuilei.spring.demo.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author JiaKun Xu, 2023-05-30 15:11
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PageVo {
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码必须 >= 1")
    @ApiModelProperty(value = "页码", required = true, position = 1)
    private Integer pageNo;

    @NotNull(message = "每页显示条数不能为空")
    @Min(value = 1, message = "每页显示条数必须 >= 1")
    @ApiModelProperty(value = "每页显示条数", required = true, position = 2)
    private Integer pageSize;
}
