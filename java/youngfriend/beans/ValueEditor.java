package youngfriend.beans;

import java.awt.Window;
import java.util.Map;

public interface ValueEditor {
	void edit(Window owner, Map<String, String> props);

	boolean isSubmit();
}
