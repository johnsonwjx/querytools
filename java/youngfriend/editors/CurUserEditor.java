/*
 * ChooseSortField.java
 *
 * Created on 2008年8月1日, 下午2:21
 */

package youngfriend.editors;

import java.awt.Window;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JPanel;

import youngfriend.beans.PropDto;
import youngfriend.beans.XMLDto;

public class CurUserEditor extends JPanel implements PropEditor {
	private static final long serialVersionUID = 1L;
	private static final Collection<XMLDto> all = new ArrayList<XMLDto>();
	static {
		all.add(new XMLDto("当前部门代码", "DEPT_CODE"));
		all.add(new XMLDto("当前部门名称", "DEPT_NAME"));
		all.add(new XMLDto("上级部门代码", "PARENT_DEPT_CODE"));
		all.add(new XMLDto("上级部门名称", "PARENT_DEPT_NAME"));
		all.add(new XMLDto("上级部门简称", "PARENT_DEPT_SHORTNAME"));
		all.add(new XMLDto("上级部门简称及代码", "PARENT_DEPT_CODESHORTNAME"));
		all.add(new XMLDto("当前人员USERID", "USER_ID"));
		all.add(new XMLDto("当前人员名称", "USER_NAME"));
		all.add(new XMLDto("当前人员PERSONID", "PERSON_ID"));
		all.add(new XMLDto("当前人拥有对象权限的集团ID", "CORP_ID"));
		all.add(new XMLDto("当前人拥有对象权限的集团代码", "CORP_CODE"));
		all.add(new XMLDto("当前人拥有对象权限的集团名称", "CORP_NAME"));

		all.add(new XMLDto("当前人行政部门所在的集团ID", "deptcorpid"));
		all.add(new XMLDto("当前人行政部门所在的集团代码", "deptcorpcode"));
		all.add(new XMLDto("当前人行政部门所在的集团名称", "deptcorpname"));

		all.add(new XMLDto("当前人岗位代码", "ROLECODE"));
		all.add(new XMLDto("当前人岗位名称", "ROLENAME"));
		all.add(new XMLDto("人员表通用字段二十三", "STRINGFIELD23"));
		all.add(new XMLDto("人员表通用字段二十四", "STRINGFIELD24"));
		all.add(new XMLDto("人员表通用字段二十五", "STRINGFIELD25"));
		all.add(new XMLDto("人员表通用字段二十五", "STRINGFIELD25"));
		all.add(new XMLDto("部门简称", "DEPTSHORTNAME"));
		all.add(new XMLDto("部门简称1", "DEPTSHORTNAME1"));
		all.add(new XMLDto("部门简称2", "DEPTSHORTNAME2"));
		all.add(new XMLDto("部门简称3", "DEPTSHORTNAME3"));
		all.add(new XMLDto("部门简称4", "DEPTSHORTNAME4"));
	}

	@Override
	public void edit(final PropDto prop, Window owner) {
		DefaultPropEditor.showSelectPnl(all, prop, 200, owner);
	}
}
