package com.cokreates.grp.ast.comacq.tempitem.dao;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.tempitem.model.UpdateTempItem;
import com.cokreates.grp.ast.comacq.tempitem.model.UpdateTempItemDetail;
import com.cokreates.grp.ast.comacq.tempitem.response.GetByOidUpdateResponseBody;
import com.cokreates.grp.ast.comacq.tempitem.util.GetByOidTemItemUpdateRowMapper;
import com.cokreates.grp.ast.comacq.tempitem.util.UpdateQuery;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.util.IdGenerator;

@Repository("comacqTempitemV1UpdateDao")
public class UpdateDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("idGenerator")
    private IdGenerator idGenerator;
    
    @Transactional(readOnly=true)
    public GetByOidUpdateResponseBody getTemporaryItemByOid(String oid) {
    	ImmutablePair<String, Object[]> query = UpdateQuery.getTemporaryItemSql(oid);
        return jdbcTemplate.queryForObject(query.getLeft(), query.getRight(), new GetByOidTemItemUpdateRowMapper());
    }

    @Transactional
    public void updateTemporaryItemDetails(AuthUser user, UpdateTempItem body, List<UpdateTempItemDetail> tempItemDetails) throws Exception {
    	ImmutablePair<String, Object[]> query = UpdateQuery.updateTemporaryItemSql(user, body);
        int result = jdbcTemplate.update(query.getLeft(), query.getRight());
        if(result != 1) {
        	throw new Exception("No record updated into Temporary Item!!!");
        }
        
        query = UpdateQuery.deleteTemporaryItemDetailsSql(body.getOid());
        jdbcTemplate.update(query.getLeft(), query.getRight());
        
    	for(UpdateTempItemDetail ti : tempItemDetails) {
	    	ImmutablePair<String, Object[]> query2 = UpdateQuery.saveTempItemDetailSql(ti, idGenerator.generateId(), body.getOid());
	        result = jdbcTemplate.update(query2.getLeft(), query2.getRight());
	        if(result != 1) {
	        	throw new Exception("No record updated into Temporary Item Detail!!!");
	        }
    	}
    }

}
