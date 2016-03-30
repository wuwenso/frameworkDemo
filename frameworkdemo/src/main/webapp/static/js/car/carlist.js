$(function(){
	menu("car");
	var cols=[{title: '车牌号',name: 'licenseNum',ellipsis: true,sortField: 'orderId',width:'50'},
			{title: '车架号',name: 'carNo',ellipsis: true,sortField: 'car_no',width:'80'},
			{title: '车型',name: 'carType',ellipsis: true,sortField: 'car_type',width:'80'},
			{title: '车具号',name: 'carNum',sortField: 'car_num',ellipsis: true,width:'80'},
			{title: '可乘人数',name: 'seatNum',sortField: 'seat_num',ellipsis: true,width:'100'},
			{title: '状态',name: 'status',sortField: 'status',ellipsis: true,width:'100'},
			{title: '操作',isSort: false,width:'50',
				renderer: function(data) {
					var editBtn = $('<a/>', {
						'class': 'btn btn-small btn-operation',
						'custom-event': '', 
						'text': '修改'
					});
					editBtn.on('click', function() {
						openUrl("/routine/queryRoutineOrderDetail?orderId="+data.orderId);
					});
					return $('<div/>').append(editBtn);
			}
		}];
	var table = $('#carList').pagination({
			ajax: {
				url: '/pf/car/carList.do',
				data:function(data){
					$.extend(data,$.getFormValue($("#dataDiv")));
				}
			},
			cols:cols,
			checkRule:function(item,items,index){
				return true;
			},
			checkCol: function() {
				return true;
			},
		});
	$("#dataDiv input,select").on("keyup",function(e){
		if(13==e.keyCode){
			 table.reload();
		}
	});
	$("#searchBtn").on("click",function(){
		table.reload();
	});
	$("#delBtn").on("click",function(){
		var arr=table.getSelect();
		if(0==arr.length){
			$.messager.alert("友情提示","您没有选择任何要删除的车辆信息！");
			return false;
		}
		$.messager.confirm("友情提示","确定要删除选中的车辆信息吗？（删除后不可恢复）","",function(bl){
			if(bl){
				var param={ids:arr};
				$.ajax({
					"url":"/pf/car/delCar.do",
					"data":param,
					"dataType":"json",
					"type":"post",
					"success":function(rs){
						if(rs.status==1){
							table.reload();
						}
						$.messager.alert("友情提示",rs.message);
					}
				});
			}
		});
	});
	
});