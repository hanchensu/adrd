package com.sohu.adrd.data.sessionlog.config;

public class SortConfig {
	
	private boolean idSort = false;
	private String idCreater = null;
	
	public String getIdCreater() {
		return idCreater;
	}
	
	public void setIdCreater(String idCreater) {
		this.idCreater = idCreater;
	}

	public boolean isSort() {
		return idSort;
	}

	public void setIdSort(boolean idSort) {
		this.idSort = idSort;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{sort=").append(idSort).append(",creater=").append(idCreater).append("}");
		return sb.toString();
	}

}
