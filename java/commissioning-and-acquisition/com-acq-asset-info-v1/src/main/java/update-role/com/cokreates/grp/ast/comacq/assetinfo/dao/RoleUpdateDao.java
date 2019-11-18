package com.cokreates.grp.ast.comacq.assetinfo.dao;

import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.assetinfo.model.RoleDetailsUpdate;
import com.cokreates.grp.ast.comacq.assetinfo.model.RoleUpdate;
import com.cokreates.grp.ast.comacq.assetinfo.util.RoleUpdateQuery;
import com.cokreates.grp.ast.comacq.assetinfo.util.RoleUpdateRowMapper;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.util.IdGenerator;

@Repository("comacqAssetInfoV1RoleUpdateDao")
public class RoleUpdateDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
    @Qualifier("idGenerator")
    private IdGenerator idGenerator;

	@Transactional
    public List<RoleDetailsUpdate> getRoles(String oid) {
    	ImmutablePair<String, Object[]> query = RoleUpdateQuery.getEmployeeRoleSql(oid);
        List<RoleDetailsUpdate> result = jdbcTemplate.query(query.getLeft(), query.getRight(), new RoleUpdateRowMapper());
        return ListUtils.emptyIfNull(result);
    }
	
	@Transactional
    public void saveRoleUpdate(AuthUser user, RoleUpdate roleUpdate, List<RoleDetailsUpdate> roleList) throws Exception {
		
		for(RoleDetailsUpdate rdl: roleList) {
			boolean hasRole = false;
			for(RoleDetailsUpdate rdu: roleUpdate.getRoleDetails()) {
				if(rdl.getGrpRoleOid().equals(rdu.getGrpRoleOid())) {
					hasRole = true;
					break;
				}
			}
			if(hasRole == false) {
				ImmutablePair<String, Object[]> query = RoleUpdateQuery.updateEmployeeRole(user, roleUpdate, rdl);
	            int result = jdbcTemplate.update(query.getLeft(), query.getRight());
	            if(result != 1) {
	            	throw new Exception("Record not updated!!!");
	            }
			}
		}
		
		for(RoleDetailsUpdate rdu: roleUpdate.getRoleDetails()) {
			boolean hasNotRole = true;		
			for(RoleDetailsUpdate rdl: roleList) {					
				if(rdu.getGrpRoleOid().equals(rdl.getGrpRoleOid())) {
					hasNotRole = false;
					break;
				}
			}
			if(hasNotRole == true) {
				ImmutablePair<String, Object[]> query = RoleUpdateQuery.saveRoleInfoSql(user, idGenerator.generateId(), roleUpdate.getGrpUserOid(), rdu);
	            int result = jdbcTemplate.update(query.getLeft(), query.getRight());
	            if(result != 1) {
	            	throw new Exception("Record not insert!!!");
	            }
			}
		}
    }

}
