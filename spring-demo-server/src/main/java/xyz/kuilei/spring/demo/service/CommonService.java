package xyz.kuilei.spring.demo.service;

import javax.annotation.Nonnull;
import java.util.Date;

/**
 * @author JiaKun Xu, 2023-11-18 10:47:06
 */
public interface CommonService {
    /**
     * 统一时间字段
     */
    @Nonnull
    Date nowDate();
}
