package money.jupiter.employeemanagement.config;
import money.jupiter.employeemanagement.models.EmployeeData;
import money.jupiter.employeemanagement.models.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
public class RedisConfig{



    @Bean
    public RedisTemplate<String, Student> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Student> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Student.class));
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Student.class));
        return template;
    }

    @Bean
    public RedisTemplate<String, EmployeeData> redisTemplate1 (RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, EmployeeData> template1 = new RedisTemplate<>();
        template1.setConnectionFactory(redisConnectionFactory);
        template1.setKeySerializer(new StringRedisSerializer());
        template1.setHashKeySerializer(new StringRedisSerializer());
        template1.setValueSerializer(new Jackson2JsonRedisSerializer<>(EmployeeData.class));
        template1.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(EmployeeData.class));
        return template1;
    }




}
