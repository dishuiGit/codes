package app.${table}.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.bizoss.frame.util.ObjUtils;

import app.${table}.bean.${table?cap_first}Bean;
import app.utils.BeanCopy;
import app.utils.exceptions.AppErrorException;
@Service
public class ${table?cap_first}Service {
	private static final Logger LOGGER = Logger.getLogger(${table?cap_first}Service.class);
}

