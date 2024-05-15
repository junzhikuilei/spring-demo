package xyz.kuilei.spring.demo.bean.vo.group;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * 不用 @Data 是因为参数有时会很长
 * 1. toString() 消耗大量资源
 * 2. 打印的日志太占空间
 *
 * @author JiaKun Xu, 2023-05-29 09:39
 */
@Getter
@Setter
@NoArgsConstructor
public class AddVo {
    @ApiModelProperty(value = "群体.名称", required = true)
    @NotBlank(message = "群体.名称不能为空")
    private String name;

    @ApiModelProperty(value = "群体成员列表")
    // nullable
    @Valid
    private List<GroupMember> groupMembers;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class GroupMember {
        @ApiModelProperty(value = "群体成员.类型", required = true)
        @NotBlank(message = "群体成员.类型不能为空")
        @Pattern(regexp = "^(PHONE_NUMBER|ID_CARD)$", message = "群体成员.类型仅限：PHONE_NUMBER、ID_CARD")
        private String type;

        @ApiModelProperty(value = "群体成员.标识符", required = true)
        @NotBlank(message = "群体成员.标识符不能为空")
        private String identifier;
    }
}
