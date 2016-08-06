package app.${table};

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import app.Result;
import app.product.service.ProductService;
import app.${table}.bean.${table?cap_first}Bean;
import app.${table}.service.${table?cap_first}Service;
import app.utils.exceptions.AppErrorException;

@Path("${table}")
@Produces("application/json;charset=UTF-8")
@Component
public class ${table?cap_first}Resource {
	private static final Logger LOGGER = Logger.getLogger(${table?cap_first}Service.class);
	@Resource
	private ${table?cap_first}Service ${table}Service;

    @GET @Path("get/{id}")
    public Result get${table?cap_first}ById(@PathParam("id") String ${table}Id) throws AppErrorException {
    	
    	Result result = new Result();
    	
    	
    	return result;
    }
    
    
    
}
