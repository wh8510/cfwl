package org.example.cfwl.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.elasticsearch.config.ElasticsearchConfigurationSupport;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;

@Configuration
public class ElasticsearchConverterConfig extends ElasticsearchConfigurationSupport {

    // 定义日期格式（与 ES 中的日期格式完全一致）
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // 修复核心：用匿名内部类替代 Lambda，明确泛型类型
    @NotNull
    @Bean
    @Override
    public ElasticsearchCustomConversions elasticsearchCustomConversions() {
        // 匿名内部类形式定义转换器（明确 Converter<String, LocalDateTime> 泛型）
        Converter<String, LocalDateTime> stringToLocalDateTimeConverter = new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(String source) {
                if (source.trim().isEmpty()) {
                    return null;
                }
                try {
                    // 按指定格式解析日期字符串
                    return LocalDateTime.parse(source.trim(), DATE_TIME_FORMATTER);
                } catch (Exception e) {
                    throw new IllegalArgumentException(
                            "日期格式解析失败！预期格式：yyyy-MM-dd HH:mm:ss，实际值：" + source, e
                    );
                }
            }
        };

        // 注册转换器（如果需要反向转换 LocalDateTime → String，可添加对应转换器）
        return new ElasticsearchCustomConversions(
                Collections.singletonList(stringToLocalDateTimeConverter)
        );
    }
}