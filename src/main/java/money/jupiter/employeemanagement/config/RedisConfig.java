package money.jupiter.employeemanagement.config;

import money.jupiter.employeemanagement.models.Employee;
import money.jupiter.employeemanagement.models.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Student> redisTemplateStudent(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Student> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Student.class));
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Student.class));
        return template;
    }

    @Bean
    public RedisTemplate<String, Employee> redisTemplateEmployee(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Employee> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Employee.class));
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Employee.class));
        return template;
    }
}
