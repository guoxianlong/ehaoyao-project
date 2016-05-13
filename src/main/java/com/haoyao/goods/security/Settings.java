package com.haoyao.goods.security;

public class Settings {
	 private static Settings INSTANCE = new Settings();

	    private Settings() {
	    }

	    public static Settings getInstance() {
		return INSTANCE;
	    }
	    /**
	     * 上传的附件保存服务器的位置
	     */
	    private String uploadPath;
        /**
         * 服务器ip地址
         * @return
         */
	    private String spPath;
	    /**
	     * 服务器外网地址
	     * @return
	     */
	    private String wwPath;
		public String getSpPath() {
			return spPath;
		}

		public void setSpPath(String spPath) {
			this.spPath = spPath;
		}

		public String getWwPath() {
			return wwPath;
		}

		public void setWwPath(String wwPath) {
			this.wwPath = wwPath;
		}

		public String getUploadPath() {
			return uploadPath;
		}

		public void setUploadPath(String uploadPath) {
			this.uploadPath = uploadPath;
		}
}
