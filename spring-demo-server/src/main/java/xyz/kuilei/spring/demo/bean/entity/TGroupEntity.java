package xyz.kuilei.spring.demo.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.methods.Insert;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import lombok.*;
import xyz.kuilei.spring.demo.bean.vo.group.AddVo;
import xyz.kuilei.spring.demo.common.util.UniqueIdUtil;

import javax.annotation.Nonnull;
import java.util.Date;

/**
 * 群体
 *
 * @author JiaKun Xu, 2023-05-26 15:03
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@TableName("T_GROUP")
public class TGroupEntity {
    /**
     * WARN:
     * 如果字段名为 id，那么无论它是否为 null，baomidou 生成的 insert sql 都会强制带上这个字段。
     * \
     * 发现这个是因为以前在用 oracle 的 sequence.NEXTVAL 时，生成的 insert 语句是 insert (id, ...) values(null, ...)，
     * 导致 oracle 认为咱要 insert null 到主键。
     * NOTE:
     * 之所以 insert (id, ...) values(null, ...)，是因为以前在 spring 配置中配置了 id-type: UUID，
     * 而在 baomidou 中没有地方用到了 {@link IdType#UUID}，因为它过时了。
     * \
     * 现在知道 {@link KeySequence} 可以很容易地解决这个问题。
     *
     * 思维路径：
     * {@link SqlMethod#INSERT_ONE} ->
     * {@link SqlMethod#getSql} ->
     * {@link Insert#injectMappedStatement} ->
     * {@link TableInfo#getAllInsertSqlColumnMaybeIf} ->
     * {@link TableInfo#getKeyInsertSqlColumn}
     *
     * baomidou 的默认主键名：
     * {@link TableInfoHelper#DEFAULT_ID_NAME}
     */
    public static final String GROUP_ID = "GROUP_ID";
    public static final String NAME = "NAME";
    public static final String CREATE_TIME = "CREATE_TIME";
    public static final String UPDATE_TIME = "UPDATE_TIME";

    /**
     * 群体 id
     * NOTE: 出于 AOP 考量，不加 ApiModelProperty 注释。(JiaKun Xu, 2023-06-05 17:19)
     */
    private String groupId;
    /**
     * 名称
     */
    private String name;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;

    @Nonnull
    public static TGroupEntity fromAdd(@Nonnull AddVo vo, @Nonnull Date nowDate) {
        return new TGroupEntity(
                UniqueIdUtil.uniqueId(),
                vo.getName(),
                nowDate,
                null
        );
    }
}
