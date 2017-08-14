package com.ymhw.website.model;

import java.sql.SQLException;


import com.jfinal.plugin.activerecord.IAtom;

public class InfoAddAtom implements IAtom
{
//	private static final Logger logger = Logger.getLogger(InfoAtom.class);
	private Info info;
	private String[] tags ;
	private String[] childInfoTitles;
	private String[] childInfoDescs;
	
	public InfoAddAtom(String[] tags, Info info, String[] childInfoTitles, String[] childInfoDescs)
	{
		this.tags = tags;
		this.info = info;
		this.childInfoTitles = childInfoTitles;
		this.childInfoDescs = childInfoDescs;
	}
	
	@Override
	public boolean run() throws SQLException
	{
		//info 表的处理
		boolean result = info.save();
		if (!result)
		{
			//尽早回滚
			return false;
		}
		
		//info_has_tag 表的处理
		int infoId = info.getInfoId();
		//需要通过事务处理
		if (tags != null && tags.length != 0) {
			for(int i = 0; i < tags.length; i++) {
				int tagId = Integer.parseInt(tags[i]);
				InfoHasTag ist = new InfoHasTag();
				ist.setInfoId(infoId);
				ist.setTagId(tagId);
				if (!ist.save())
				{
					//return false就回滚
					return false;
				}
			}
		}
		
		//info_module表的处理
		if (childInfoTitles != null && childInfoDescs !=null && childInfoTitles.length != 0 && childInfoDescs.length != 0) {
			for (int i = 0; i< childInfoDescs.length; i++) {
				InfoModule module = new InfoModule();
				module.setModuleName(childInfoTitles[i].trim());
				module.setModuleContent(childInfoDescs[i].trim());
				module.setParentId(infoId);
				if (!module.save())
				{
					return false;
				}
			}
		}
		return true;
	}

}
