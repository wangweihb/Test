package com.ymhw.website.model;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.ymhw.website.model.base.BaseFarmstay;
import java.util.List;
import java.util.Map;

public class Farmstay extends BaseFarmstay<Farmstay>
{
  public static final Farmstay dao = new Farmstay();

  public boolean saveOne(Farmstay farmstay, String[] proTitles, String[] proPrices, String[] proPics)
  {
    return dao.save();
  }

  public List<Farmstay> getFarmsByInfoId(int infoId) {
    return dao.find("select * from farmstay where destinationId = ? order by updateTime desc", new Object[] { Integer.valueOf(infoId) });
  }

  public Page<Farmstay> paginateByCondition(int pageNumber, int pageSize, Map<String, Object> param) {
    String keyword = (String)param.get("keyword");
    String sql = "select f.*,inf.subject from farmstay f, info inf where f.destinationId = inf.info_id";
    if (StrKit.isBlank(keyword)) {
      sql = sql + " order by f.updateTime desc";
      return dao.paginate(pageNumber, pageSize, sql);
    }
    sql = sql + " and f.shopName like ? order by f.updateTime desc";
    return dao.paginate(pageNumber, pageSize, sql, new Object[] { "%" + keyword + "%" });
  }
}