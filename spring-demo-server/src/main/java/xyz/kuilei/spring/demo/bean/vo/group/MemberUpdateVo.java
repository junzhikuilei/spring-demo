package xyz.kuilei.spring.demo.bean.vo.group;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author JiaKun Xu, 2023-05-29 15:17
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class MemberUpdateVo {
    @ApiModelProperty(value = "群体成员.id", required = true)
    @NotBlank(message = "群体成员.id不能为空")
    private String groupMemberId;

    @ApiModelProperty(value = "群体.id", required = true)
    @NotBlank(message = "群体.id不能为空")
    private String groupId;

    @ApiModelProperty(value = "群体成员.类型", required = true)
    @NotBlank(message = "群体成员.类型不能为空")
    @Pattern(regexp = "^(PHONE_NUMBER|ID_CARD)$", message = "群体成员.类型仅限：PHONE_NUMBER、ID_CARD")
    private String type;

    @ApiModelProperty(value = "群体成员.标识符", required = true)
    @NotBlank(message = "群体成员.标识符不能为空")
    private String identifier;
}
