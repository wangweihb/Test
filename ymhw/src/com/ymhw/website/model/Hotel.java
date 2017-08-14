package com.ymhw.website.model;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.ymhw.website.model.base.BaseHotel;
import java.util.Map;

public class Hotel extends BaseHotel<Hotel>
{
  public static final Hotel dao = new Hotel();

  public Page<Hotel> paginateByCondition(int pageNumber, int pageSize, Map<String, Object> param) {
    String keyword = (String)param.get("keyword");
    String sql = "select h.*,inf.subject from hotel h, info inf where h.destinationId = inf.info_id";
    if (StrKit.isBlank(keyword)) {
      sql = sql + " order by h.updateTime desc";
      return dao.paginate(pageNumber, pageSize, sql);
    }
    sql = sql + " and h.name like ? order by h.updateTime desc";
    return dao.paginate(pageNumber, pageSize, sql, new Object[] { "%" + keyword + "%" });
  }
}