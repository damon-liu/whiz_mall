package iot.enums;

import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * bigint -> Long -> Int64
 * date,datetime -> Date -> Date
 * decimal -> BigDecimal -> Decimal
 * tinyint,int -> Integer -> Int32
 * double -> Double -> Double
 * varchar -> String -> String
 * List -> text -> Array 特殊处理
 */

public enum TslDbTypeEnum implements DicEnumInter {
    BIGINT("bigint", "bigint"),
    INT("int", "int"),
    DOUBLE("double", "double"),
    TINYINT("tinyint", "tinyint"),
    DECIMAL("decimal", "decimal"),
    VARCHAR("varchar", "varchar"),
    DATE("date", "date"),
    DATETIME("datetime", "datetime"),
    TEXT("text", "text"),
    LIST("List", "Array")
    ;

    TslDbTypeEnum(String val, String desc) {
        this.desc = desc;
        this.val = val;
    }

    private String val;
    private String desc;

    @Override
    public Integer getVersion() {
        return 3;
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

    public static TslDbTypeEnum getEnum(String val) {
        if (!StringUtils.isEmpty(val)) {
            for (TslDbTypeEnum item : TslDbTypeEnum.values()) {
                if (Objects.equals(item.val, val)) {
                    return item;
                }
            }
        }
        return null;
    }
}
