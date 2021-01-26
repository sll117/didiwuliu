package zzl;

import com.google.gson.Gson;
import zzl.beans.json.Result;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "Filter", urlPatterns = {"/*"})
public class Filter implements javax.servlet.Filter {
    public void destroy() {
    }

    /**
     * …Ë÷√±‡¬Î
     *
     * @param req
     * @param resp
     * @param chain
     * @throws ServletException
     * @throws IOException
     */
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=utf-8");

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        HttpSession session = request.getSession(false);

        String path = request.getServletPath();
        if (!path.equals("/login") && !path.equals("/register")) {
            if (session == null || session.getAttribute("user") == null) {
                if (session != null) {
                    System.out.println(session.getId());
                }
                Result<String> result = new Result<String>();
                result.setStatus("error");
                result.setMsg("Œ¥µ«¬º");
                PrintWriter pw = response.getWriter();
                pw.println(new Gson().toJson(result));
                pw.close();
                return;
            }
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
