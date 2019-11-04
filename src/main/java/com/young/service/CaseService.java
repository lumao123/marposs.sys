package com.young.service;

import com.young.Pager;
import com.young.domain.KsCase;

public interface CaseService {
    public int saveCase(KsCase c);

    public KsCase getCaseById(final Long id);

    public Pager<KsCase> getCaseListForPage(
            final String companyName,
            final String dept,
            final String mobile,
            final Integer status,
            final Long userID,
            final String name,
            final int currentPage,
            final int pageSize
    );
}
