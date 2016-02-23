package cn.dishui.inter;

import java.util.List;

import org.aeonbits.owner.Config.*;
import org.aeonbits.owner.Config;

@Sources({"classpath:generator.properties" })
public interface GeneratorConfig extends Config{
	@Key("gen.class.dest")
    String class_dest();
	@Key("gen.xml.dest")
	String xml_dest();
	@Key("gen.ftls")
	List<String> ftls();
	@Key("gen.table.name")
	String name();//表名
	@Key("gen.table.fields")
	List<String> fields();//表字段
	
}
