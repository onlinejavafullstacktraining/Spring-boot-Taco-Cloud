package com.springboot.tacocloud;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.RandomAccess;

@Repository
public class JdbcTacoRepository implements TacoRepository {
    private JdbcTemplate jdbc;
    public JdbcTacoRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
    @Override
    public Taco save(Taco taco) {
        long tacoId = saveTacoInfo(taco);
        taco.setId(tacoId);
        for (String ingredient : taco.getIngredients()) {
            saveIngredientToTaco(ingredient, tacoId);
        }
        return taco;
    }
    private long saveTacoInfo(Taco taco) {
        taco.setCreatedAt(new Date());
        Random rnd = new Random();
        long number = rnd.nextInt(9999);
        taco.setId(number);
        jdbc.update("insert into taco  values (?, ?,?)", taco.getId(), taco.getName(),taco.getCreatedAt());
        return taco.getId();
    }
    private void saveIngredientToTaco(
            String ingredient, long tacoId) {
        jdbc.update(
                "insert into Taco_Ingredients (taco, ingredient) " +
                        "values (?, ?)",
                tacoId, ingredient);
    }
}

