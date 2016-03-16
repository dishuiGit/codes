package cn.dishui.inter;

import java.util.List;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

@Sources({"classpath:generator.properties"})
public interface GeneratorConfig extends Config{
	@Key("gen.class.dest")
    String class_dest();
	@Key("gen.xml.dest")
	String xml_dest();
	@Key("gen.ftl.folder")
	String ftl_floder();
	@Key("gen.f")
	String floder();
	@Key("gen.ftls")
	List<String> ftls();
	@Key("gen.table.name")
	String name();//表名
	@Key("gen.table.name.alias")
	String alias();//表别名
	@Key("gen.table.fields")
	List<String> fields();//表字段
	@Key("gen.generated.files")
	List<String> files();//生成的文件
	@Key("gen.packages")
	List<String> packages();//包名
	
}
