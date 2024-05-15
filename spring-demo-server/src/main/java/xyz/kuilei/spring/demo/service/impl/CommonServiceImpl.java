package xyz.kuilei.spring.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.kuilei.spring.demo.dao.CommonMapper;
import xyz.kuilei.spring.demo.service.CommonService;

import javax.annotation.Nonnull;
import java.util.Date;

/**
 * @author JiaKun Xu, 2023-11-18 10:47:36
 */
@Service
public class CommonServiceImpl implements CommonService {
    @Autowired
    private CommonMapper commonMapper;

    /**
     * 统一时间字段，按需
     *   1. 使用 db 时间 or srv 时间
     *   2. 精确到秒 or 毫秒
     */
    @Nonnull
    @Override
    public Date nowDate() {
        return dbSysdate();
    }

    /**
     * db 时间，精确到秒
     */
    @Nonnull
    private Date dbSysdate() {
        return commonMapper.dbSysdate();
    }

    /**
     * db 时间，精确到毫秒
     */
    @Nonnull
    private Date dbSystimestamp() {
        return commonMapper.dbSystimestamp();
    }

    /**
     * srv 时间，精确到秒
     */
    @Nonnull
    private Date srvSysdate() {
        return new Date(System.currentTimeMillis() / 1000 * 1000);
    }

    /**
     * srv 时间，精确到毫秒
     */
    @Nonnull
    private Date srvSystimestamp() {
        return new Date();
    }
}
