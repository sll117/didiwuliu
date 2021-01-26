package zzl.servlet;

import com.google.gson.Gson;
import zzl.Utils;
import zzl.beans.User;
import zzl.beans.json.OrderItemBean;
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

@WebServlet(name = "AddBalanceServlet", urlPatterns = {"/addbalance"})
public class AddBalanceServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MySql mySql = new MySql();
        User user = (User) request.getSession().getAttribute("user");
        Result<Boolean> result = new Result<>();
        try {

            result.setStatus("ok");
            String str = request.getParameter("money");
            result.setData(false);
            if (str != null && !str.isEmpty()) {
                float money = Float.parseFloat(str);
                if (money > 0) {
                    mySql.updateBalance(user.getUserID(), money);
                    result.setData(true);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus("error");
        } finally {
            mySql.closeAllConnection();
        }
        Gson gson = new Gson();
        Utils.send(response, gson.toJson(result));
    }
}
