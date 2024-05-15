package xyz.kuilei.spring.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import xyz.kuilei.spring.demo.bean.entity.TGroupEntity;

/**
 * @author JiaKun Xu, 2023-05-27 09:47
 */
@Mapper
@Repository
public interface TGroupDao extends BaseMapper<TGroupEntity> {
}
