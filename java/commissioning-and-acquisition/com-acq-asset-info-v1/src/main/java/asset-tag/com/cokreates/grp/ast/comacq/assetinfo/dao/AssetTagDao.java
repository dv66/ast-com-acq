package com.cokreates.grp.ast.comacq.assetinfo.dao;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.assetinfo.response.GetByOidAssetTagResponseBody;
import com.cokreates.grp.ast.comacq.assetinfo.util.AssetTagQuery;
import com.cokreates.grp.ast.comacq.assetinfo.util.GetByOidAssetTagRowMapper;
import com.cokreates.grp.model.AuthUser;

@Repository("comacqAssetInfoV1AssetTagDao")
public class AssetTagDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional(readOnly=true)
    public GetByOidAssetTagResponseBody getAssetStatusByOid(String oid) {
    	ImmutablePair<String, Object[]> query = AssetTagQuery.getAssetStatusSql(oid);
        return jdbcTemplate.queryForObject(query.getLeft(), query.getRight(), new GetByOidAssetTagRowMapper());
    }

	@Transactional
	public void saveAssetTaggedInfo(AuthUser user,String oid) throws Exception {
	
			ImmutablePair<String, Object[]> query = AssetTagQuery.assetTagSql(user, oid);
			int result = jdbcTemplate.update(query.getLeft(), query.getRight());
			if (result != 1) {
				throw new Exception("No record saved as available!!!");
			}
		}
	
	@Transactional(readOnly=true)
    public List<GetByOidAssetTagResponseBody> getAssetStatusAfterAssetTag(String oid) {
    	ImmutablePair<String, Object[]> query = AssetTagQuery.getAssetStatusAfterTagSql(oid);
        return jdbcTemplate.query(query.getLeft(), query.getRight(), new GetByOidAssetTagRowMapper());
    }
	
	@Transactional
	public void updateTempItemStatusAfterAssetTagSql(String oid) throws Exception {
	
			ImmutablePair<String, Object[]> query = AssetTagQuery.temporaryItemUpdatedAfterAssetTagSql(oid);
			int result = jdbcTemplate.update(query.getLeft(), query.getRight());
			if (result != 1) {
				throw new Exception("No record saved as available!!!");
			}
		}
	
	
	}


