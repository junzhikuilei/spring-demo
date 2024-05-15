package xyz.kuilei.spring.demo.common.bean;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * 分页数据
 */
@Getter
@Setter
@ToString
public class PageBean<T> {
    @ApiModelProperty(value = "当前页")
    long pageNo;

    @ApiModelProperty(value = "每页显示条数")
    long pageSize;

    @ApiModelProperty(value = "总条数")
    long totalCount;

    @ApiModelProperty(value = "总页数")
    long totalPages;

    @ApiModelProperty(value = "数据列表", dataType = "T")
    List<T> data;

    public PageBean(@Nonnull Page<T> page) {
        pageNo = page.getCurrent();
        pageSize = page.getSize();
        totalCount = page.getTotal();
        totalPages = page.getPages();
        data = page.getRecords();
    }
}
