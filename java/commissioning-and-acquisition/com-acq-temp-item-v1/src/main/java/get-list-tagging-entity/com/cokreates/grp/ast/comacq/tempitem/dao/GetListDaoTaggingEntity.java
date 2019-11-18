package com.cokreates.grp.ast.comacq.tempitem.dao;

import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.tempitem.model.TempItemTaggingEntity;
import com.cokreates.grp.ast.comacq.tempitem.request.GetListRequestTaggingEntity;
import com.cokreates.grp.ast.comacq.tempitem.util.GetListQueryTaggingEntity;
import com.cokreates.grp.ast.comacq.tempitem.util.GetListRowMapperTaggingEntity;
import com.cokreates.grp.client.AuthWsClient;
import com.cokreates.grp.model.EmployeeResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository("comAcqTempItemV1GetListDaoTaggingEntity")
public class GetListDaoTaggingEntity {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AuthWsClient authWsClient;
    
    @Transactional
    public List<TempItemTaggingEntity> findAll(GetListRequestTaggingEntity request, String officeOid) {
        ImmutablePair<String, Object[]> query = GetListQueryTaggingEntity.getTemporaryItemSql(request, officeOid);
        List<TempItemTaggingEntity> result = jdbcTemplate.query(query.getLeft(), query.getRight(), new GetListRowMapperTaggingEntity());
        for(int i=0; i<result.size(); i++) {
            try {
                JSONObject j = new JSONObject();
                j.put("userOid", result.get(i).getReceivedBy());
                EmployeeResponse response = authWsClient.getUsername((j.toString()));
                log.info("ReceivedBy name response : {}", response);
                if(response != null && response.getStatus() == 200) {
                    result.get(i).setReceivedByEn(response.getData().getNameEn());
                    result.get(i).setReceivedByBn(response.getData().getNameBn());
                }
            } catch (Exception e) {
                log.error("An exception occurred while getting receivedBy name  : ", e);
            }
       }
        return ListUtils.emptyIfNull(result);
    }
    
    @Transactional
    public int getDataCount(String officeOid, GetListRequestTaggingEntity request) {
        ImmutablePair<String, Object[]> query = GetListQueryTaggingEntity.getDataCountSql(request, officeOid);
        int count = jdbcTemplate.queryForObject(query.getKey(), query.getValue(), Integer.class);
        return count;
    }

}
