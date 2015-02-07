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

public class DefaultDateEditor extends JPanel implements PropEditor {
	private static final long serialVersionUID = 1L;
	private static final Collection<XMLDto> all = new ArrayList<XMLDto>();
	static {
		all.add(new XMLDto("��-��-�գ���ǰ���ڣ�", "YYYY-MM-DD"));
		all.add(new XMLDto("��-�£���ǰ���£�", "YYYY-MM"));
		all.add(new XMLDto("�꣨��ǰ)", "YYYY"));
		all.add(new XMLDto("�£���ǰ��", "MM"));
		all.add(new XMLDto("�£����£�", "MM_LM"));
		all.add(new XMLDto("�³�����ǰ�£�", "BM"));
		all.add(new XMLDto("�³������£�", "BM_LM"));
		all.add(new XMLDto("��ĩ����ǰ�£�", "EM"));
		all.add(new XMLDto("�������ǰ�꣩", "BY"));
		all.add(new XMLDto("��ĩ����ǰ�꣩", "EY"));
	}

	@Override
	public void edit(PropDto prop, Window owner) {
		DefaultPropEditor.showSelectPnl(all, prop, 200, owner);
	}
}
