package zzl.servlet;

import com.google.gson.Gson;
import zzl.Utils;
import zzl.beans.json.RegisterBeans;
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

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Result<Boolean> result = new Result<>();
        Gson gson = new Gson();
        try {
            String json = Utils.getJsonFromStream(request.getInputStream());
            RegisterBeans registerBeans = gson.fromJson(json, RegisterBeans.class);
            result.setStatus("ok");
            if (check(registerBeans.getPhone())) {
                result.setData(register(registerBeans));
            } else {
                result.setData(false);
                result.setMsg("ÊÖ»úºÅÂëÒÑ×¢²á");
            }
        } catch (IOException e) {
            e.printStackTrace();
            result.setStatus("error");
            result.setMsg("×¢²áÊ§°Ü");
        }
        Utils.send(response, gson.toJson(result));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
    }

    /**
     * ¼ì²éÊÖ»úºÅÊÇ·ñÒÑ×¢²á
     *
     * @param phone
     * @return
     */
    private boolean check(String phone) {
        MySql sql = new MySql();

        try {
            ResultSet rs = sql.getData("select iduser from user where phone=?", phone);
            if (rs.next())
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            sql.closeAllConnection();
        }
        return true;
    }

    /**
     * ×¢²á
     *
     * @param registerBeans
     * @return
     */
    private boolean register(RegisterBeans registerBeans) {
        MySql mySql = new MySql();
        int result = mySql.updateData("insert into user (phone,pwd,nickname,type) values (?,?,?,?)",
                registerBeans.getPhone(),
                registerBeans.getPwd(),
                registerBeans.getNickName(),
                String.valueOf(registerBeans.getType()));
        return result == 1;
    }
}
