package xyz.kuilei.spring.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import xyz.kuilei.spring.demo.bean.entity.TGroupMemberEntity;
import xyz.kuilei.spring.demo.bean.vo.group.MemberBatchAddVo;
import xyz.kuilei.spring.demo.bean.vo.group.MemberBatchDeleteVo;
import xyz.kuilei.spring.demo.txnservice.impl.TxnGroupServiceImpl;

import javax.annotation.Nonnull;

/**
 * @author JiaKun Xu, 2023-05-29 16:59
 */
@Mapper
@Repository
public interface GroupMapper {
    /**
     * 此方法不要删除
     *
     * @see TxnGroupServiceImpl#txnMemberBatchAdd(MemberBatchAddVo)
     */
    int memberMerge(@Nonnull TGroupMemberEntity entity);

    /**
     * 此方法不要删除
     *
     * @see TxnGroupServiceImpl#txnMemberBatchDelete(MemberBatchDeleteVo)
     */
    int memberDelete(@Nonnull TGroupMemberEntity entity);
}
