package digital.store.jdbctemplate.config;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Конфигурация подключения к БД через Spring JdbcTemplate.
 * Вся обработка ошибок сосредоточена здесь — в тесты и шаги исключения не прокидываются.
 * JdbcTemplate оборачивает все SQLException в DataAccessException (unchecked),
 * поэтому слой шагов работает без единого try-catch.
 */
public class DbConfig {

    private final JdbcTemplate jdbcTemplate;

    public DbConfig() {
        this.jdbcTemplate = buildJdbcTemplate(loadProperties());
    }

    public JdbcTemplate jdbcTemplate() {
        return jdbcTemplate;
    }

    private JdbcTemplate buildJdbcTemplate(Properties props) {
        try {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("org.postgresql.Driver");
            dataSource.setUrl(props.getProperty("db.url"));
            dataSource.setUsername(props.getProperty("db.user"));
            dataSource.setPassword(props.getProperty("db.password"));
            return new JdbcTemplate(dataSource);
        } catch (Exception e) {
            throw new RuntimeException("Не удалось создать JdbcTemplate [" + props.getProperty("db.url") + "]: " + e.getMessage(), e);
        }
    }

    private Properties loadProperties() {
        Properties props = new Properties();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            if (is == null) {
                throw new RuntimeException("Файл db.properties не найден в classpath");
            }
            props.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось загрузить db.properties: " + e.getMessage(), e);
        }
        return props;
    }
}
