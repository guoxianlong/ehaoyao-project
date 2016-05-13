<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style type="text/css">

</style>

<script>
    $(document).ready(function(){
    	$('.selectpicker').selectpicker();
    });
</script>

<div>
	<div class="pull-left" style="width:150px;">
		<img class="img-thumbnail" src="${member.headImageUrl!=null && member.headImageUrl!='' && member.headImageUrl!='null'?member.headImageUrl:'http://design.ehaoyao.com/static/images/default.jpg'}"
			onerror="this.src='http://design.ehaoyao.com/static/images/default.jpg';this.onerror=null;" alt="头像"
			style="width: 120px; height: 120px;" />
		<div class="tr_inline">
			<div class="td_item item_title4">会员等级：</div>
			<div class="td_item" style="padding-top:5px;" >${memberGrade}</div>
			<div class="clearfix"></div>
		</div>
		<div class="tr_inline">
			<div class="td_item item_title4">活跃度：</div>
			<div class="td_item" style="padding-top:5px;" >积极</div>
			<div class="clearfix"></div>
		</div>
		<div class="tr_inline">
			<div class="td_item item_title4">注册日期：</div>
			<div class="td_item" style="padding-top:5px;" >2015/01/22</div>
			<div class="clearfix"></div>
		</div>
	</div>
	<div class="pull-left" style="width:350px;">
		<h4 class="pull-left" >官网会员档案</h4>
		<div class="pull-right">
			<button class="btn btn-info btn-sm">保存</button>
		</div>
		<div class="clearfix"></div>
		<div style="border:solid 1px #DDD;border-radius:4px;">
			<div class="tr_inline">
				<div class="td_item item_title4">用户名：</div>
				<div class="td_item" style="padding-top:5px;" >${member.memberName}</div>
				<div class="td_item item_title4">会员编号：</div>
				<div class="td_item" style="padding-top:5px;" ></div>
				<div style="clear:both;"></div>
			</div>
			<div class="tr_inline">
				<div class="td_item item_title4">昵称：</div>
				<div class="td_item input_div">
					<input type="text" id="" name="" value="" class="form-control"/>
				</div>
				<div style="clear:both;"></div>
			</div>
			<div class="tr_inline">
				<div class="td_item item_title4">年龄：</div>
				<div class="td_item input_div">
					<input type="text" id="" name="" value="" class="form-control"/>
				</div>
				<div style="clear:both;"></div>
			</div>
			<div class="tr_inline">
				<div class="td_item item_title4">性别：</div>
				<div class="td_item input_div">
					<input type="text" id="" name="" value="" class="form-control"/>
				</div>
				<div style="clear:both;"></div>
			</div>
			<div class="tr_inline">
				<div class="td_item item_title4">生日：</div>
				<div class="td_item input_div">
					<input type="text" id="" name="" value="" class="form-control"/>
				</div>
				<div style="clear:both;"></div>
			</div>
			<div class="tr_inline">
				<div class="td_item item_title4">居住地：</div>
				<div class="td_item input_div">
					<select class="selectpicker form-control">
						<option value="">--请选择--</option>
					</select>
					<select class="selectpicker form-control">
						<option value="">--请选择--</option>
					</select>
					<select class="selectpicker form-control">
						<option value="">--请选择--</option>
					</select>
				</div>
				<div style="clear:both;"></div>
			</div>
			<div class="tr_inline">
				<div class="td_item item_title4">QQ号：</div>
				<div class="td_item input_div">
					<input type="text" id="" name="" value="" class="form-control"/>
				</div>
				<div class="td_item item_title4">状态：</div>
				<div class="td_item" style="padding-top:5px;">未绑定</div>
				<div style="clear:both;"></div>
			</div>
			<div class="tr_inline">
				<div class="td_item item_title4">电子邮件：</div>
				<div class="td_item input_div">
					<input type="text" id="" name="" value="" class="form-control"/>
				</div>
				<div class="td_item item_title4">状态：</div>
				<div class="td_item" style="padding-top:5px;">未绑定</div>
				<div style="clear:both;"></div>
			</div>
			<div class="tr_inline">
				<div class="td_item item_title4">手机号码：</div>
				<div class="td_item input_div">
					<input type="text" id="" name="" value="" class="form-control"/>
				</div>
				<div class="td_item item_title4">状态：</div>
				<div class="td_item" style="padding-top:5px;">未绑定</div>
				<div style="clear:both;"></div>
			</div>
		</div>
	</div>
	<div class="pull-left">2</div>
	<div class="clearfix"></div>
</div>