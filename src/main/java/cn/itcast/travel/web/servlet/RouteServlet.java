package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;
import com.sun.istack.internal.NotNull;
import org.w3c.dom.css.ElementCSSInlineStyle;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    private RouteService service = new RouteServiceImpl();
    private FavoriteService service2 = new FavoriteServiceImpl();

    /**
     * 分页查询
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(@NotNull HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.接收参数（html中使用ajax请求）
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");

        //接受rname线路名称
        String rname = request.getParameter("rname");
        //tomcat1.7，debug出了乱码
        rname = new String(rname.getBytes("iso-8859-1"),"utf-8");

        //2.处理参数
        int cid = 0;//类别id
        if (cidStr != null && cidStr.length() > 0 && !"null".equals(cidStr)) {
            cid = Integer.parseInt(cidStr);
        }

        int currentPage = 0;//当前页码，若不传递，则默认为第一页
        if (currentPageStr != null && currentPageStr.length() > 0) {
            currentPage = Integer.parseInt(currentPageStr);
        } else {
            currentPage = 1;
        }

        int pageSize = 0;//每页显示参数，如果不传递，默认每页显示5条记录
        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            pageSize = Integer.parseInt(pageSizeStr);
        } else {
            pageSize = 5;
        }

        //3.调用service查询PageBean对象
        PageBean<Route> pb = service.pageQuery(cid, currentPage, pageSize, rname);
        //4.将pageBean对象序列化为json并返回
        writeValue(pb, response);
    }

    /**
     * 根据id查询一个旅游线路的详细信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(@NotNull HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //1.接收参数id
        String rid = request.getParameter("rid");
        //2.调用service查询
        Route route = service.findOne(rid);
        //3.返回json响应信息
        writeValue(route,response);
    }

    /**
     * 判断当前登录用户是否收藏该线路
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(@NotNull HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //1.接收参数id
        String rid = request.getParameter("rid");
        //2.获取当前登录的用户user
        User user = (User) request.getSession().getAttribute("user");
        int uid;//用户的id
        if (user == null){
            //用户尚未登录
            uid = 0;
        } else {
            //用户已经登录
            uid = user.getUid();
        }
        //3.调用FavoriteService查询是否收藏
        boolean flag = service2.isFavorite(rid, uid);
        //4.写回客户端
        writeValue(flag,response);
    }

    /**
     * 添加收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addFavorite(@NotNull HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //1.接收参数rid
        String rid = request.getParameter("rid");
        //2.获取当前登录的用户
        User user = (User) request.getSession().getAttribute("user");
        int uid;//用户的id
        if (user == null){
            //用户尚未登录
            return;
        } else {
            //用户已经登录
            uid = user.getUid();
        }
        //3.调用service添加
        service2.add(rid,uid);
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void remFavorite(@NotNull HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //1.接收参数rid
        String rid = request.getParameter("rid");
        //2.获取当前登录的用户
        User user = (User) request.getSession().getAttribute("user");
        int uid = user.getUid();
        //3.调用service添加
        service2.rem(rid,uid);
    }
}
