package xyz.kuilei.spring.demo.txnservice;

import xyz.kuilei.spring.demo.bean.entity.TGroupEntity;
import xyz.kuilei.spring.demo.bean.entity.TGroupMemberEntity;
import xyz.kuilei.spring.demo.bean.vo.group.DeleteVo;
import xyz.kuilei.spring.demo.bean.vo.group.MemberBatchAddVo;
import xyz.kuilei.spring.demo.bean.vo.group.MemberBatchDeleteVo;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * @author JiaKun Xu, 2023-05-29 17:29
 */
public interface TxnGroupService {
    int txnAdd(@Nonnull TGroupEntity group, @Nullable List<TGroupMemberEntity> groupMembers);

    int txnDelete(DeleteVo vo);

    void txnMemberBatchAdd(MemberBatchAddVo vo);

    void txnMemberBatchDelete(MemberBatchDeleteVo vo);
}
