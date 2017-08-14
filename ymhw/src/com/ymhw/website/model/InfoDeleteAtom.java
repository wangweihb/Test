package com.ymhw.website.model;

import java.sql.SQLException;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;

public class InfoDeleteAtom implements IAtom
{
	private int id;
	
	public InfoDeleteAtom(int id)
	{
		super();
		this.id = id;
	}


	@Override
	public boolean run() throws SQLException
	{
		boolean result1 = Info.dao.deleteById(id);
		if (!result1) {
			//尽早回滚
			return false;
		}
		int tagLen = Info.dao.getTags(id).size();
		int childLen = Info.dao.getModules(id).size();
		if (tagLen > 0)
		{
			int cnt = Db.update("delete from info_has_tag where info_id = ?", id);
			if (cnt < 1)
			{
				return false;
			}
		}
		
		if (childLen > 0)
		{
			int cnt = Db.update("delete from info_module where parent_id = ?", id);
			if (cnt < 1)
			{
				return false;
			}
		}
		//所有的删除操作没有异常，说明正常删除
		return true;
	}

}
