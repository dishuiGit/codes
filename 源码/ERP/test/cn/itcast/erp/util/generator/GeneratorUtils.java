package cn.itcast.erp.util.generator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import cn.itcast.erp.invoice.storeoper.vo.StoreOperModel;


public class GeneratorUtils {
	private Class clazz;
	private String b;
	private String l;
	private String s ;
	private String pkg;
	private String rootDir;
	
	public static void main(String[] args) throws IOException {
		//EmpModel,RoleModel,ResModel,MenuModel
		//SupplierModel,GoodsTypeModel,GoodsModel
		//OrderModel,OrderDetailModel
		//StoreModel,StoreDetailModel,StoreOperModel
		new GeneratorUtils(StoreOperModel.class);
		System.out.println("struts.xml:未添加Action定义");
		System.out.println("Hbm.xml:未设置关联关系");
		System.out.println("QueryModel:自定义查询条件未设置");
		System.out.println("DaoImpl:自定义查询条件未设置实现");
	}
	
	public GeneratorUtils(Class clazz) throws IOException{
		//生成所有代码
		this.clazz = clazz;
		//-1.数据初始化
		dataInit();
		//0.生成目录
		generatorDirectories();
		//1.QueryModel
		generatorQueryModel();
		//2.HbmXml
		generatorHbmXml();
		//3.Dao
		generatorDao();
		//4.Impl
		generatorImpl();
		//5.Ebi
		generatorEbi();
		//6.Ebo
		generatorEbo();
		//7.Action
		generatorAction();
		//8.ApplicationContextXml
		generatorApplicationContextXml();
		//9.struts.xml
		//modifyStrutXml();
	}
	//9.struts.xml
	private void modifyStrutXml() throws IOException {
		File f = new File("resources/struts.xml");
		File f2 = new File("resources/struts2.xml");
		
		BufferedReader br = new BufferedReader(new FileReader(f));
		BufferedWriter bw = new BufferedWriter(new FileWriter(f2));
		
		String msg = null;
		do{
			msg = br.readLine();
			if(msg != null){
				//判断读取的内容是不是包含</package>
				//写入特殊内容
				if(msg.contains("</package>")){
					bw.write("    	<!-- "+b+" -->");
					bw.newLine();
					bw.write("    	<action name=\""+s+"_*\" class=\""+s+"Action\" method=\"{1}\">");
					bw.newLine();
					bw.write("	   	</action>");
					bw.newLine();
					bw.newLine();
				}
				
				bw.write(msg);
				bw.newLine();
			}
		}while(msg != null);
		
		br.close();
		bw.flush();
		bw.close();
		
		//删除f
		f.delete();
		//修改f2为f
		f2.renameTo(f);
	}
	//8.ApplicationContextXml
	private void generatorApplicationContextXml() throws IOException {
		//1.创建文件
		File f = new File("resources/applicationContext-"+s+".xml");
		
		//保障不会进行误操作
		if(f.exists()){
			return;
		}
		
		f.createNewFile();
		//2.写文件
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		
		bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		bw.newLine();
		
		bw.write("<beans xmlns=\"http://www.springframework.org/schema/beans\"");
		bw.newLine();
		
		bw.write("	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" ");
		bw.newLine();
		
		bw.write("	xsi:schemaLocation=\"");
		bw.newLine();
		
		bw.write("		http://www.springframework.org/schema/beans ");
		bw.newLine();
		
		bw.write("		http://www.springframework.org/schema/beans/spring-beans.xsd");
		bw.newLine();
		
		bw.write("		\">");
		bw.newLine();
		
		bw.write("	<!-- Action -->");
		bw.newLine();
		
		bw.write("	<bean id=\""+s+"Action\" class=\""+pkg+".web."+b+"Action\" scope=\"prototype\">");
		bw.newLine();
		
		bw.write("		<property name=\""+s+"Ebi\" ref=\""+s+"Ebi\"/>");
		bw.newLine();
		
		bw.write("	</bean>");
		bw.newLine();
		
		bw.write("	<!-- Ebi -->");
		bw.newLine();
		
		bw.write("	<bean id=\""+s+"Ebi\" class=\""+pkg+".business.ebo."+b+"Ebo\">");
		bw.newLine();
		
		bw.write("		<property name=\""+s+"Dao\" ref=\""+s+"Dao\"/>");
		bw.newLine();
		
		bw.write("	</bean>");
		bw.newLine();
		
		bw.write("	<!-- Dao -->");
		bw.newLine();
		
		bw.write("	<bean id=\""+s+"Dao\" class=\""+pkg+".dao.impl."+b+"Impl\">");
		bw.newLine();
		
		bw.write("		<property name=\"sessionFactory\" ref=\"sessionFactory\"/>");
		bw.newLine();
		
		bw.write("	</bean>");
		bw.newLine();
		
		bw.write("</beans>");
		bw.newLine();
		
		//3.关闭
		bw.flush();
		bw.close();		
	}

	//7.Action
	private void generatorAction() throws IOException {
		//1.创建文件
		File f = new File("src/"+rootDir+"/web/"+b+"Action.java");
		
		//保障不会进行误操作
		if(f.exists()){
			return;
		}
		
		f.createNewFile();
		//2.写文件
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		
		bw.write("package "+pkg+".web;\r\n"+
"\r\n"+
"import java.util.List;\r\n"+
"\r\n"+
"import "+pkg+".business.ebi."+b+"Ebi;\r\n"+
"import "+pkg+".vo."+b+"Model;\r\n"+
"import "+pkg+".vo."+b+"QueryModel;\r\n"+
"import cn.itcast.erp.util.base.BaseAction;\r\n"+
"\r\n"+
"public class "+b+"Action extends BaseAction{\r\n"+
"	public "+b+"Model "+l+"m = new "+b+"Model();\r\n"+
"	public "+b+"QueryModel "+l+"qm = new "+b+"QueryModel();\r\n"+
"\r\n"+	
"	private "+b+"Ebi "+s+"Ebi;\r\n"+
"	public void set"+b+"Ebi("+b+"Ebi "+s+"Ebi) {\r\n"+
"		this."+s+"Ebi = "+s+"Ebi;\r\n"+
"	}\r\n"+
"\r\n"+	
"	//列表\r\n"+
"	public String list(){\r\n"+
"		setDataTotal("+s+"Ebi.getCount("+l+"qm));\r\n"+
"		List<"+b+"Model> "+s+"List = "+s+"Ebi.getAll("+l+"qm,pageNum,pageCount);\r\n"+
"		put(\""+s+"List\", "+s+"List);\r\n"+
"		return LIST;\r\n"+
"	}\r\n"+
"\r\n"+	
"	//跳转到添加/修改\r\n"+
"	public String input(){\r\n"+
"		if("+l+"m.getUuid()!=null){\r\n"+
"			"+l+"m = "+s+"Ebi.get("+l+"m.getUuid());\r\n"+
"		}\r\n"+
"		return INPUT;\r\n"+
"	}\r\n"+
"\r\n"+	
"	//添加/修改\r\n"+
"	public String save(){\r\n"+
"		if("+l+"m.getUuid() == null){\r\n"+
"			"+s+"Ebi.save("+l+"m);\r\n"+
"		}else{\r\n"+
"			"+s+"Ebi.update("+l+"m);\r\n"+
"		}\r\n"+
"		return TO_LIST;\r\n"+
"	}\r\n"+
"\r\n"+	
"	//删除\r\n"+
"	public String delete(){\r\n"+
"		"+s+"Ebi.delete("+l+"m);\r\n"+
"		return TO_LIST;\r\n"+
"	}\r\n"+
"\r\n"+	
"}");
		
		//3.关闭
		bw.flush();
		bw.close();		
	}

	//6.Ebo
	private void generatorEbo() throws IOException {
		//1.创建文件
		File f = new File("src/"+rootDir+"/business/ebo/"+b+"Ebo.java");
		
		//保障不会进行误操作
		if(f.exists()){
			return;
		}
		
		f.createNewFile();
		//2.写文件
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		
		bw.write("package "+pkg+".business.ebo;\r\n"+
"\r\n"+
"import java.util.List;\r\n"+
"\r\n"+
"import "+pkg+".business.ebi."+b+"Ebi;\r\n"+
"import "+pkg+".dao.dao."+b+"Dao;\r\n"+
"import "+pkg+".vo."+b+"Model;\r\n"+
"import cn.itcast.erp.util.base.BaseQueryModel;\r\n"+
"\r\n"+
"public class "+b+"Ebo implements "+b+"Ebi{\r\n"+
"	private "+b+"Dao "+s+"Dao;\r\n"+
"	public void set"+b+"Dao("+b+"Dao "+s+"Dao) {\r\n"+
"		this."+s+"Dao = "+s+"Dao;\r\n"+
"	}\r\n"+
"\r\n"+
"	public void save("+b+"Model "+l+"m) {\r\n"+
"		"+s+"Dao.save("+l+"m);\r\n"+
"	}\r\n"+
"\r\n"+
"	public List<"+b+"Model> getAll() {\r\n"+
"		return "+s+"Dao.getAll();\r\n"+
"	}\r\n"+
"\r\n"+
"	public "+b+"Model get(Long uuid) {\r\n"+
"		return "+s+"Dao.get(uuid);\r\n"+
"	}\r\n"+
"\r\n"+
"	public void update("+b+"Model "+l+"m) {\r\n"+
"		"+s+"Dao.update("+l+"m);\r\n"+
"	}\r\n"+
"\r\n"+
"	public void delete("+b+"Model "+l+"m) {\r\n"+
"		"+s+"Dao.delete("+l+"m);\r\n"+
"	}\r\n"+
"\r\n"+
"	public List<"+b+"Model> getAll(BaseQueryModel qm, Integer pageNum,Integer pageCount) {\r\n"+
"		return "+s+"Dao.getAll(qm,pageNum,pageCount);\r\n"+
"	}\r\n"+
"\r\n"+
"	public Integer getCount(BaseQueryModel qm) {\r\n"+
"		return "+s+"Dao.getCount(qm);\r\n"+
"	}\r\n"+
"\r\n"+
"}");
		
		//3.关闭
		bw.flush();
		bw.close();	
	}

	//5.Ebi
	private void generatorEbi() throws IOException {
		//1.创建文件
		File f = new File("src/"+rootDir+"/business/ebi/"+b+"Ebi.java");
		
		//保障不会进行误操作
		if(f.exists()){
			return;
		}
		
		f.createNewFile();
		//2.写文件
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		
		bw.write("package "+pkg+".business.ebi;\r\n"+
				"\r\n"+
"import org.springframework.transaction.annotation.Transactional;\r\n"+
"\r\n"+
"import "+pkg+".vo."+b+"Model;\r\n"+
"import cn.itcast.erp.util.base.BaseEbi;\r\n"+
"\r\n"+
"@Transactional\r\n"+
"public interface "+b+"Ebi extends BaseEbi<"+b+"Model>{\r\n"+
"\r\n"+
"}"
);
		
		//3.关闭
		bw.flush();
		bw.close();	
	}

	//4.Impl
	private void generatorImpl() throws IOException {
		//1.创建文件
		File f = new File("src/"+rootDir+"/dao/impl/"+b+"Impl.java");
		
		//保障不会进行误操作
		if(f.exists()){
			return;
		}
		
		f.createNewFile();
		//2.写文件
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		
		bw.write("package "+pkg+".dao.impl;\r\n"+
				"\r\n"+
"import org.hibernate.criterion.DetachedCriteria;\r\n"+
"import org.hibernate.criterion.Restrictions;\r\n"+
"\r\n"+
"import "+pkg+".dao.dao."+b+"Dao;\r\n"+
"import "+pkg+".vo."+b+"Model;\r\n"+
"import "+pkg+".vo."+b+"QueryModel;\r\n"+
"import cn.itcast.erp.util.base.BaseImpl;\r\n"+
"import cn.itcast.erp.util.base.BaseQueryModel;\r\n"+
"\r\n"+
"public class "+b+"Impl extends BaseImpl<"+b+"Model> implements "+b+"Dao{\r\n"+
"\r\n"+
"	public void doQbc(DetachedCriteria dc,BaseQueryModel qm){\r\n"+
"		"+b+"QueryModel "+l+"qm = ("+b+"QueryModel)qm;\r\n"+
"		// TODO 未添加自定义查询条件查询方式设定\r\n"+
"	}\r\n"+
"}" 
);
		
		//3.关闭
		bw.flush();
		bw.close();	
	}

	//3.Dao
	private void generatorDao() throws IOException {
		//1.创建文件
		File f = new File("src/"+rootDir+"/dao/dao/"+b+"Dao.java");
		
		//保障不会进行误操作
		if(f.exists()){
			return;
		}
		
		f.createNewFile();
		//2.写文件
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		
		bw.write("package "+pkg+".dao.dao;\r\n"+
"\r\n"+
"import "+pkg+".vo."+b+"Model;\r\n"+
"import cn.itcast.erp.util.base.BaseDao;\r\n"+
"\r\n"+
"public interface "+b+"Dao extends BaseDao<"+b+"Model>{\r\n"+
"\r\n"+
"}"
);
		
		//3.关闭
		bw.flush();
		bw.close();	
	}

	//2.HbmXml
	private void generatorHbmXml() throws IOException {
		//1.创建文件
		File f = new File("src/"+rootDir+"/vo/"+b+"Model.hbm.xml");
		
		//保障不会进行误操作
		
		if(f.exists()){
			return;
		}
		
		f.createNewFile();
		//2.写文件
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		
		bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		bw.newLine();
		
		bw.write("<!DOCTYPE hibernate-mapping PUBLIC");
		bw.newLine();
		
		bw.write("        '-//Hibernate/Hibernate Mapping DTD 3.0//EN'");
		bw.newLine();
		
		bw.write("        'http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd'>");
		bw.newLine();
		
		bw.write("<hibernate-mapping>");
		bw.newLine();
		
		bw.write("    <class name=\""+pkg+".vo."+b+"Model\" table=\"tbl_"+s+"\">");
		bw.newLine();
		
		bw.write("        <id name=\"uuid\">");
		bw.newLine();
		
		bw.write("            <generator class=\"native\" />");
		bw.newLine();
		
		bw.write("        </id>");
		bw.newLine();
		
		//使用反射思想获取所有字段
		Field[] fs = clazz.getDeclaredFields();
		for(Field fd:fs){
			//仅对private修饰的进行生成
			if(fd.getModifiers() == Modifier.PRIVATE){
				//除去uuid
				if(!fd.getName().equals("uuid")){
					//不对关联关系字段进行生成,仅对Long,Double,Integer,String生成
					if(	fd.getType().equals(Long.class) 	||
						fd.getType().equals(Double.class) 	||
						fd.getType().equals(Integer.class) 	||
						fd.getType().equals(String.class) 	){
						bw.write("        <property name=\""+fd.getName()+"\"/>");
						bw.newLine();
					}
				}
			}
		}
		
		bw.write("    </class>");
		bw.newLine();
		
		bw.write("</hibernate-mapping>");
		bw.newLine();
		
		//3.关闭
		bw.flush();
		bw.close();
	}

	//1.QueryModel
	private void generatorQueryModel() throws IOException {
		//1.创建文件
		File f = new File("src/"+rootDir+"/vo/"+b+"QueryModel.java");
		
		//保障不会进行误操作
		if(f.exists()){
			return;
		}
		
		f.createNewFile();
		//2.写文件
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		
		bw.write("package "+pkg+".vo;");
		bw.newLine();
		
		bw.newLine();
		
		bw.write("import cn.itcast.erp.util.base.BaseQueryModel;");
		bw.newLine();
		
		bw.newLine();
		
		bw.write("public class "+b+"QueryModel extends "+b+"Model implements BaseQueryModel{");
		bw.newLine();
		
		bw.write("	// TODO 请添加自定义查询条件");
		bw.newLine();
		
		bw.write("}");
		bw.newLine();
		
		//3.关闭
		bw.flush();
		bw.close();
	}
	//0.生成目录
	private void generatorDirectories() {
		//web
		File f = new File("src/"+rootDir+"/web");
		f.mkdirs();
		//ebi
		f = new File("src/"+rootDir+"/business/ebi");
		f.mkdirs();
		//ebo
		f = new File("src/"+rootDir+"/business/ebo");
		f.mkdirs();
		//dao
		f = new File("src/"+rootDir+"/dao/dao");
		f.mkdirs();
		//impl
		f = new File("src/"+rootDir+"/dao/impl");
		f.mkdirs();
	}
	//-1.数据初始化
	private void dataInit() {
		//依据Model
		//clazz
		String allName = clazz.getSimpleName();	//EmpModel
		//Emp		GoodsType->goodstype
		b = allName.substring(0,allName.length()-5);
		//e
		l = b.substring(0,1).toLowerCase();
		//emp	goodsType
		s = l + b.substring(1);
		//cn.itcast.erp.auth.emp
		Package p = clazz.getPackage();
		//cn.itcast.erp.auth.emp.vo
		String pStr = p.getName();
		pkg = pStr.substring(0,pStr.length()-3);
		//cn/itcast/erp/auth/emp
		rootDir = pkg.replace(".", "/");
	}
	
	/*
	public static void main(String[] args) throws IOException {
		File f = new File("src/cn/User.java");
		f.createNewFile();
		
		FileWriter fw = new FileWriter(f);
		fw.write("package cn.itcast;");
		fw.close();
	}
	*/
	
	/*
	public static void main(String[] args) throws Exception {
		
		File f = new File("resources/1.txt");
		RandomAccessFile raf = new RandomAccessFile(f, "rw");
		
		byte[] buf = new byte[1024];
		int len = raf.read(buf);
		System.out.println(len);
		
		//读取原始文件到内存
		//将信息写入到新文件
		//读第1行，写入第二个文件
		//读第2行，写入第二个文件
		//读第3行，写入第二个文件
		//读第4行，如果是特殊的行，先写入一些内容，将读取的东西写入第二个文件
		//最后，删除文件1，将文件2更名为文件1
		
	}
	*/
	
}
