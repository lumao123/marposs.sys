package com.young.service;

import com.young.Pager;
import com.young.dao.CommonDaoImpl;
import com.young.domain.KsCase;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.spi.TypedValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.management.relation.Relation;
import java.util.ArrayList;
import java.util.List;

@Service("caseService")
public class CaseServiceImpl implements CaseService {

    private static final Logger logger = LoggerFactory.getLogger(CaseServiceImpl.class);

    @Autowired
    private CommonDaoImpl commonDao;

    @Override
    public int saveCase(KsCase c) {
        try {
            commonDao.saveOrUpdate(c);
        } catch (Exception e) {
            logger.error("Save case error!");
            return 0;
        }
        return 1;
    }

    @Override
    public KsCase getCaseById(final Long id) {
        return (KsCase) commonDao.findUniqueByProperty(KsCase.class, "caseID", id);
    }

    @Override
    public Pager<KsCase> getCaseListForPage(final String companyName, final String dept, final String mobile,
                                            final Integer status, final Long userID, final String name, final int currentPage, final int pageSize) {
        Integer firstResult = (currentPage - 1) * pageSize;
        List<Criterion> criterionList = new ArrayList<Criterion>();
        if (!StringUtils.isEmpty(companyName)) {
            criterionList.add(Restrictions.like("companyName", "%" + companyName + "%", MatchMode.ANYWHERE));
        }
        if (!StringUtils.isEmpty(dept)) {
            criterionList.add(Restrictions.like("dept", "%" + dept + "%", MatchMode.ANYWHERE));
        }
        if (!StringUtils.isEmpty(mobile)) {
            criterionList.add(Restrictions.eq("mobile", mobile));
        }
        if (null != status) {
            criterionList.add(Restrictions.eq("status", status));
        }
        if (!StringUtils.isEmpty(userID)) {
            criterionList.add(Restrictions.eq("userID", userID));
        }
        if (!StringUtils.isEmpty(name)) {
            criterionList.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
        }
        Criterion[] criterions = new Criterion[criterionList.size()];
        for (int i = 0; i < criterionList.size(); i++) {
            criterions[i] = criterionList.get(i);
        }
        return commonDao.findForPager(KsCase.class, "insertTime", false, firstResult, pageSize, criterions);
    }

}
