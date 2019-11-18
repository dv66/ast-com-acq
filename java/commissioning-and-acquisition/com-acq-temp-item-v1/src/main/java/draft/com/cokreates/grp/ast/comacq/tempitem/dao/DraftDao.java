package com.cokreates.grp.ast.comacq.tempitem.dao;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.tempitem.model.DraftTempItemDetail;
import com.cokreates.grp.ast.comacq.tempitem.request.DraftRequest;
import com.cokreates.grp.ast.comacq.tempitem.util.DraftQuery;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.util.IdGenerator;

@Repository("comAcqTempItemV1DraftDao")
public class DraftDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("idGenerator")
    private IdGenerator idGenerator;

    @Transactional
    public int draftTemporaryItem(AuthUser user, DraftRequest request) throws Exception {
    	String tempItemOid = idGenerator.generateId();
    	ImmutablePair<String, Object[]> query1 = DraftQuery.draftTemporaryItemSql(user, request, tempItemOid);
    	int result1 = jdbcTemplate.update(query1.getLeft(), query1.getRight());
    	
        if(result1 != 1) {
        	throw new Exception("No record updated into Temporary Item!!!");
        }
        for(DraftTempItemDetail ti: request.getBody().getData().getDraftTempItemDetail()) {
        	ImmutablePair<String, Object[]> query2 = DraftQuery.draftTempItemDetailSql(ti, idGenerator.generateId(), tempItemOid);
            int result2 = jdbcTemplate.update(query2.getLeft(), query2.getRight());
            if(result2 != 1) {
            	throw new Exception("Invalid temporary item detail!!!");
            }
        }
        
        return result1;
    }

    

}
