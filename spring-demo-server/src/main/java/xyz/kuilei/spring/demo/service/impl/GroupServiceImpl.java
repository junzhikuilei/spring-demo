package xyz.kuilei.spring.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.kuilei.spring.demo.bean.entity.TGroupEntity;
import xyz.kuilei.spring.demo.bean.entity.TGroupMemberEntity;
import xyz.kuilei.spring.demo.bean.vo.group.*;
import xyz.kuilei.spring.demo.common.bean.PageBean;
import xyz.kuilei.spring.demo.dao.TGroupDao;
import xyz.kuilei.spring.demo.dao.TGroupMemberDao;
import xyz.kuilei.spring.demo.service.CommonService;
import xyz.kuilei.spring.demo.service.GroupService;
import xyz.kuilei.spring.demo.txnservice.TxnGroupService;

import java.util.Date;
import java.util.List;

/**
 * NOTE: 在读、写操作时，不判断群体的存在性，假设只会在页面上操作。
 *
 * @author JiaKun Xu, 2023-05-25 09:56
 */
@Service
@Slf4j
public class GroupServiceImpl implements GroupService {
    /**
     * 用于 baomidou 批量操作
     */
    private final Log ibatisLog = LogFactory.getLog(getClass());

    @Autowired
    private CommonService commonService;

    @Autowired
    private TxnGroupService txnGroupService;

    @Autowired
    private TGroupDao tGroupDao;
    @Autowired
    private TGroupMemberDao tGroupMemberDao;

    /**
     * 列出群体
     */
    @Override
    public PageBean<TGroupEntity> list(ListVo vo) {
        Page<TGroupEntity> page = new Page<TGroupEntity>(vo.getPageNo(), vo.getPageSize())
                .addOrder(OrderItem.desc(TGroupEntity.CREATE_TIME));

        return new PageBean<>(tGroupDao.selectPage(page, null));
    }

    /**
     * 新增群体
     */
    @Override
    public void add(AddVo vo) {
        final String groupName = vo.getName();

        // 群体.名称不能重复
        QueryWrapper<TGroupEntity> wrapper1 = new QueryWrapper<TGroupEntity>()
                .eq(TGroupEntity.NAME, groupName);

        if (tGroupDao.selectCount(wrapper1) != 0) {
            throw new IllegalArgumentException(String.format("群体.名称 [%s] 不能重复", groupName));
        }

        /*
         * 入库 entity
         */
        Date nowDate = commonService.nowDate();
        // 群体
        TGroupEntity groupEntity = TGroupEntity.fromAdd(vo, nowDate);
        // 群体成员
        List<TGroupMemberEntity> groupMemberEntities = TGroupMemberEntity.fromGroupAdd(vo, groupEntity);

        txnGroupService.txnAdd(groupEntity, groupMemberEntities);
    }

    /**
     * 删除群体
     */
    @Override
    public void delete(DeleteVo vo) {
        // WARN: baomidou dao.selectOne() 在获取到多条记录时是会报错的，可以通过以下方式只获取第一个。

//        String groupId = vo.getGroupId();
//        TGroupEntity usage = SqlHelper.getObject(ibatisLog, tGroupDao.selectList(null));
//        if (usage != null) {
//            throw new IllegalArgumentException(String.format("群体 [%s] 正在使用，不能删除", groupId));
//        }

        txnGroupService.txnDelete(vo);
    }

    /**
     * 修改群体基本信息
     */
    @Override
    public void update(UpdateVo vo) {
        String groupId = vo.getGroupId();
        String name = vo.getName();

        // 群体.名称不能重复
        QueryWrapper<TGroupEntity> wrapper1 = new QueryWrapper<TGroupEntity>()
                .eq(TGroupEntity.NAME, name)
                .ne(TGroupEntity.GROUP_ID, groupId);  // 自己除外

        if (tGroupDao.selectCount(wrapper1) != 0) {
            throw new IllegalArgumentException(String.format("群体名称 [%s] 不能重复", name));
        }

        Date nowDate = commonService.nowDate();
        UpdateWrapper<TGroupEntity> wrapper2 = new UpdateWrapper<TGroupEntity>()
                .set(TGroupEntity.NAME, name)
                .set(TGroupEntity.UPDATE_TIME, nowDate)
                //
                .eq(TGroupEntity.GROUP_ID, groupId);

        tGroupDao.update(null, wrapper2);
    }

    /**
     * 批量新增群体成员
     */
    @Override
    public void memberBatchAdd(MemberBatchAddVo vo) {
        txnGroupService.txnMemberBatchAdd(vo);
    }

    /**
     * 批量删除群体成员
     */
    @Override
    public void memberBatchDelete(MemberBatchDeleteVo vo) {
        txnGroupService.txnMemberBatchDelete(vo);
    }

    /**
     * 修改群体成员
     */
    @Override
    public void memberUpdate(MemberUpdateVo vo) {
        UpdateWrapper<TGroupMemberEntity> wrapper1 = new UpdateWrapper<TGroupMemberEntity>()
                .set(TGroupMemberEntity.TYPE, vo.getType())
                .set(TGroupMemberEntity.IDENTIFIER, vo.getIdentifier())
                //
                .eq(TGroupMemberEntity.GROUP_MEMBER_ID, vo.getGroupMemberId())
                .eq(TGroupMemberEntity.GROUP_ID, vo.getGroupId());

        tGroupMemberDao.update(null, wrapper1);
    }

    /**
     * 列出群体成员
     */
    @Override
    public PageBean<TGroupMemberEntity> memberList(MemberListVo vo) {
        long pageNo = vo.getPageNo();
        long pageSize = vo.getPageSize();

        String groupId = vo.getGroupId();
        String eqType = vo.getEqType();
        String likeIdentifier = vo.getLikeIdentifier();

        // 条件
        // NOTE: like 没有处理特殊字符 '%', '_'，不过嘛，一般不会出现这种标识符。
        QueryWrapper<TGroupMemberEntity> wrapper1 = new QueryWrapper<TGroupMemberEntity>()
                .eq(TGroupMemberEntity.GROUP_ID, groupId)
                .eq(StringUtils.isNotBlank(eqType), TGroupMemberEntity.TYPE, eqType)
                .like(StringUtils.isNotBlank(likeIdentifier), TGroupMemberEntity.IDENTIFIER, likeIdentifier);

        // 分页
        // NOTE: baomidou page 排序是全局的，而且效率要比在 wrapper 排序高，因为排序的是过滤后的。
        Page<TGroupMemberEntity> page1 = new Page<TGroupMemberEntity>(pageNo, pageSize)
                .addOrder(OrderItem.asc(TGroupMemberEntity.GROUP_MEMBER_ID));

        return new PageBean<>(tGroupMemberDao.selectPage(page1, wrapper1));
    }
}
