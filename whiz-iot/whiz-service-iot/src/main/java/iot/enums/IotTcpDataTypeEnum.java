package iot.enums;


import org.springframework.util.StringUtils;

import java.util.Objects;


public enum IotTcpDataTypeEnum implements DicEnumInter {
    /**
     * 有符号 +-
     */
    LONG("Long", "Long"),
    /**
     * 无符号  的正负
     */
    ULONG("Ulong", "Ulong"),
    STRING("String", "String"),
    LIST("List","List"),
    DOUBLE("Double","Double"),
    Boolean("Boolean","Boolean"),
        ;

    IotTcpDataTypeEnum(String val, String desc) {

        this.desc = desc;
        this.val = val;
    }

    private String val;
    private String desc;

    @Override
    public Integer getVersion() {
        return 1;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }


    @Override
    public void setVal(String val) {
        this.val = val;
    }

    @Override
    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String getVal() {
        return this.val;
    }

    public static IotTcpDataTypeEnum getEnum(String val) {
        if (!StringUtils.isEmpty(val)) {
            for (IotTcpDataTypeEnum item : IotTcpDataTypeEnum.values()) {
                if (Objects.equals(item.val, val)) {
                    return item;
                }
            }
        }
        return null;
    }


}
