package xyz.kuilei.spring.demo.common.config;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.extension.incrementer.OracleKeyGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor demoMybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 分页拦截器
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        // Optimistic Lock Light version
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        // 攻击 SQL 阻断解析器,防止全表更新与删除
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());

        return interceptor;
    }

    /**
     * @see KeySequence
     */
    @Bean
    public IKeyGenerator demoIKeyGenerator() {
        return new OracleKeyGenerator();
    }
}
