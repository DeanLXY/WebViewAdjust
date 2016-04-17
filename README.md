#关于4.4以上webview 加载网页图片适配问题
google老大原话  

	NARROW_COLUMNS and SINGLE_COLUMN no longer supported
	
	The NARROW_COLUMNS value for WebSettings.LayoutAlgorithm 
	is not be supported in the new WebView.
	
	Caution: These APIs are not supported on Android 4.4 and higher at all. 
	Even if your targetSdkVersion is set to "18" or lower, these APIs have no effect.
意思就是4.4以上不能使用单向排列了 改成了TEXT_AUTOSIZING


![适配前](https://github.com/wangxujie/WebViewTest/blob/master/screenshots/1.gif)


按照屏幕适配

![适配后](https://github.com/wangxujie/WebViewTest/blob/master/screenshots/2.gif)