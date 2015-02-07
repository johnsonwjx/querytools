package youngfriend.beans;

import java.io.File;

public enum TreeIconEnum {

	CATALOG_IMG {

		@Override
		public String getPath() {
			return "images" + File.separator + "common" + File.separator + "folder.gif";
		}
	},
	CATALOG_OPEN_IMG {

		@Override
		public String getPath() {
			return "images" + File.separator + "common" + File.separator + "folder-expanded.gif";
		}
	},
	FOLDER_GREEN {

		@Override
		public String getPath() {
			return "images" + File.separator + "common" + File.separator + "folder_green_favourite.png";
		}
	},
	FOLDER_RED {

		@Override
		public String getPath() {
			return "images" + File.separator + "common" + File.separator + "folder_red_parent.png";
		}
	},
	CIRCLE_GREEN {

		@Override
		public String getPath() {
			return "images" + File.separator + "common" + File.separator + "circle_green.png";
		}
	},
	CIRCLE_YELLOW {

		@Override
		public String getPath() {
			return "images" + File.separator + "common" + File.separator + "circle_yellow.png";
		}
	},
	CLASS_IMG {

		@Override
		public String getPath() {
			return "images" + File.separator + "common" + File.separator + "111.bmp";
		}
	};

	public abstract String getPath();
}
