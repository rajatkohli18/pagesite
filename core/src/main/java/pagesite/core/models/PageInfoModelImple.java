package pagesite.core.models;

import java.util.Map;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

import pagesite.core.services.PageInfoService;



@Model(adaptables = SlingHttpServletRequest.class, adapters = PageInfoModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PageInfoModelImple implements PageInfoModel {

	@OSGiService
	PageInfoService service;
	
	@Override
	public Map<String, Object> getPageInfo() {

		return service.getPageInfo();
	}

}

