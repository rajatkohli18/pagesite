package pagesite.core.services;


import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
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

import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
@Component(name = "CsvService", service = CsvService.class, immediate = true)
public class CsvServiceImple implements CsvService {

	@Reference
	private ResourceResolverFactory resourceResolverFactory;

	private static final Logger logger = LoggerFactory.getLogger(CsvServiceImple.class);

	public static final String SERVICE_NAME = "rk";

	public static final String RESOURCE_PATH = "/content/dam/pagesite/aempagesite/username.csv";
	
	ResourceResolver resourceResolver = null;
	String csvFile = null;
	
@Activate
@Modified
public void activate() {
	
		 logger.info("The control is coming inside the ResourceResolver and the bundle is activated!");
	        Map<String, Object> map = new HashMap<>();
	        map.put(ResourceResolverFactory.SUBSERVICE, SERVICE_NAME);
	        try {
	        	resourceResolver = resourceResolverFactory.getServiceResourceResolver(map);
	        	logger.info("Resource Resolver registered");
	        } catch (LoginException e) {
	            logger.error("Login Failed");
	        }
	    }

	@Override
	public String getAsset() {
		 try {
	            Resource resource = resourceResolver.getResource(RESOURCE_PATH);
	            logger.info("resource is coming");
	            Asset asset = resource.adaptTo(Asset.class);
	            Rendition rendition = asset.getOriginal();
	            InputStream inputStream = rendition.adaptTo(InputStream.class);
	            if (inputStream != null) {
	            	csvFile = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
	                return csvFile;
	            }
	            else {
	                return "We have failed";
	            }
		 } catch (Exception e) {
	            logger.error("Something went wrong in the get Asset function");
	        }
		 return "Nothing is happening";
    

		

	}
	 
	            
	    
	 }

	