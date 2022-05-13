package ru.netology.dao.repository;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * PersonRepository
 *
 * @author Viktor_Loskutov
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Repository
public class PersonRepository {

    private static final String SELECT_PRODUCT_BY_NAME_QUERY = read("/selectProducts.sql");

    NamedParameterJdbcTemplate template;

    public String getProductName(String name) {
        var result = template.queryForList(SELECT_PRODUCT_BY_NAME_QUERY, Map.of("name", name), String.class);
        return result.isEmpty() ? "Not found" : String.join(", ", result);
    }

    private static String read(String scriptFileName) {
        try (InputStream is = new ClassPathResource(scriptFileName).getInputStream();
             var bufferedReader = new BufferedReader(new InputStreamReader(is))) {
            return bufferedReader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}