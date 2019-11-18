package com.cokreates.grp.ast.comacq.directin.dao;

import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.directin.model.DirectIn;
import com.cokreates.grp.ast.comacq.directin.request.GetListRequest;
import com.cokreates.grp.ast.comacq.directin.util.GetListQuery;
import com.cokreates.grp.ast.comacq.directin.util.GetListRowMapper;
import com.cokreates.grp.model.AuthUser;

@Repository("comAcqDirectInV1GetListEndUserDao")
public class GetListDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    
    @Transactional
    public List<DirectIn> findAll(GetListRequest request, AuthUser user) {
        ImmutablePair<String, Object[]> query = GetListQuery.getDirectInSql(request, user);
        List<DirectIn> result = jdbcTemplate.query(query.getLeft(), query.getRight(), new GetListRowMapper());
        return ListUtils.emptyIfNull(result);
    }
    
    @Transactional
    public int getDataCount(GetListRequest request, AuthUser user) {
        ImmutablePair<String, Object[]> query = GetListQuery.getDataCountSql(request, user);
        int count = jdbcTemplate.queryForObject(query.getKey(), query.getValue(), Integer.class);
        return count;
    }
    

}
