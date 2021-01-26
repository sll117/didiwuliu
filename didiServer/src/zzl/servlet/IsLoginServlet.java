package zzl.servlet;

import com.google.gson.Gson;
import zzl.Utils;
import zzl.beans.User;
import zzl.beans.json.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "IsLoginServlet", urlPatterns = {"/islogin"})
public class IsLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Result<Boolean> result = new Result<>();
        result.setStatus("ok");
        try {
            User user = (User) request.getSession().getAttribute("user");
            result.setData(user != null);
        } catch (Exception e) {
            e.printStackTrace();
            result.setData(false);
        }
        Utils.send(response, new Gson().toJson(result));
    }
}
