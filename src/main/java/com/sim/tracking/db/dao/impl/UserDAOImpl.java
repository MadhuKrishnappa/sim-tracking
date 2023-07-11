package com.sim.tracking.db.dao.impl;

import com.sim.tracking.db.dao.IUserDAO;
import com.sim.tracking.db.entity.User;
import com.sim.tracking.model.request.SignUpRequest;
import com.sim.tracking.utilities.DatabaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.CollectionUtils;

import java.math.BigInteger;
import java.util.List;

@Repository
public class UserDAOImpl implements IUserDAO {


    @Autowired
    @Qualifier("mysql")
    NamedParameterJdbcTemplate jdbcTemplate;


    @Override
    public User getByUserNamePassword(String userName, String passwordHash) {

        String query = "select * from users where username = :userName and password = :passwordHash ";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("userName", userName);
        source.addValue("passwordHash", passwordHash);

        List<User> data = jdbcTemplate.query(query, source, new BeanPropertyRowMapper<>(User.class));

        if(CollectionUtils.isEmpty(data)){
            return null;
        }

        return data.get(0);

    }

    @Override
    public BigInteger signUp(User newUser) {

        String query = DatabaseUtil.getInsertQuery(User.class);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(query, DatabaseUtil.getParameterSource(newUser), keyHolder,
                new String[]{"id"});
        return new BigInteger(keyHolder.getKey().toString());
    }

}
