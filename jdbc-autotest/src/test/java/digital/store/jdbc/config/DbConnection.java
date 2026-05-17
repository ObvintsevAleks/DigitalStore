package digital.store.jdbc.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Properties;

public class DbConnection {

    private final String url;
    private final String user;
    private final String password;

    public DbConnection() {
        Properties props = loadProperties();
        this.url = props.getProperty("db.url");
        this.user = props.getProperty("db.user");
        this.password = props.getProperty("db.password");
    }

    public int executeUpdate(String sql, Object... params) {
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            applyParams(ps, params);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка executeUpdate [" + sql + "]: " + e.getMessage(), e);
        }
    }

    public <T> T executeQuery(String sql, ResultSetMapper<T> mapper, Object... params) {
        try (Connection conn = openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            applyParams(ps, params);
            try (ResultSet rs = ps.executeQuery()) {
                return mapper.map(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка executeQuery [" + sql + "]: " + e.getMessage(), e);
        }
    }

    private Connection openConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось подключиться к БД [" + url + "]: " + e.getMessage(), e);
        }
    }

    private void applyParams(PreparedStatement ps, Object[] params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            Object p = params[i];
            if (p instanceof LocalDate ld) {
                ps.setDate(i + 1, Date.valueOf(ld));
            } else if (p instanceof ZonedDateTime zdt) {
                ps.setObject(i + 1, zdt.toOffsetDateTime());
            } else {
                ps.setObject(i + 1, p);
            }
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

    @FunctionalInterface
    public interface ResultSetMapper<T> {
        T map(ResultSet rs) throws SQLException;
    }
}
