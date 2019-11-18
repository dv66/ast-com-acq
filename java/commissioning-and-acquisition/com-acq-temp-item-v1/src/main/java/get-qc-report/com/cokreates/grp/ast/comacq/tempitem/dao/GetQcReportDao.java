package com.cokreates.grp.ast.comacq.tempitem.dao;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.tempitem.response.GetQcReportResponseBody;
import com.cokreates.grp.ast.comacq.tempitem.util.GetQcReportQuery;
import com.cokreates.grp.ast.comacq.tempitem.util.GetQcReportRowMapper;
import com.cokreates.grp.model.AuthUser;

@Repository("comacqTempitemV1GetQcReportDao")
public class GetQcReportDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Transactional(readOnly=true)
    public GetQcReportResponseBody getTemporaryItemByOid(AuthUser user,String oid) {
    	ImmutablePair<String, Object[]> query = GetQcReportQuery.getTemporaryItemSql(user, oid);
        return jdbcTemplate.queryForObject(query.getLeft(), query.getRight(), new GetQcReportRowMapper());
    }

}
