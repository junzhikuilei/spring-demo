package xyz.kuilei.spring.demo.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import xyz.kuilei.spring.demo.bean.entity.TGroupEntity;
import xyz.kuilei.spring.demo.bean.entity.TGroupMemberEntity;
import xyz.kuilei.spring.demo.bean.vo.group.*;
import xyz.kuilei.spring.demo.common.bean.PageBean;
import xyz.kuilei.spring.demo.common.bean.ResultBean;
import xyz.kuilei.spring.demo.common.constant.ResultCodeEnum;
import xyz.kuilei.spring.demo.common.util.ResultUtil;
import xyz.kuilei.spring.demo.service.GroupService;

/**
 * 群体管理
 *
 * @author JiaKun Xu, 2023-05-23 11:52
 */
@Api(value = "群体管理", tags = "群体管理")
@RestController
@RequestMapping("/group")
@Slf4j
public class GroupController {
    @Autowired
    private GroupService groupService;

    @ApiOperation(value = "列出群体")
    @ApiOperationSupport(order = 1)
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResultBean<PageBean<TGroupEntity>> list(@RequestBody @Validated ListVo vo) {
        try {
            return ResultUtil.success(ResultCodeEnum.SUCCESS_QUERY, groupService.list(vo));
        } catch (Exception e) {
            log.error("异常日志：", e);
            return ResultUtil.failure(ResultCodeEnum.ERROR_QUERY.getCode(), e.getMessage());
        }
    }

    @ApiOperation(value = "新增群体")
    @ApiOperationSupport(order = 2)
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultBean add(@RequestBody @Validated AddVo vo) {
        try {
            groupService.add(vo);
            return ResultUtil.success(ResultCodeEnum.SUCCESS_CREATE);
        } catch (Exception e) {
            log.error("异常日志：", e);
            return ResultUtil.failure(ResultCodeEnum.ERROR_CREATE.getCode(), e.getMessage());
        }
    }

    @ApiOperation(value = "删除群体")
    @ApiOperationSupport(order = 3)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultBean delete(@RequestBody @Validated DeleteVo vo) {
        try {
            groupService.delete(vo);
            return ResultUtil.success(ResultCodeEnum.SUCCESS_DELETE);
        } catch (Exception e) {
            log.error("异常日志：", e);
            return ResultUtil.failure(ResultCodeEnum.ERROR_DELETE.getCode(), e.getMessage());
        }
    }

    @ApiOperation(value = "修改群体")
    @ApiOperationSupport(order = 4)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultBean update(@RequestBody @Validated UpdateVo vo) {
        try {
            groupService.update(vo);
            return ResultUtil.success(ResultCodeEnum.SUCCESS_UPDATE);
        } catch (Exception e) {
            log.error("异常日志：", e);
            return ResultUtil.failure(ResultCodeEnum.ERROR_UPDATE.getCode(), e.getMessage());
        }
    }

    @ApiOperation(value = "批量新增群体成员")
    @ApiOperationSupport(order = 5)
    @RequestMapping(value = "/member/batchAdd", method = RequestMethod.POST)
    public ResultBean memberBatchAdd(@RequestBody @Validated MemberBatchAddVo vo) {
        try {
            groupService.memberBatchAdd(vo);
            return ResultUtil.success(ResultCodeEnum.SUCCESS);
        } catch (Exception e) {
            log.error("异常日志：", e);
            return ResultUtil.failure(ResultCodeEnum.ERROR.getCode(), e.getMessage());
        }
    }

    @ApiOperation(value = "批量删除群体成员")
    @ApiOperationSupport(order = 6)
    @RequestMapping(value = "/member/batchDelete", method = RequestMethod.POST)
    public ResultBean memberBatchDelete(@RequestBody @Validated MemberBatchDeleteVo vo) {
        try {
            groupService.memberBatchDelete(vo);
            return ResultUtil.success(ResultCodeEnum.SUCCESS_DELETE);
        } catch (Exception e) {
            log.error("异常日志：", e);
            return ResultUtil.failure(ResultCodeEnum.ERROR_DELETE.getCode(), e.getMessage());
        }
    }

    @ApiOperation(value = "修改群体成员")
    @ApiOperationSupport(order = 7)
    @RequestMapping(value = "/member/update", method = RequestMethod.POST)
    public ResultBean memberUpdate(@RequestBody @Validated MemberUpdateVo vo) {
        try {
            groupService.memberUpdate(vo);
            return ResultUtil.success(ResultCodeEnum.SUCCESS_UPDATE);
        } catch (Exception e) {
            log.error("异常日志：", e);
            return ResultUtil.failure(ResultCodeEnum.ERROR_UPDATE.getCode(), e.getMessage());
        }
    }

    @ApiOperation(value = "列出群体成员")
    @ApiOperationSupport(order = 8)
    @RequestMapping(value = "/member/list", method = RequestMethod.POST)
    public ResultBean<PageBean<TGroupMemberEntity>> memberList(@RequestBody @Validated MemberListVo vo) {
        try {
            return ResultUtil.success(ResultCodeEnum.SUCCESS_QUERY, groupService.memberList(vo));
        } catch (Exception e) {
            log.error("异常日志：", e);
            return ResultUtil.failure(ResultCodeEnum.ERROR_QUERY.getCode(), e.getMessage());
        }
    }
}
