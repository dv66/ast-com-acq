package com.cokreates.grp.ast.comacq.tempitem.dao;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.tempitem.model.ReQc;
import com.cokreates.grp.ast.comacq.tempitem.util.ReQcQuery;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.util.IdGenerator;

@Repository("comacqTempitemV1ReQcDao")
public class ReQcDao {

	@Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("idGenerator")
    private IdGenerator idGenerator;
    
    @Transactional(readOnly=true)
    public boolean getTemporaryItemStatus(AuthUser user, String oid) {
    	ImmutablePair<String, Object[]> query = ReQcQuery.getTemporaryItemStatusSql(user, oid);
    	long count = jdbcTemplate.queryForObject(query.getLeft(), query.getRight(), Long.class);
//        return jdbcTemplate.queryForObject(query.getLeft(), query.getRight(), new ReQcRowMapper());
    	
    	if(count == 1) {
    		return true;
    	}
    	
    	return false;
    }

    @Transactional
    public void updateTemporaryItem(AuthUser user, ReQc body) throws Exception {
    	ImmutablePair<String, Object[]> query = ReQcQuery.updateTemporaryItemSql(user, body);
        int result = jdbcTemplate.update(query.getLeft(), query.getRight());
        if(result != 1) {
        	throw new Exception("No record updated into Temporary Item!!!");
        }
    }

}
