package xyz.kuilei.spring.demo.bean.vo.group;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import xyz.kuilei.spring.demo.bean.vo.PageVo;

import javax.validation.constraints.NotBlank;

/**
 * @author JiaKun Xu, 2023-05-29 15:25
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class MemberListVo extends PageVo {
    @ApiModelProperty(value = "群体.id", required = true)
    @NotBlank(message = "群体.id不能为空")
    private String groupId;

    @ApiModelProperty(value = "equals 群体成员.类型")
    // nullable
    private String eqType;

    @ApiModelProperty(value = "like 群体成员.标识符")
    // nullable
    private String likeIdentifier;
}
