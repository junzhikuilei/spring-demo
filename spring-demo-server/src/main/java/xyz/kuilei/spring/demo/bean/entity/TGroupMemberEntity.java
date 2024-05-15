package xyz.kuilei.spring.demo.bean.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.apache.commons.collections.CollectionUtils;
import xyz.kuilei.spring.demo.bean.vo.group.AddVo;
import xyz.kuilei.spring.demo.bean.vo.group.MemberBatchAddVo;
import xyz.kuilei.spring.demo.bean.vo.group.MemberBatchDeleteVo;
import xyz.kuilei.spring.demo.common.util.UniqueIdUtil;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 群体成员
 *
 * @author JiaKun Xu, 2023-05-26 15:05
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@TableName("T_GROUP_MEMBER")
public class TGroupMemberEntity {
    public static final String GROUP_MEMBER_ID = "GROUP_MEMBER_ID";
    public static final String GROUP_ID = "GROUP_ID";
    public static final String TYPE = "TYPE";
    public static final String IDENTIFIER = "IDENTIFIER";

    /**
     * 群体成员 id
     */
    private String groupMemberId;
    /**
     * 群体 id
     */
    private String groupId;
    /**
     * 类型
     */
    private String type;
    /**
     * 标识符（电话号码、身份证、……）
     */
    private String identifier;

    @Nonnull
    public static List<TGroupMemberEntity> fromGroupAdd(@Nonnull AddVo vo, @Nonnull TGroupEntity groupEntity) {
        List<AddVo.GroupMember> groupMembers = vo.getGroupMembers();

        if (CollectionUtils.isEmpty(groupMembers)) {
            return Collections.emptyList();
        }

        List<TGroupMemberEntity> entities = new ArrayList<>(groupMembers.size());
        String groupId = groupEntity.getGroupId();

        for (AddVo.GroupMember member : groupMembers) {
            entities.add(new TGroupMemberEntity(
                    UniqueIdUtil.uniqueId(),
                    groupId,
                    member.getType(),
                    member.getIdentifier()
            ));
        }

        return entities;
    }

    @Nonnull
    public static List<TGroupMemberEntity> fromBatchAdd(@Nonnull MemberBatchAddVo vo) {
        String groupId = vo.getGroupId();
        List<AddVo.GroupMember> groupMembers = vo.getGroupMembers();

        if (CollectionUtils.isEmpty(groupMembers)) {
            return Collections.emptyList();
        }

        List<TGroupMemberEntity> entities = new ArrayList<>(groupMembers.size());

        for (AddVo.GroupMember member : groupMembers) {
            entities.add(new TGroupMemberEntity(
                    UniqueIdUtil.uniqueId(),
                    groupId,
                    member.getType(),
                    member.getIdentifier()
            ));
        }

        return entities;
    }

    @Nonnull
    public static List<TGroupMemberEntity> fromBatchDelete(@Nonnull MemberBatchDeleteVo vo) {
        String groupId = vo.getGroupId();
        List<String> groupMemberIds = vo.getGroupMemberIds();

        if (CollectionUtils.isEmpty(groupMemberIds)) {
            return Collections.emptyList();
        }

        List<TGroupMemberEntity> entities = new ArrayList<>(groupMemberIds.size());

        for (String groupMemberId : groupMemberIds) {
            entities.add(new TGroupMemberEntity(
                    groupMemberId,
                    groupId,
                    null,
                    null
            ));
        }

        return entities;
    }
}
