package pagesite.core.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.jcr.JcrConstants;



@Component(name = "PageInfoService", service = PageInfoService.class, immediate = true)

public class PageInfoServiceImple implements PageInfoService {

	private ResourceResolver resourceresolver;

	@Reference
	private ResourceResolverFactory resourceresolverfactory;

	private static final Logger logger = LoggerFactory.getLogger(PageInfoServiceImple.class);

	@Override
	public Map<String, Object> getPageInfo() {

		Map<String, Object> pageInfo = null;

		pageInfo = new HashMap<String, Object>();

		Resource res = resourceresolver.getResource("/content/pagesite");

		pageInfo.put("primaryType", res.getValueMap().get(JcrConstants.JCR_PRIMARYTYPE, String.class));
		pageInfo.put("title", res.getName());
		pageInfo.put("path", res.getPath());

		pageInfo.put("isChildExists", res.hasChildren());

		if (res.hasChildren()) {
			Iterator<Resource> childResources = res.listChildren();

			List<String> childPageNames = new ArrayList<String>();

			while (childResources.hasNext()) {
				Resource childResource = childResources.next();

				childPageNames.add(childResource.getName());

			}
			pageInfo.put("childrenNames", childPageNames);
		}
		return pageInfo;
	}

	@Activate @Modified
	public void activate()  {
		final String SUBSERVICE = "rk";

		try {
			resourceresolver = getResolver(resourceresolverfactory, SUBSERVICE);
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info(": ACTIVATE THE RESOURCE RESOLVER :");
	}

	private ResourceResolver getResolver(ResourceResolverFactory resourceresolverfactory, String SUBSERVICE)
			throws LoginException {

		ResourceResolver resourceresolver = null;

		Map<String, Object> resolverPayload = new HashMap<String, Object>();
		resolverPayload.put(ResourceResolverFactory.SUBSERVICE, SUBSERVICE);

		resourceresolver = resourceresolverfactory.getServiceResourceResolver(resolverPayload);
		return resourceresolver;
	}

}

