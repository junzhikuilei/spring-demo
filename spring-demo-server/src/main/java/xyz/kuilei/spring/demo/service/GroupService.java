package xyz.kuilei.spring.demo.service;

import xyz.kuilei.spring.demo.bean.entity.TGroupEntity;
import xyz.kuilei.spring.demo.bean.entity.TGroupMemberEntity;
import xyz.kuilei.spring.demo.bean.vo.group.*;
import xyz.kuilei.spring.demo.common.bean.PageBean;

/**
 * @author JiaKun Xu, 2023-05-25 09:55
 */
public interface GroupService {
    PageBean<TGroupEntity> list(ListVo vo);

    void add(AddVo vo);

    void delete(DeleteVo vo);

    void update(UpdateVo vo);

    void memberBatchAdd(MemberBatchAddVo vo);

    void memberBatchDelete(MemberBatchDeleteVo vo);

    void memberUpdate(MemberUpdateVo vo);

    PageBean<TGroupMemberEntity> memberList(MemberListVo vo);
}
