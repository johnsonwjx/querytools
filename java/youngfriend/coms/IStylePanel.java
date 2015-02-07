package youngfriend.coms;

import java.awt.Component;
import java.util.List;

import youngfriend.utils.ComEum;

public interface IStylePanel extends IStyleCom {
	public List<IStyleCom> getChilds();

	public void addChild(Component child);

	public boolean forbid(ComEum type);
}