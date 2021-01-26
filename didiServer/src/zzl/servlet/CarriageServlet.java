package zzl.servlet;

import com.google.gson.Gson;
import zzl.Utils;
import zzl.beans.User;
import zzl.beans.json.PathBean;
import zzl.beans.json.Result;
import zzl.dao.MySql;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@WebServlet(name = "CarriageServlet",urlPatterns = {"/carriage"})
public class CarriageServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MySql mySql=new MySql();
		User user=(User) request.getSession().getAttribute("user");
		Result<List<PathBean>> result=new Result<>();
		List<PathBean> list=new LinkedList<>();
		try {
			String str=request.getParameter("id");
			int id;
			if(str!=null&&!str.isEmpty())
			{
				id=Integer.parseInt(str);
			}else {
				id=user.getUserID();
			}
			ResultSet resultSet=mySql.getData("select location,carriage from path where iduser=? order by orderNum",id);
			while (resultSet.next())
			{
				PathBean pathBean=new PathBean();
				pathBean.setLocation(resultSet.getString("location"));
				pathBean.setCarriage(resultSet.getFloat("carriage"));
				list.add(pathBean);
			}
			result.setStatus("ok");
		} catch (SQLException e) {
			e.printStackTrace();
			result.setStatus("error");
		} finally {
			mySql.closeAllConnection();
		}
		result.setData(list);
		Gson gson=new Gson();
		Utils.send(response,gson.toJson(result));
	}
}
