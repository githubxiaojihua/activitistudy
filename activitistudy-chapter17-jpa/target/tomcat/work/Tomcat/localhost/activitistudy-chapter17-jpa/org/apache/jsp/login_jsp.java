/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2020-11-12 02:52:17 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class login_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(4);
    _jspx_dependants.put("/common/global-taglibs.jsp", Long.valueOf(1605149410776L));
    _jspx_dependants.put("/common/global.jsp", Long.valueOf(1605149410778L));
    _jspx_dependants.put("/common/meta.jsp", Long.valueOf(1605149410784L));
    _jspx_dependants.put("/common/include-base-styles.jsp", Long.valueOf(1605149410783L));
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("\t<title>《Activiti实战》示例第17章-登录系统</title>\n");
      out.write("\t");
      out.write('\n');
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      if (_jspx_meth_c_005fset_005f0(_jspx_page_context))
        return;
      out.write("\n");
      out.write("<script type=\"text/javascript\">\n");
      out.write("\tvar ctx = '");
      out.print(request.getContextPath() );
      out.write("';\n");
      out.write("</script>");
      out.write('\n');
      out.write('	');
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=UTF-8\"/>\r\n");
      out.write("<meta http-equiv=\"Cache-Control\" content=\"no-store\"/>\r\n");
      out.write("<meta http-equiv=\"Pragma\" content=\"no-cache\"/>\r\n");
      out.write("<meta http-equiv=\"Expires\" content=\"0\"/>");
      out.write("\n");
      out.write("    ");
      out.write("\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ctx }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/css/bootstrap.min.css\" type=\"text/css\"/>\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ctx }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/css/style.css\" type=\"text/css\"/>");
      out.write("\n");
      out.write("    <style type=\"text/css\">\n");
      out.write("        body {\n");
      out.write("            padding-top: 40px;\n");
      out.write("            padding-bottom: 40px;\n");
      out.write("            background-color: #eee;\n");
      out.write("        }\n");
      out.write("\n");
      out.write("        .form-signin {\n");
      out.write("            max-width: 330px;\n");
      out.write("            padding: 15px;\n");
      out.write("            margin: 0 auto;\n");
      out.write("        }\n");
      out.write("        .form-signin .form-signin-heading,\n");
      out.write("        .form-signin .checkbox {\n");
      out.write("            margin-bottom: 10px;\n");
      out.write("        }\n");
      out.write("        .form-signin .checkbox {\n");
      out.write("            font-weight: normal;\n");
      out.write("        }\n");
      out.write("        .form-signin .form-control {\n");
      out.write("            position: relative;\n");
      out.write("            height: auto;\n");
      out.write("            -webkit-box-sizing: border-box;\n");
      out.write("            -moz-box-sizing: border-box;\n");
      out.write("            box-sizing: border-box;\n");
      out.write("            padding: 10px;\n");
      out.write("            font-size: 16px;\n");
      out.write("        }\n");
      out.write("        .form-signin .form-control:focus {\n");
      out.write("            z-index: 2;\n");
      out.write("        }\n");
      out.write("        .form-signin input[type=\"email\"] {\n");
      out.write("            margin-bottom: -1px;\n");
      out.write("            border-bottom-right-radius: 0;\n");
      out.write("            border-bottom-left-radius: 0;\n");
      out.write("        }\n");
      out.write("        .form-signin input[type=\"password\"] {\n");
      out.write("            margin-bottom: 10px;\n");
      out.write("            border-top-left-radius: 0;\n");
      out.write("            border-top-right-radius: 0;\n");
      out.write("        }\n");
      out.write("\n");
      out.write("    </style>\n");
      out.write("\n");
      out.write("    <script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ctx }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/js/common/jquery.js\" type=\"text/javascript\"></script>\n");
      out.write("    <script type=\"text/javascript\">\n");
      out.write("        $(function() {\n");
      out.write("            $('#username').focus();\n");
      out.write("        });\n");
      out.write("    </script>\n");
      out.write("</head>\n");
      out.write("\n");
      out.write("<body>\n");
      out.write("<div class=\"container\">\n");
      out.write("\n");
      out.write("    <div class=\"row\">\n");
      out.write("        <div class=\"col-xs-6 col-xs-offset-4\">\n");
      out.write("            <h2>第17章—《集成JPA》配套示例</h2>\n");
      out.write("        </div>\n");
      out.write("    </div>\n");
      out.write("\n");
      out.write("    <div class=\"row\">\n");
      out.write("        <div class=\"col-xs-4 col-xs-offset-4\">\n");
      out.write("            <form action=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ctx }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/user/logon\" class=\"form-signin\" role=\"form\">\n");
      out.write("                <input type=\"text\" id=\"username\" name=\"username\" class=\"form-control\" placeholder=\"用户名\" required autofocus>\n");
      out.write("                <input type=\"password\" id=\"password\" name=\"password\" class=\"form-control\" placeholder=\"密码\" required>\n");
      out.write("                <button class=\"btn btn-lg btn-primary btn-block\" type=\"submit\">登录</button>\n");
      out.write("            </form>\n");
      out.write("        </div>\n");
      out.write("    </div>\n");
      out.write("\n");
      out.write("    <hr/>\n");
      out.write("    <div class=\"row\">\n");
      out.write("        <div class=\"col-xs-6 col-xs-offset-4\">\n");
      out.write("            <p class=\"text-info\">\n");
      out.write("                如果按照书上的用户名、密码登录失败请用终端进入 <strong>chapter17-jpa</strong> 项目目录执行：\n");
      out.write("                <br/><code>mvn antrun:run -Pinit-db</code>\n");
      out.write("            </p>\n");
      out.write("        </div>\n");
      out.write("        <hr>\n");
      out.write("    </div>\n");
      out.write("\n");
      out.write("    <div class=\"row\">\n");
      out.write("        <div class=\"col-xs-6 col-xs-offset-4\">\n");
      out.write("            <h4 class=\"text-info\">用户与角色列表（密码：000000）</h4>\n");
      out.write("            <table class=\"table table-hover\">\n");
      out.write("                <thead>\n");
      out.write("                <tr>\n");
      out.write("                    <th>#</th>\n");
      out.write("                    <th>用户名</th>\n");
      out.write("                    <th>角色</th>\n");
      out.write("                    <th>部门</th>\n");
      out.write("                </tr>\n");
      out.write("                </thead>\n");
      out.write("                <tbody>\n");
      out.write("                <tr>\n");
      out.write("                    <td>1</td>\n");
      out.write("                    <td>henry</td>\n");
      out.write("                    <td>系统管理员</td>\n");
      out.write("                    <th>IT部</th>\n");
      out.write("                </tr>\n");
      out.write("                <tr>\n");
      out.write("                    <td>2</td>\n");
      out.write("                    <td>bill</td>\n");
      out.write("                    <td>总经理</td>\n");
      out.write("                    <td>总经理室</td>\n");
      out.write("                </tr>\n");
      out.write("                <tr>\n");
      out.write("                    <td>3</td>\n");
      out.write("                    <td>jenny</td>\n");
      out.write("                    <td>人事经理</td>\n");
      out.write("                    <td>人事部</td>\n");
      out.write("                </tr>\n");
      out.write("                <tr class=\"success\">\n");
      out.write("                    <td>4</td>\n");
      out.write("                    <td>kermit</td>\n");
      out.write("                    <td>部门经理</td>\n");
      out.write("                    <td>市场部</td>\n");
      out.write("                </tr>\n");
      out.write("                <tr class=\"success\">\n");
      out.write("                    <td>5</td>\n");
      out.write("                    <td>eric</td>\n");
      out.write("                    <td>普通职员</td>\n");
      out.write("                    <td>市场部</td>\n");
      out.write("                </tr>\n");
      out.write("                <tr class=\"success\">\n");
      out.write("                    <td>6</td>\n");
      out.write("                    <td>tom</td>\n");
      out.write("                    <td>普通职员</td>\n");
      out.write("                    <td>市场部</td>\n");
      out.write("                </tr>\n");
      out.write("                <tr class=\"info\">\n");
      out.write("                    <td>7</td>\n");
      out.write("                    <td>andy</td>\n");
      out.write("                    <td>普通职员</td>\n");
      out.write("                    <td>业务部</td>\n");
      out.write("                </tr>\n");
      out.write("                <tr class=\"info\">\n");
      out.write("                    <td>8</td>\n");
      out.write("                    <td>amy</td>\n");
      out.write("                    <td>普通职员</td>\n");
      out.write("                    <td>业务部</td>\n");
      out.write("                </tr>\n");
      out.write("                <tr class=\"danger\">\n");
      out.write("                    <td>9</td>\n");
      out.write("                    <td>tony</td>\n");
      out.write("                    <td>财务人员</td>\n");
      out.write("                    <td>财务部</td>\n");
      out.write("                </tr>\n");
      out.write("                <tr class=\"danger\">\n");
      out.write("                    <td>10</td>\n");
      out.write("                    <td>lily</td>\n");
      out.write("                    <td>出纳员</td>\n");
      out.write("                    <td>财务部</td>\n");
      out.write("                </tr>\n");
      out.write("                <tr>\n");
      out.write("                    <td>11</td>\n");
      out.write("                    <td>thomas</td>\n");
      out.write("                    <td>后勤人员</td>\n");
      out.write("                    <td>后勤部</td>\n");
      out.write("                </tr>\n");
      out.write("                </tbody>\n");
      out.write("            </table>\n");
      out.write("        </div>\n");
      out.write("        <hr>\n");
      out.write("    </div>\n");
      out.write("</div> <!-- /container -->\n");
      out.write("</body>\n");
      out.write("</html>\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_c_005fset_005f0(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:set
    org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_005fset_005f0 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
    _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fset_005f0.setParent(null);
    // /common/global-taglibs.jsp(6,0) name = var type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fset_005f0.setVar("ctx");
    // /common/global-taglibs.jsp(6,0) name = value type = javax.el.ValueExpression reqTime = true required = false fragment = false deferredValue = true expectedTypeName = java.lang.Object deferredMethod = false methodSignature = null
    _jspx_th_c_005fset_005f0.setValue(new org.apache.jasper.el.JspValueExpression("/common/global-taglibs.jsp(6,0) '${pageContext.request.contextPath}'",_el_expressionfactory.createValueExpression(_jspx_page_context.getELContext(),"${pageContext.request.contextPath}",java.lang.Object.class)).getValue(_jspx_page_context.getELContext()));
    int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
    if (_jspx_th_c_005fset_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
    return false;
  }
}
