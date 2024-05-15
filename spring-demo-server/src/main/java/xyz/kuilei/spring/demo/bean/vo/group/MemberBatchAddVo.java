package xyz.kuilei.spring.demo.bean.vo.group;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * @author JiaKun Xu, 2023-05-29 14:38
 */
@Getter
@Setter
@NoArgsConstructor
public class MemberBatchAddVo {
    public static final class ValidMode {
        public static final String APPEND = "APPEND";
        public static final String OVERWRITE = "OVERWRITE";
    }

    @ApiModelProperty(value = "群体.id", required = true)
    @NotBlank(message = "群体.id不能为空")
    private String groupId;

    @ApiModelProperty(value = "新增方式", required = true)
    @NotBlank(message = "新增方式不能为空")
    @Pattern(regexp = "^(APPEND|OVERWRITE)$", message = "新增方式仅限：APPEND、OVERWRITE")
    private String mode;

    @ApiModelProperty(value = "群体成员列表", required = true)
    @NotEmpty(message = "群体成员列表不能为空")
    @Valid
    private List<AddVo.GroupMember> groupMembers;
}
