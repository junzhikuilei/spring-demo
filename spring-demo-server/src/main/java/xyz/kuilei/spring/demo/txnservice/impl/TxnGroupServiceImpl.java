package xyz.kuilei.spring.demo.txnservice.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.kuilei.spring.demo.bean.entity.TGroupEntity;
import xyz.kuilei.spring.demo.bean.entity.TGroupMemberEntity;
import xyz.kuilei.spring.demo.bean.vo.group.DeleteVo;
import xyz.kuilei.spring.demo.bean.vo.group.MemberBatchAddVo;
import xyz.kuilei.spring.demo.bean.vo.group.MemberBatchDeleteVo;
import xyz.kuilei.spring.demo.dao.GroupMapper;
import xyz.kuilei.spring.demo.dao.TGroupDao;
import xyz.kuilei.spring.demo.dao.TGroupMemberDao;
import xyz.kuilei.spring.demo.txnservice.TxnGroupService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * @author JiaKun Xu, 2023-05-29 17:29
 */
@Service
public class TxnGroupServiceImpl implements TxnGroupService {
    /**
     * 用于 baomidou 批量操作
     */
    private final Log ibatisLog = LogFactory.getLog(getClass());

    @Autowired
    private TGroupDao tGroupDao;
    @Autowired
    private TGroupMemberDao tGroupMemberDao;

    /**
     * 新增群体
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int txnAdd(@Nonnull TGroupEntity group, @Nullable List<TGroupMemberEntity> groupMembers) {
        // 群体
        final int did = tGroupDao.insert(group);

        if (did == 1) {
            // 群体成员
            if (CollectionUtils.isNotEmpty(groupMembers)) {
                String sqlStatement = SqlHelper.getSqlStatement(
                        TGroupMemberDao.class,
                        SqlMethod.INSERT_ONE
                );
                SqlHelper.executeBatch(
                        TGroupMemberEntity.class,
                        ibatisLog,
                        groupMembers,
                        1000,
                        (sqlSession, entity) -> sqlSession.insert(sqlStatement, entity)
                );
            }
        }

        return did;
    }

    /**
     * 删除群体
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int txnDelete(DeleteVo vo) {
        String groupId = vo.getGroupId();

        // 群体成员
        QueryWrapper<TGroupMemberEntity> wrapper1 = new QueryWrapper<TGroupMemberEntity>()
                .eq(TGroupMemberEntity.GROUP_ID, groupId);

        tGroupMemberDao.delete(wrapper1);

        // 群体
        QueryWrapper<TGroupEntity> wrapper2 = new QueryWrapper<TGroupEntity>()
                .eq(TGroupEntity.GROUP_ID, groupId);

        return tGroupDao.delete(wrapper2);
    }

    /**
     * 新增群体成员
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void txnMemberBatchAdd(MemberBatchAddVo vo) {
        List<TGroupMemberEntity> groupMembers = TGroupMemberEntity.fromBatchAdd(vo);

        if (CollectionUtils.isEmpty(groupMembers)) {
            return;
        }

        String groupId = vo.getGroupId();
        String mode = vo.getMode();

        // 覆盖 (先删除，后追加)
        if (MemberBatchAddVo.ValidMode.OVERWRITE.equals(mode)) {
            QueryWrapper<TGroupMemberEntity> wrapper1 = new QueryWrapper<TGroupMemberEntity>()
                    .eq(TGroupMemberEntity.GROUP_ID, groupId);
            tGroupMemberDao.delete(wrapper1);
        }

        /**
         * WARN: 能这样用也是看了源码 {@link SqlHelper#getSqlStatement}。
         */
        // 追加
        String sqlStatement = GroupMapper.class.getName() + StringPool.DOT + "memberMerge";
        SqlHelper.executeBatch(
                TGroupMemberEntity.class,
                ibatisLog,
                groupMembers,
                1000,
                (sqlSession, entity) -> sqlSession.insert(sqlStatement, entity)
        );
    }

    /**
     * 批量新增群体成员
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void txnMemberBatchDelete(MemberBatchDeleteVo vo) {
        List<TGroupMemberEntity> groupMembers = TGroupMemberEntity.fromBatchDelete(vo);

        if (CollectionUtils.isEmpty(groupMembers)) {
            return;
        }

        String sqlStatement = GroupMapper.class.getName() + StringPool.DOT + "memberDelete";
        SqlHelper.executeBatch(
                TGroupMemberEntity.class,
                ibatisLog,
                groupMembers,
                1000,
                (sqlSession, entity) -> sqlSession.delete(sqlStatement, entity)
        );
    }

}
