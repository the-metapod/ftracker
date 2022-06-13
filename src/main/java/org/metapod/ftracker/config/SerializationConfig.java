package org.metapod.ftracker.config;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.metapod.ftracker.model.serialization.OffsetDateTimeDeserializer;
import org.metapod.ftracker.model.serialization.OffsetDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.OffsetDateTime;

@Configuration
public class SerializationConfig {
    @Bean
    public JavaTimeModule javaTimeModule() {
        JavaTimeModule module = new JavaTimeModule();
        module.addDeserializer(OffsetDateTime.class, new OffsetDateTimeDeserializer());
        module.addSerializer(OffsetDateTime.class, new OffsetDateTimeSerializer());
        return module;
    }
}
