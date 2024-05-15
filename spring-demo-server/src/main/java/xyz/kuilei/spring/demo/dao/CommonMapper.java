package xyz.kuilei.spring.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author JiaKun Xu, 2023-05-29 11:23
 */
@Mapper
@Repository
public interface CommonMapper {
    /**
     * 精确到秒
     */
    Date dbSysdate();

    /**
     * 精确到毫秒
     */
    Date dbSystimestamp();
}
