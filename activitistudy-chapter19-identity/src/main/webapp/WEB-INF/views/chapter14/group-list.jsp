<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/common/global.jsp"%>
    <%@ include file="/common/meta.jsp" %>
    <%@ include file="/common/include-base-styles.jsp" %>
    <%@ include file="/common/include-base-scripts.jsp" %>
    <title>组列表--chapter14</title>
    <script type="text/javascript">
        $(function() {
            $('.edit-group').click(function() {
                var $tr = $('#' + $(this).data('id'));
                $('#groupId').val($tr.find('.prop-id').text());
                $('#groupName').val($tr.find('.prop-name').text());
                $('#type').val($tr.find('.prop-type').text());
            });
        });
    </script>
</head>
<body>
<div class="container">
    <ul class="nav nav-pills">
        <li><a href="${ctx}/chapter14/identity/user/list"><i class="glyphicon glyphicon-user"></i>用户管理</a></li>
        <li class="active"><a href="${ctx}/chapter14/identity/group/list"><i class="glyphicon glyphicon-list"></i>组管理</a></li>
    </ul>
    <hr>
    <c:if test="${not empty message}">
        <div id="message" class="alert alert-success">${message}</div>
        <!-- 自动隐藏提示信息 -->
        <script type="text/javascript">
            setTimeout(function() {
                $('#message').hide('slow');
            }, 5000);
        </script>
    </c:if>
    <div class="row">
        <div class="col-md-8">
            <fieldset>
                <legend><small>组列表</small></legend>
                <table width="100%" class="table table-bordered table-hover table-condensed">
                    <thead>
                    <tr>
                        <th>组ID</th>
                        <th>组名称</th>
                        <th>类型</th>
                        <th width="140">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${page.result }" var="group">
                        <tr id="${group.id}">
                            <td class="prop-id">${group.id}</td>
                            <td class="prop-name">${group.name}</td>
                            <td class="prop-type">${group.type}</td>
                            <td>
                                <a class="btn btn-danger btn-xs" href="${ctx}/chapter14/identity/group/delete/${group.id}"><i class="glyphicon glyphicon-remove"></i>删除</a>
                                <a class="btn btn-info btn-xs edit-group" data-id="${group.id}" href="#"><i class="glyphicon glyphicon-pencil"></i>编辑</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <tags:pagination page="${page}" paginationSize="${page.pageSize}"/>
            </fieldset>
        </div>
        <div class="col-md-4">
            <form action="${ctx }/chapter14/identity/group/save" class="form-horizontal" method="post">
                <input type="hidden" name="id"/>
                <fieldset>
                    <legend><small>新增/编辑组</small></legend>
                    <div id="messageBox" class="alert alert-error input-large controls" style="display:none">输入有误，请先更正。</div>
                    <div class="form-group">
                        <label for="groupId" class="col-sm-3 control-label">组ID:</label>
                        <div class="col-sm-6">
                            <input type="text" id="groupId" name="groupId" class="form-control required" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="groupName" class="col-sm-3 control-label">组名:</label>
                        <div class="col-sm-6">
                            <input type="text" id="groupName" name="groupName" class="form-control required" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="type" class="col-sm-3 control-label">组类型:</label>
                        <div class="col-sm-6">
                            <select name="type" id="type" class="form-control required">
                                <option value="security-role">安全角色</option>
                                <option value="feature-role">功能角色</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-sm-offset-3 col-sm-10">
                        <button type="submit" class="btn btn-primary"><i class="glyphicon glyphicon-ok-sign"></i>保存</button>
                        <button type="reset" class="btn"><i class="glyphicon glyphicon-remove"></i>重置</button>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>
</body>
</html>