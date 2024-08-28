package com.zzx.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Category {
    //分组校验
    @NotNull(groups = Update.class)
    private Integer id;//主键ID
//    @NotEmpty(groups = {Add.class,Update.class})
    @NotEmpty
    private String categoryName;//分类名称
//    @NotEmpty(groups = {Add.class,Update.class})
    @NotEmpty
    private String categoryAlias;//分类别名
    private Integer createUser;//创建人ID
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;//更新时间
    //继承默认的校验规则，就是初始的校验，有独特的就单独加新的对应操作的字节码对象
    public interface Add extends Default {

    }

    public interface Update extends Default{

    }
}
