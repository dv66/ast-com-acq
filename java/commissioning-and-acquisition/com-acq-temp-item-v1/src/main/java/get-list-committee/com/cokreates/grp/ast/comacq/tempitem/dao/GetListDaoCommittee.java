package com.cokreates.grp.ast.comacq.tempitem.dao;

import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cokreates.grp.ast.comacq.tempitem.model.TempItemCommittee;
import com.cokreates.grp.ast.comacq.tempitem.request.GetListRequestCommittee;
import com.cokreates.grp.ast.comacq.tempitem.util.GetListQueryCommittee;
import com.cokreates.grp.ast.comacq.tempitem.util.GetListRowMapperCommittee;
import com.cokreates.grp.client.AuthWsClient;
import com.cokreates.grp.model.AuthUser;
import com.cokreates.grp.model.EmployeeResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository("comAcqTempItemV1GetListDaoCommittee")
public class GetListDaoCommittee {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
       private AuthWsClient authWsClient;
    
    @Transactional
    public List<TempItemCommittee> findAll(GetListRequestCommittee request, AuthUser user) {
        ImmutablePair<String, Object[]> query = GetListQueryCommittee.getTemporaryItemSql(request, user);
        List<TempItemCommittee> result = jdbcTemplate.query(query.getLeft(), query.getRight(), new GetListRowMapperCommittee());
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
    public int getCountDataSql(GetListRequestCommittee request, String officeOid) {
        ImmutablePair<String, Object[]> query = GetListQueryCommittee.getCountDataSql(request, officeOid);
        int count = jdbcTemplate.queryForObject(query.getKey(), query.getValue(), Integer.class);
        return count;
    }

}
