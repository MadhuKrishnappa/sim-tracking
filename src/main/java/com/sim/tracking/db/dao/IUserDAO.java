package com.sim.tracking.db.dao;

import com.sim.tracking.db.entity.User;

import java.math.BigInteger;

public interface IUserDAO {
    User getByUserNamePassword(String username, String passwordHash);

    BigInteger signUp(User newUser);
}
