package com.young.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository(value = "commonDao")
public class CommonDaoImpl<T> extends BaseDaoImpl {

    private SessionFactory commonSessionFactory;

    public SessionFactory getCommonSessionFactory() {
        return commonSessionFactory;
    }

    @Autowired
    public void setSessionFactoryOverride(SessionFactory commonSessionFactory) {
        super.setSessionFactory(commonSessionFactory);
    }

    private Session getCurrentSession() {
        return this.commonSessionFactory.getCurrentSession();
    }
}
