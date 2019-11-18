package com.cokreates.grp.ast.comacq.tempitem.dao;

import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.tempitem.model.TempItemDataEntryOperator;
import com.cokreates.grp.ast.comacq.tempitem.request.GetListRequestDataEntryOperator;
import com.cokreates.grp.ast.comacq.tempitem.util.GetListQueryDataEntryOperator;
import com.cokreates.grp.ast.comacq.tempitem.util.GetListRowMapperDataEntryOperator;
import com.cokreates.grp.model.AuthUser;

@Repository("comAcqTempItemV1GetListDaoDataEntryOperator")
public class GetListDaoDataEntryOperator {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    
    @Transactional
    public List<TempItemDataEntryOperator> findAll(AuthUser user,GetListRequestDataEntryOperator request) {
        ImmutablePair<String, Object[]> query = GetListQueryDataEntryOperator.getTemporaryItemSql(user, request);
        List<TempItemDataEntryOperator> result = jdbcTemplate.query(query.getLeft(), query.getRight(), new GetListRowMapperDataEntryOperator());
        return ListUtils.emptyIfNull(result);
    }
    @Transactional
    public int getDataCount(AuthUser user,GetListRequestDataEntryOperator request) {
        ImmutablePair<String, Object[]> query = GetListQueryDataEntryOperator.getDataCountSql(user, request);
        int count = jdbcTemplate.queryForObject(query.getKey(), query.getValue(), Integer.class);
        return count;
    }

}
