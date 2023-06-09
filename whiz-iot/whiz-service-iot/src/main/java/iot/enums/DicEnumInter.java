package iot.enums;

/**
 * @author zhangpeng
 * @title: DicEnumInter
 * @date 2020-02-139:53
 */
public interface DicEnumInter {
    /**
     * 版本号 版本号高于数据库存储，则修改数据库的值，如果不高于 被重载值
     *
     * @return
     */
    Integer getVersion();

    /**
     * 中文描述
     *
     * @return
     */
    String getDesc();


    /**
     * 通过反射 设置值
     */
    void setVal(String val);

    void setDesc(String desc);

    /**
     * 获取值
     *
     * @return
     */
    String getVal();

    /**
     * 获取字典值的int型
     *
     * @return
     */
    default Integer getIntVal() {
        return Integer.valueOf(this.getVal());
    }
}
