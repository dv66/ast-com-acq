package com.cokreates.grp.ast.comacq.assetinfo.dao;

import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.assetinfo.model.GetListRoleByOid;
import com.cokreates.grp.ast.comacq.assetinfo.request.GetListRoleByOidRequest;
import com.cokreates.grp.ast.comacq.assetinfo.util.GetListRoleByOidQuery;
import com.cokreates.grp.ast.comacq.assetinfo.util.GetListRoleByOidRowMapper;
import com.cokreates.grp.model.AuthUser;

@Repository("comAcqAssetInfoV1GetListRoleByOidDao")
public class GetListRoleByOidDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
//    @Transactional
//    public List<GetListRoleByOid> findAll(GetListRoleByOidRequest request, AuthUser user) {
//        ImmutablePair<String, Object[]> query = GetListRoleByOidQuery.getByOidExpertDecisionSql(request.getBody().getOid(), user);        
//        List< jdbcTemplate.queryForObject(query.getLeft(), query.getRight(), new GetListRoleByOidRowMapper());
//    }
    
    @Transactional
    public List<GetListRoleByOid> findAllDetails(GetListRoleByOidRequest request, AuthUser user) {
        ImmutablePair<String, Object[]> query = GetListRoleByOidQuery.getByOidExpertDecisionSql(request.getBody().getOid(), user);
        List<GetListRoleByOid> result = jdbcTemplate.query(query.getLeft(), query.getRight(), new GetListRoleByOidRowMapper());
        return ListUtils.emptyIfNull(result);
    }
    
    @Transactional
    public int getDataCount(GetListRoleByOidRequest request, AuthUser user) {
        ImmutablePair<String, Object[]> query = GetListRoleByOidQuery.getDataCountSql(request.getBody().getOid(), user);
        int count = jdbcTemplate.queryForObject(query.getKey(), query.getValue(), Integer.class);
        return count;
    }
    

}
