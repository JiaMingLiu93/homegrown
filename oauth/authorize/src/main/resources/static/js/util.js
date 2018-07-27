/*******************************************************************************
 * 
 * 字符串处理工具类
 * 
 ******************************************************************************/
var StringUtil = {
	/**
	 * 纠正url
	 * 
	 * @param url
	 *            待纠正的url
	 * @param flag
	 *            ajax 打开ur，仅当打开新页面的时候设置为true
	 * @returns {String} 纠正后的url
	 */
	fixUrl : function(url) {
		
			return "http://10.74.8.12:8080/portal-web" + "/" + url;
		
	},
	/**
	 * 格式化字符串，使用方式类似java中的String.format()方法
	 **/
	format : function(format) {
		var args = [];
		for (var i = 1; i < arguments.length; i++) {
			args.push(arguments[i]);
		}
		return format.replace(/\{(\d+)\}/g, function(m, i) {
			return args[i];
		});
	},
	/**
	 * 判断字符串是否为空
	 **/
	isNull : function(str) {
		return str == null || !str || typeof str == undefined || str == '';
	},
	/**
	 * HTML转换输出
	 **/
	renderHtml : function(str) {
		if (Util.strIsEmpty(str)) {
			return '';
		}
		return str.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g,
				'&gt;');
	},
	/**
	 * 表格数据为null时不显示
	 **/
	nullToBlank : function(str) {
		if(str == null){
		return str = '';
		}
		else
		return str;
		
	},
	/**
	 * 表格数据为null时不显示
	 **/
	 difStatus : function(str) {
		if(str == null){
		return str = '在职';
		}
		else
		return str = '离职';
		
	},
	/** 
	 * 将传入值转换成整数
	 **/
	parseInteger : function(v) {
		if (typeof v == 'number') {
			return v;
		} else if (typeof v == 'string') {
			var ret = parseInt(v);

			if (isNaN(ret) || !isFinite(ret)) {
				return 0;
			}

			return ret;
		} else {
			return 0;
		}
	},
	/**
	 * 将传入值转换成小数
	 **/
	parseFloat : function(v) {
		if (typeof v == 'number') {
			return v;
		} else if (typeof v == 'string') {
			var ret = parseFloat(v);
			if (isNaN(ret) || !isFinite(ret)) {
				return 0;
			}

			return ret;
		} else {
			return 0;
		}
	},
	/**
	 * 将传入值转换成小数,传入值可以是以逗号(,)分隔的数字，此方法将会过滤掉(,)
	 **/
	parseDotFloat : function(v) {
		if (typeof v == 'number') {
			return v;
		} else if (typeof v == 'string') {
			v = v.replace(/[^\d|.]/g, '');
			v = parseFloat(v);

			if (isNan(v) || !isFinite(v)) {
				return 0;
			}
			return ret;
		} else {
			return 0;
		}
	},
	/***********************************************************************
	 * 
	 * 字符串传换成date类型
	 * 
	 * @str {string}字符串格式表示的日期，格式为：yyyy-mm-dd
	 * @return {Date}由str转换得到的Date对象
	 * 
	 **********************************************************************/
	str2date : function(str) {
		var re = /^(\d{4})\S(\d{1,2})\S(\d{1,2})$/;
		var dt;
		if (re.test(str)) {
			dt = new Date(RegExp.$1, RegExp.$2 - 1, RegExp.$3);
		}
		return dt;
	},
	/**
	 * 计算2个日期之间的天数
	 * 
	 * @day1 {Date}起始日期
	 * @day2 {Date}结束日期
	 * @return day2 - day1的天数差
	 */
	dayMinus : function(day1, day2) {
		var days = Math.floor((day2 - day1) / (1000 * 60 * 60 * 24));
		return days;
	},
	/**
	 * 设置组合列表框选择项
	 * 
	 * @combo 组件
	 * @value 选择项值
	 * @defaultIdx 默认选择项
	 */
	setComboSelected : function(combo, value, defaultIdx) {
		if (typeof combo == 'string') {
			combo = $(combo);
		}
		var idx = defaultIdx;
		if (typeof defaultIdx == 'undefined') {
			idx = -1;
		}
		for (var i = 0, len = combo.options.length; i < len; ++i) {
			var v = combo.options[i].value;
			if (v == value) {
				idx = i;
				break;
			}
		}
		combo.selectedIndex = idx;
	},
	/**
	 * 字符串转换成日期
	 * 
	 * @str 需要转换的字符串
	 */
	str2date : function(str) {
		var re = /^(\d{4})\S(\d{1,2})\S(\d{1,2})$/;
		var dt;
		if (re.test(str)) {
			dt = new Date(RegExp.$1, RegExp.$2 - 1, RegExp.$3);
		}
		return dt;
	},
	/**
	 * 计算2个日期间的天数差
	 * 
	 * @day1 开始时间
	 * @day2 截止时间
	 */
	dayInterval : function(day1, day2) {
		var days = Math.floor((day2 - day1) / (1000 * 60 * 60 * 24));
		return days;
	}
};

