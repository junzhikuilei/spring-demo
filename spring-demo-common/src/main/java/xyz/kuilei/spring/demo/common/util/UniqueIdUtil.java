package xyz.kuilei.spring.demo.common.util;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;

import javax.annotation.Nonnull;

/**
 * @author JiaKun Xu, 2023-05-29 10:58
 */
public class UniqueIdUtil {
    @Nonnull
    public static String uniqueId() {
        return IdWorker.get32UUID();
    }
}
