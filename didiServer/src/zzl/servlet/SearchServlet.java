package zzl.servlet;

import com.google.gson.Gson;
import zzl.Utils;
import zzl.beans.json.PathInfoBean;
import zzl.beans.json.Result;
import zzl.beans.json.SearchBean;
import zzl.dao.MySql;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "SearchServlet",urlPatterns = {"/search"})
public class SearchServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String json = Utils.getJsonFromStream(request.getInputStream());
		Result<List<PathInfoBean>> result = new Result<>();
		Gson gson = new Gson();
		if (!json.isEmpty()) {
			SearchBean searchBean = gson.fromJson(json, SearchBean.class);
			List<PathInfoBean> list = search(searchBean);

			result.setStatus("ok");
			result.setData(list);

		} else {
			result.setStatus("error");
			result.setMsg("错误的访问参数");
		}
		Utils.send(response, gson.toJson(result));
	}

	/**
	 * 查询经过这两个地点的司机的信息和运费单价
	 * @param search
	 * @return
	 */
	private List<PathInfoBean> search(SearchBean search) {
		MySql mySql = new MySql();
		List<PathInfoBean> list = new ArrayList<>();
		try {
			ResultSet rs = mySql.getData("select user.iduser,nickname,phone,pstart.carriage,pend.carriage from path as pstart " +
					"inner join path as pend on pstart.iduser=pend.iduser and pstart.location=? and pend.location=? " +
					"inner join user on user.iduser=pstart.iduser", search.getStart(), search.getEnd());
			while (rs.next()) {
				PathInfoBean pathInfo = new PathInfoBean();
				pathInfo.setPhone(rs.getString("phone"));
				pathInfo.setNickName(rs.getString("nickname"));
				pathInfo.setId(rs.getInt("user.iduser"));
				float price=rs.getFloat("pstart.carriage")-rs.getFloat("pend.carriage");
				pathInfo.setPrice(price>=0?price:-price);
				list.add(pathInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mySql.closeAllConnection();
		}
		return list;
	}
}
