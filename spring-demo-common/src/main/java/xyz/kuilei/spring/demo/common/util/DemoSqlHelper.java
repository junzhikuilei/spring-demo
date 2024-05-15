package xyz.kuilei.spring.demo.common.util;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.apache.ibatis.executor.BatchExecutor;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.SqlSession;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author JiaKun Xu, 2023-11-09 10:18:14
 */
public class DemoSqlHelper {
    private static final class UpdateCountAccumulator implements Consumer<List<BatchResult>> {
        int affected = 0;

        @Override
        public void accept(@Nullable List<BatchResult> batchResults) {
            if (CollectionUtils.isEmpty(batchResults)) {
                return;
            }

            for (BatchResult result : batchResults) {
                for (int count : result.getUpdateCounts()) {
                    affected += count;
                }
            }
        }
    }

    /**
     * WARN:
     * 批量 {@link SqlSession} 在执行方法时，默认返回 {@link BatchExecutor#BATCH_UPDATE_RETURN_VALUE}。
     * 因为在这个时候 sql 语句还没执行，只有在 {@link SqlSession#flushStatements()} 时，sql 语句才会执行。
     * 因此，编写此方法以获取影响的记录条数。
     *
     * @see SqlHelper#executeBatch(Class, Log, Collection, int, BiConsumer)
     * @see BatchExecutor#doUpdate(MappedStatement, Object)
     *
     * @return sum of update counts
     */
    public static <E> int executeBatch(@Nonnull final Class<?> entityClass,
                                       @Nonnull final Log log,
                                       @Nonnull final Collection<E> list,
                                       final int batchSize,
                                       @Nonnull final BiConsumer<SqlSession, E> consumer) {
        Assert.isFalse(batchSize < 1, "batchSize must not be less than one");

        if (list.isEmpty()) {
            return 0;
        }

        UpdateCountAccumulator accumulator = new UpdateCountAccumulator();

        SqlHelper.executeBatch(entityClass, log, sqlSession -> {
            int size = list.size();
            int i = 1;
            for (E element : list) {
                consumer.accept(sqlSession, element);
                if ((i % batchSize == 0) || i == size) {
                    accumulator.accept(sqlSession.flushStatements());
                }
                ++i;
            }
        });

        return accumulator.affected;
    }
}
