package com.damon.comon.component.version;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/**
 * Description:定义版本匹配RequestCondition
 *
 * @author damon.liu
 * Date 2023-04-30 2:43
 */


@Data
@Slf4j
public class ApiVersionCondition implements RequestCondition<ApiVersionCondition> {

    /**
     * support v1.1.1, v1.1, v1; three levels .
     */
    // private static final Pattern VERSION_PREFIX_PATTERN_1 = Pattern.compile("/v\\d\\.\\d\\.\\d/");
    // private static final Pattern VERSION_PREFIX_PATTERN_2 = Pattern.compile("/v\\d\\.\\d/");
    // private static final Pattern VERSION_PREFIX_PATTERN_3 = Pattern.compile("/v\\d/");
    // private static final List<Pattern> VERSION_LIST = Collections.unmodifiableList(
    //         Arrays.asList(VERSION_PREFIX_PATTERN_1, VERSION_PREFIX_PATTERN_2, VERSION_PREFIX_PATTERN_3)
    // );

    /**
     * 接口路径中的版本号前缀，如: api/v[1-n]/test
     */
    private final static Pattern VERSION_PREFIX_PATTERN = Pattern.compile("/v(\\d+)/");
    private final static String VERSION_HEARDER = "version";

    private int apiVersion;

    ApiVersionCondition(int apiVersion) {
        this.apiVersion = apiVersion;
    }

    @Override
    public ApiVersionCondition combine(ApiVersionCondition other) {
        // 最近优先原则，方法定义的 @ApiVersion > 类定义的 @ApiVersion
        return new ApiVersionCondition(other.getApiVersion());
    }

    @Override
    public ApiVersionCondition getMatchingCondition(HttpServletRequest request) {
        String versionStr = request.getHeader(VERSION_HEARDER);
        if (StringUtils.isBlank(versionStr)) {
            return null;
        } else {
            int version = 0;
            try {
                version = Integer.parseInt(versionStr);
            } catch (NumberFormatException e) {
                log.warn("版本号必须为数字:{}", versionStr);
                return null;
            }
            // 版本号相等
            if (version >= getApiVersion()) {
                return this;
            }
            return null;
        }
    }

    @Override
    public int compareTo(ApiVersionCondition other, HttpServletRequest request) {
        // 当出现多个符合匹配条件的ApiVersionCondition，优先匹配版本号较大的
        return other.getApiVersion() - getApiVersion();
    }
}
