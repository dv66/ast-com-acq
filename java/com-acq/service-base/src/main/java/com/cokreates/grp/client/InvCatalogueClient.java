package com.cokreates.grp.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cokreates.grp.model.inv.InvItem;
import com.cokreates.grp.model.inv.InvItemCategory;
import com.cokreates.grp.model.inv.InvItemSetup;
import com.cokreates.grp.util.constant.Api;

@FeignClient(name = "${inv.catalogue.service.name}")
public interface InvCatalogueClient {
	
	@RequestMapping(method = RequestMethod.GET, value = Api.GET_ITEM_CATEGORY_LIST, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public List<InvItemCategory> getItemCategoryList(@RequestHeader("Authorization") String token);

	@RequestMapping(method = RequestMethod.GET, value = Api.GET_ITEM_SETUP_LIST, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public List<InvItemSetup> getItemSetupList(@RequestHeader("Authorization") String token);
//	public <T> Object getItemSetupList(@RequestHeader("Authorization") String token);

	@RequestMapping(method = RequestMethod.GET, value = Api.ITEM_GET_BY_OID, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public InvItem getItem(@RequestHeader("Authorization") String token, @PathVariable("id") String id);

	
}
