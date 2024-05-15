package xyz.kuilei.spring.demo.bean.vo.group;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @author JiaKun Xu, 2023-05-29 14:09
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class DeleteVo {
    @ApiModelProperty(value = "群体.id", required = true)
    @NotBlank(message = "群体.id不能为空")
    private String groupId;
}
