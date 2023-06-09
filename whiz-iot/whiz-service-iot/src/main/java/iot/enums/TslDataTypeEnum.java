package iot.enums;


import org.springframework.util.StringUtils;

import java.util.Objects;

public enum TslDataTypeEnum implements DicEnumInter {
    LONG("Long", "Long"),
    INTEGER("Integer", "Integer"),
    DOUBLE("Double", "Double"),
    ENUM("Enum", "Enum"),
    BIG_DECIMAL("BigDecimal", "BigDecimal"),
    BOOLEAN("Boolean", "Boolean"),
    STRING("String", "String"),
    DATE("Date", "Date"),
    MAP("Map", "Map"),
    LIST("List", "List"),
    ;

    TslDataTypeEnum(String val, String desc) {
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

    public static TslDataTypeEnum getEnum(String val) {
        if (!StringUtils.isEmpty(val)) {
            for (TslDataTypeEnum item : TslDataTypeEnum.values()) {
                if (Objects.equals(item.val, val)) {
                    return item;
                }
            }
        }
        return null;
    }
}
