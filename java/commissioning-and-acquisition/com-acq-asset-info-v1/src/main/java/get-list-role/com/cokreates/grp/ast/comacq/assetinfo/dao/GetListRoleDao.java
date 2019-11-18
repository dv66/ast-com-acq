package com.cokreates.grp.ast.comacq.assetinfo.dao;

import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.assetinfo.model.RoleList;
import com.cokreates.grp.ast.comacq.assetinfo.request.GetListRoleRequest;
import com.cokreates.grp.ast.comacq.assetinfo.util.GetListRoleQuery;
import com.cokreates.grp.ast.comacq.assetinfo.util.GetListRoleRowMapper;



@Repository("comAcqAssetInfoV1GetListRoleDao")
public class GetListRoleDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    
    @Transactional
    public List<RoleList> findAll(GetListRoleRequest request) {
        ImmutablePair<String, Object[]> query = GetListRoleQuery.getTemporaryItemSql(request);
        List<RoleList> result = jdbcTemplate.query(query.getLeft(), query.getRight(), new GetListRoleRowMapper());
        return ListUtils.emptyIfNull(result);
    }
    
    @Transactional
    public int getDataCount(GetListRoleRequest request) {
        ImmutablePair<String, Object[]> query = GetListRoleQuery.getDataCountSql(request);
        int count = jdbcTemplate.queryForObject(query.getKey(), query.getValue(), Integer.class);
        return count;
    }
    

}
