package com.young.service;

import com.young.dao.CommonDaoImpl;
import com.young.domain.KsUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private CommonDaoImpl commonDao;

    @Override
    public KsUser getUserByName(String userName) {
        if (null == userName || "".equals(userName)) return null;
        KsUser user = null;
        try {
            user = (KsUser) commonDao.findUniqueByProperty(KsUser.class, "loginName", userName);
        } catch (Exception e) {
            logger.error("查询用户失败{}", userName);
            return null;
        }
        return user;
    }

    @Override
    public int saveUser(String userName, String password) {
        KsUser ksUser = new KsUser();
        ksUser.setLoginName(userName);
        ksUser.setLoginPwd(password);
        ksUser.setRole(0);
        commonDao.saveOrUpdate(ksUser);
        return 1;
    }
}
