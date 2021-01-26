package zzl.servlet;

import com.google.gson.Gson;
import zzl.Utils;
import zzl.beans.User;
import zzl.beans.json.Result;
import zzl.beans.json.UserInfo;
import zzl.dao.MySql;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "UserInfoServlet", urlPatterns = {"/userinfo"})
public class UserInfoServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Result<UserInfo> result = new Result<>();
		Gson gson = new Gson();

		User u=(User) request.getSession().getAttribute("user");
		if (u != null) {
			result.setStatus("ok");
			System.out.println(u.getUserID());
			UserInfo user = getUserInfo(u);
			result.setData(user);
		}else {
			result.setStatus("error");
		}
		Utils.send(response, gson.toJson(result));
	}

	private UserInfo getUserInfo(User u) {
		String sql = "select iduser,nickname,sex,phone,type,balance from user where iduser=?";
		MySql mySql = new MySql();
		UserInfo user = null;
		ResultSet rs = mySql.getData(sql, u.getUserID());
		try {
			if (rs.next()) {
				user=new UserInfo();
				user.setId(rs.getInt("iduser"));
				user.setNickName(rs.getString("nickname"));
				user.setSex(rs.getString("sex"));
				user.setPhone(rs.getString("phone"));
				user.setBalance(rs.getFloat("balance"));
				String type=rs.getString("type");

				if(type!=null&&!type.isEmpty())
				{
					user.setType(Integer.valueOf(type));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mySql.closeAllConnection();
		}
		return user;
	}
}
