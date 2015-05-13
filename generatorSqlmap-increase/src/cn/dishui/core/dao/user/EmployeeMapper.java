package cn.dishui.core.dao.user;

import cn.dishui.core.po.user.Employee;
import cn.dishui.core.po.user.EmployeeQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EmployeeMapper {
    int countByExample(EmployeeQuery example);

    int deleteByExample(EmployeeQuery example);

    int deleteByPrimaryKey(String username);

    int insert(Employee record);

    int insertSelective(Employee record);

    List<Employee> selectByExample(EmployeeQuery example);

    Employee selectByPrimaryKey(String username);

    int updateByExampleSelective(@Param("record") Employee record, @Param("example") EmployeeQuery example);

    int updateByExample(@Param("record") Employee record, @Param("example") EmployeeQuery example);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);
}