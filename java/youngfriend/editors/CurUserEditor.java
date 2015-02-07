/*
 * ChooseSortField.java
 *
 * Created on 2008��8��1��, ����2:21
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
		all.add(new XMLDto("��ǰ���Ŵ���", "DEPT_CODE"));
		all.add(new XMLDto("��ǰ��������", "DEPT_NAME"));
		all.add(new XMLDto("�ϼ����Ŵ���", "PARENT_DEPT_CODE"));
		all.add(new XMLDto("�ϼ���������", "PARENT_DEPT_NAME"));
		all.add(new XMLDto("�ϼ����ż��", "PARENT_DEPT_SHORTNAME"));
		all.add(new XMLDto("�ϼ����ż�Ƽ�����", "PARENT_DEPT_CODESHORTNAME"));
		all.add(new XMLDto("��ǰ��ԱUSERID", "USER_ID"));
		all.add(new XMLDto("��ǰ��Ա����", "USER_NAME"));
		all.add(new XMLDto("��ǰ��ԱPERSONID", "PERSON_ID"));
		all.add(new XMLDto("��ǰ��ӵ�ж���Ȩ�޵ļ���ID", "CORP_ID"));
		all.add(new XMLDto("��ǰ��ӵ�ж���Ȩ�޵ļ��Ŵ���", "CORP_CODE"));
		all.add(new XMLDto("��ǰ��ӵ�ж���Ȩ�޵ļ�������", "CORP_NAME"));

		all.add(new XMLDto("��ǰ�������������ڵļ���ID", "deptcorpid"));
		all.add(new XMLDto("��ǰ�������������ڵļ��Ŵ���", "deptcorpcode"));
		all.add(new XMLDto("��ǰ�������������ڵļ�������", "deptcorpname"));

		all.add(new XMLDto("��ǰ�˸�λ����", "ROLECODE"));
		all.add(new XMLDto("��ǰ�˸�λ����", "ROLENAME"));
		all.add(new XMLDto("��Ա��ͨ���ֶζ�ʮ��", "STRINGFIELD23"));
		all.add(new XMLDto("��Ա��ͨ���ֶζ�ʮ��", "STRINGFIELD24"));
		all.add(new XMLDto("��Ա��ͨ���ֶζ�ʮ��", "STRINGFIELD25"));
		all.add(new XMLDto("��Ա��ͨ���ֶζ�ʮ��", "STRINGFIELD25"));
		all.add(new XMLDto("���ż��", "DEPTSHORTNAME"));
		all.add(new XMLDto("���ż��1", "DEPTSHORTNAME1"));
		all.add(new XMLDto("���ż��2", "DEPTSHORTNAME2"));
		all.add(new XMLDto("���ż��3", "DEPTSHORTNAME3"));
		all.add(new XMLDto("���ż��4", "DEPTSHORTNAME4"));
	}

	@Override
	public void edit(final PropDto prop, Window owner) {
		DefaultPropEditor.showSelectPnl(all, prop, 200, owner);
	}
}
