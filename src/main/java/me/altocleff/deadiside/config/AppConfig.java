package me.altocleff.deadiside.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.altocleff.deadiside.util.DiceCollection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;

@Configuration
public class AppConfig {

    @Bean
    public List<DiceCollection> diceCollections(ObjectMapper objectMapper) throws IOException {
        ClassPathResource resource = new ClassPathResource("dice.json");
        return objectMapper.readValue(resource.getInputStream(), new TypeReference<>() {});
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
