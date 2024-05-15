package xyz.kuilei.spring.demo.bean.vo.group;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MemberBatchDeleteVo {
    @ApiModelProperty(value = "群体.id", required = true)
    @NotBlank(message = "群体.id不能为空")
    private String groupId;

    @ApiModelProperty(value = "群体成员.id列表", required = true)
    @NotEmpty(message = "群体成员.id列表不能为空")
    @Valid
    private List<@NotBlank(message = "群体成员.id不能为空") String> groupMemberIds;
}
