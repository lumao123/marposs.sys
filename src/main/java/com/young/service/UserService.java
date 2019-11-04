package com.young.service;

import com.young.domain.KsUser;

import java.util.List;

public interface UserService {
    public KsUser getUserByName(String userName);

    public int saveUser(String userName, String password);
}