// 对Date的扩展，将 Date 转化为指定格式的String   
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，   
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)   
// 例子：   
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423   
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18   
Date.prototype.format = function(fmt)   
{ //author: meizz   
  var o = {   
    "M+" : this.getMonth()+1,                 //月份   
    "d+" : this.getDate(),                    //日   
    "h+" : this.getHours(),                   //小时   
    "m+" : this.getMinutes(),                 //分   
    "s+" : this.getSeconds(),                 //秒   
    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
    "S"  : this.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
}  


/*******************************************************************************
 * 
 * 时间工具类
 * 
 ******************************************************************************/
var DateUtil = {
	/** 日期对象转换为毫秒数 */
	dateToLong : function(date) {
		return date.getTime();
	},
	/** 毫秒转换为日期对象 */
	longToDate : function(time) {
		return new Date(time);
	},
	/** 毫秒转为时间格式字符串 */
	format : function(time, pattern) {
		if (!StringUtil.isNull(time)) {
			return new Date(Number(time)).format(StringUtil.isNull(pattern) ? 'yyyy-MM-dd hh:mm:ss' : pattern);
		}
		return "";
	},
	/** 时间格式：Y-m-d */
	formatYMD : function(time) {
		return DateUtil.format(time, 'yyyy-MM-dd');
	},
	/** 时间格式：Y年m月d日 */
	formatCYMD : function(time) {
		return DateUtil.format(time, 'yyyy年MM月dd日');
	},
	/** 得到两个时间（“yyyy-mm-dd”格式）相差的月数 */
	betweenDateMonth : function(startDate, endDate) {
		var startArray = startDate.split("-");
		var endArray = endDate.split("-");
		var startYear = new Number(startArray[0]);
		var startMonth = new Number(startArray[1]);
		var endYear = new Number(endArray[0]);
		var endMonth = new Number(endArray[1]);
		var diff = 0;
		var diffM = new Number(endMonth - startMonth);
		var diffY = new Number(endYear - startYear);
		if (diffY == -1) {
			diff = -(12 - endMonth + startMonth);
		} else if (diffY != -1 && diffY < 0) {
			diff = -(12 - endMonth + startMonth) + (diffY + 1) * 12;
		} else if (diffY == 0 && diffM < 0) {
			diff = diffM;
		} else if (diffY == 0 && diffM == 0) {
			diff = 0;
		} else if (diffY == 0 && diffM > 0) {
			diff = diffM + 1;
		} else if (diffY == 1 && diffM == 0) {
			diff = 12;
		} else if (diffY == 1 && diffM != 0) {
			diff = 12 - startMonth + endMonth;
		} else if (diffY > 1) {
			diff = (diffY - 1) * 12 + 12 - startMonth + endMonth;
		}
		return diff;
	}
};