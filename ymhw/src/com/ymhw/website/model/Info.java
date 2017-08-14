package com.ymhw.website.model;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.ymhw.website.model.base.BaseInfo;
import com.ymhw.website.utils.StringTool;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Info extends BaseInfo<Info>
{
  public static final Info dao = new Info();

  public List<Record> getTitles()
  {
    return Db.find("select info_id,subject from info order by createTime desc");
  }

  public List<Info> getInfosByType(int type, int num)
  {
    if (num == 0)
    {
      return dao.find("select info_id,subject,commentScore,commentNum,substring_index(subjectImg, ',', 1) as img,`desc` from info where type=? order by createTime desc", new Object[] { Integer.valueOf(type) });
    }

    return dao.find("select info_id,subject,commentScore,commentNum,substring_index(subjectImg, ',', 1) as img,`desc` from info where type=? order by createTime desc limit ?", new Object[] { Integer.valueOf(type), Integer.valueOf(num) });
  }

  public List<Info> getInfos(int num)
  {
    if (num == 0)
    {
      return dao.find("select info_id,subjectImg,commentScore,commentNum,substring_index(subjectImg, ',', 1) as img,`desc` from info order by createTime desc");
    }

    return dao.find("select info_id,subject,commentScore,commentNum,substring_index(subjectImg, ',', 1) as img,`desc` from info order by createTime desc limit ?", new Object[] { Integer.valueOf(num) });
  }

  public List<Info> getBasicInfos(int num)
  {
    if (num == 0)
    {
      return dao.find("select *,substring_index(subjectImg, ',', 1) as img from info order by createTime desc");
    }

    return dao.find("select *,substring_index(subjectImg, ',', 1) as img from info order by createTime desc limit ?", new Object[] { Integer.valueOf(num) });
  }

  public List<Record> getInfoDetails(int num)
  {
	List<Info> infos = new ArrayList();
    List<Record> records = new ArrayList();
    infos = dao.find("select info_id from info order by createTime desc limit ?", new Object[] { Integer.valueOf(num) });
    for (Info one : infos) {
      records.add(getInfoAllById(one.getInfoId().intValue()));
    }
    return records;
  }

  public List<Record> getInfoDetails2(int num)
  {
    List<Info> infos = new ArrayList();
    List records = new ArrayList();
    infos = dao.find("select info_id from info order by createTime desc limit ?", new Object[] { Integer.valueOf(num) });
    for (Info one : infos) {
      records.add(getInfoAllById2(one.getInfoId().intValue()));
    }
    return records;
  }

  public List<Record> getInfoDetailsByType(int type)
  {
    List<Info> infos = new ArrayList();
    List records = new ArrayList();
    if (type == 0)
      infos = dao.find("select info_id from info order by createTime desc limit 100");
    else {
      infos = dao.find("select info_id from info where type = ? order by createTime desc", new Object[] { Integer.valueOf(type) });
    }
    for (Info one : infos) {
      records.add(getInfoAllById2(one.getInfoId().intValue()));
    }
    return records;
  }

  public Record getInfoAllById(int id)
  {
    Info info = (Info)dao.findById(Integer.valueOf(id));
    Record record = new Record();
    record.set("info", info);
    record.set("images", info.getSubjectImg().split(","));
    record.set("tags", getTags(Integer.valueOf(id)));
    record.set("tagstr", getTagStr(Integer.valueOf(id)));

    record.set("farmstays", getFarmstays(Integer.valueOf(id)));
    record.set("hotels", getHotel(Integer.valueOf(id)));
    return record;
  }

  public Record getInfoAllById2(int id)
  {
    Info info = (Info)dao.findById(Integer.valueOf(id));
    Record record = new Record();
    record.set("info", info);
    record.set("images", info.getSubjectImg().split(","));
    record.set("tags", getTags(Integer.valueOf(id)));
    record.set("tagstr", getTagStr(Integer.valueOf(id)));
    return record;
  }

  public boolean deleteInfoById(int id)
  {
    return Db.tx(new InfoDeleteAtom(id));
  }

  public boolean saveOne(Info base, String[] tags, String[] childInfoTitles, String[] childInfoDescs)
  {
    return Db.tx(new InfoAddAtom(tags, base, childInfoTitles, childInfoDescs));
  }

  public List<InfoTag> getTags(Integer infoId)
  {
    if (infoId == null) {
      infoId = getInfoId();
    }
    return InfoTag.dao.find("select t.* from info_tag t, info_has_tag iht where iht.info_id = ? and t.tag_id = iht.tag_id", new Object[] { infoId });
  }

  public String getTagStr(Integer infoId) {
    if (infoId == null) {
      infoId = getInfoId();
    }
    List<InfoHasTag> tags = InfoHasTag.dao.find("select tag_id from info_has_tag WHERE info_id = ?", new Object[] { infoId });
    StringBuilder builder = new StringBuilder();
    for (InfoHasTag infoHasTag : tags) {
      builder.append(infoHasTag.getTagId()).append(",");
    }
    return builder.toString();
  }

  public List<InfoModule> getModules(Integer infoId)
  {
    if (infoId == null) {
      infoId = getInfoId();
    }
    return InfoModule.dao.find("select * from info_module where parent_id = ?", new Object[] { infoId });
  }

  public List<Farmstay> getFarmstays(Integer infoId)
  {
    if (infoId == null) {
      infoId = getInfoId();
    }
    return Farmstay.dao.find("select * from farmstay where destinationId = ?", new Object[] { infoId });
  }

  public List<Hotel> getHotel(Integer infoId)
  {
    if (infoId == null) {
      infoId = getInfoId();
    }
    return Hotel.dao.find("select * from hotel where destinationId = ?", new Object[] { infoId });
  }

  public List<Info> getNInfos(int num) {
    if (num == 0)
    {
      return dao.find("select info_id,subject,provice,city,region,commentScore,commentNum,SUBSTRING_INDEX(subjectImg,',',1) as subjectImg from info order by createTime desc");
    }

    return dao.find("select info_id,subject,provice,city,region,commentScore,commentNum,SUBSTRING_INDEX(subjectImg,',',1) as subjectImg from info order by createTime desc limit ?", new Object[] { Integer.valueOf(num) });
  }

  public List<Info> getInfosByConditon(Map<String, Object> condition)
  {
    String infoTypes = (String)condition.get("infoTypes");
    String infoTags = (String)condition.get("infoTags");
    List results = new ArrayList();
    if ((!StrKit.isBlank(infoTypes)) && (!StrKit.isBlank(infoTags))) {
      infoTypes = ",".equals(Character.valueOf(infoTypes.charAt(infoTypes.length() - 1))) ? infoTypes.substring(0, infoTypes.length() - 1) : infoTypes;
      infoTags = ",".equals(Character.valueOf(infoTags.charAt(infoTags.length() - 1))) ? infoTags.substring(0, infoTags.length() - 1) : infoTags;
      results = dao.find("select DISTINCT *,substring_index(subjectImg, ',', 1) as img,type from info  WHERE info_id in (SELECT DISTINCT info_id from info_has_tag WHERE tag_id in (?)) or type in (?)  order by createTime desc", new Object[] { 
        infoTags, infoTypes });
    } else if ((StrKit.isBlank(infoTypes)) && (!StrKit.isBlank(infoTags))) {
      infoTags = ",".equals(Character.valueOf(infoTags.charAt(infoTags.length() - 1))) ? infoTags.substring(0, infoTags.length() - 1) : infoTags;
      results = dao.find("select DISTINCT *,substring_index(subjectImg, ',', 1) as img,type from info  WHERE info_id in (SELECT DISTINCT info_id from info_has_tag WHERE tag_id in (?))   order by createTime desc", new Object[] { 
        infoTags });
    } else if ((!StrKit.isBlank(infoTypes)) && (StrKit.isBlank(infoTags))) {
      infoTypes = ",".equals(Character.valueOf(infoTypes.charAt(infoTypes.length() - 1))) ? infoTypes.substring(0, infoTypes.length() - 1) : infoTypes;
      results = dao.find("select DISTINCT *,substring_index(subjectImg, ',', 1) as img,type from info  WHERE   type in (?)  order by createTime desc", new Object[] { 
        infoTypes });
    } else {
      results = dao.find("select *,substring_index(subjectImg, ',', 1) as img from info order by createTime desc");
    }
    results = StringTool.removeDuplicate(results);
    System.out.println("results : " + results.size());
    return results;
  }

  public List<Info> getInfosByConditon2(Map<String, Object> condition) {
    String regions = (String)condition.get("regions");

    List results = new ArrayList();
    if ("".equals(regions)) {
      results = dao.find("select *,substring_index(subjectImg, ',', 1) as img from info  order by createTime desc", new Object[] { regions });
    } else {
      regions = regions.substring(0, regions.length() - 1);
      results = dao.find("select *,substring_index(subjectImg, ',', 1) as img from info where region = ? order by createTime desc", new Object[] { regions });
    }
    System.out.println("results : " + results.size());
    return results;
  }

  public int searchByKeyword(String keyword)
  {
    Info info = (Info)dao.findFirst("select * from info where subject like ?", new Object[] { '%' + keyword + '%' });
    if (info != null) {
      return info.getInfoId().intValue();
    }
    return 0;
  }

  public Page<Info> paginateByCondition(int pageNumber, int pageSize, Map<String, Object> param) {
    String keyword = (String)param.get("keyword");
    int type = ((Integer)param.get("type")).intValue();
    List params = new ArrayList();
    String start = "select *,substring_index(subjectImg, ',', 1) as img from info where 1=1 ";
    String middle = "";
    String end = " order by createTime desc";
    if (!StrKit.isBlank(keyword)) {
      middle = middle + "and (subject like ? or provice like ? or city like ? or region like ? or streetAddress like ?)";
      params.add("%" + keyword + "%");
      params.add("%" + keyword + "%");
      params.add("%" + keyword + "%");
      params.add("%" + keyword + "%");
      params.add("%" + keyword + "%");
    }
    if (type != 0) {
      middle = middle + " and type = ? ";
      params.add(Integer.valueOf(type));
    }
    String sql = start + middle + end;
    return params.size() == 0 ? dao.paginate(pageNumber, pageSize, sql) : dao.paginate(pageNumber, pageSize, sql, params.toArray());
  }
}