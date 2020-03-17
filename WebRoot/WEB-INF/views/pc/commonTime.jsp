<%@page pageEncoding="UTF-8"%>
<div class="layui-inline">
    <input type="checkbox" lay-filter="optTime" name="optTime" title="近一个月" checked="checked" data-time="${nextOneMonth}" lay-filter="optTime"><div class="layui-unselect layui-form-checkbox"><i class="layui-icon layui-icon-ok layui-icon-default"></i></div>
    <input type="checkbox" lay-filter="optTime" name="optTime" title="近两个月" data-time="${nextTwoMonth}" lay-filter="optTime"><div class="layui-unselect layui-form-checkbox layui-form-checked"><i class="layui-icon layui-icon-ok layui-icon-default"></i></div>
    <input type="checkbox" lay-filter="optTime" name="optTime" title="近三个月" data-time="${nextThreeMonth}" lay-filter="optTime"><div class="layui-unselect layui-form-checkbox"><i class="layui-icon layui-icon-ok layui-icon-default"></i></div>
</div>
