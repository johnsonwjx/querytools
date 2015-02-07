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

public class DefaultDateEditor extends JPanel implements PropEditor {
	private static final long serialVersionUID = 1L;
	private static final Collection<XMLDto> all = new ArrayList<XMLDto>();
	static {
		all.add(new XMLDto("年-月-日（当前日期）", "YYYY-MM-DD"));
		all.add(new XMLDto("年-月（当前年月）", "YYYY-MM"));
		all.add(new XMLDto("年（当前)", "YYYY"));
		all.add(new XMLDto("月（当前）", "MM"));
		all.add(new XMLDto("月（上月）", "MM_LM"));
		all.add(new XMLDto("月初（当前月）", "BM"));
		all.add(new XMLDto("月初（上月）", "BM_LM"));
		all.add(new XMLDto("月末（当前月）", "EM"));
		all.add(new XMLDto("年初（当前年）", "BY"));
		all.add(new XMLDto("年末（当前年）", "EY"));
	}

	@Override
	public void edit(PropDto prop, Window owner) {
		DefaultPropEditor.showSelectPnl(all, prop, 200, owner);
	}
}
