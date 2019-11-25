<!doctype html>
<html>
	<head>
	<meta charset="utf-8">
	<title>${name}</title>
	<link href="http://192.168.147.101:43960/pi/video-js.min.css" rel="stylesheet">
	<style>
body {
	background-color: #191919
}
.m {
	width: 960px;
	height: 400px;
	margin-left: auto;
	margin-right: auto;
	margin-top: 100px;
}
</style>
	</head>

	<body>
    <div class="m">
    	<span style="color: white;">${name}</span>
      <video id="my-video" class="video-js" controls preload="auto" width="960" height="400"
		  data-setup="{}">
        <!-- <source src="http://192.168.147.101:43960/vi/2127.mp4" type="video/mp4"> -->
        <source src="${netPath}" type="video/mp4">
        <p class="vjs-no-js"> To view this video please enable JavaScript, and consider upgrading to a web browser that <a href="http://videojs.com/html5-video-support/" target="_blank">supports HTML5 video</a> </p>
      </video>
      <button id="ne" style="text-align: center; margin: 60px 10% 150px ; width: 80% ; height: 50px">下一个</button>
      <div id="realP" style="color: white; font-size: 10px">${realPath}</div>
      <script src="http://192.168.147.101:43960/pi/video.min.js"></script> 
      <script type="text/javascript">
      	console.log("${curr}");
      	console.log("${name}");
      	console.log("${netPath}");
		  //设置中文
		  videojs.addLanguage('zh-CN', {
			  "Play": "播放",
			  "Pause": "暂停",
			  "Current Time": "当前时间",
			  "Duration": "时长",
			  "Remaining Time": "剩余时间",
			  "Stream Type": "媒体流类型",
			  "LIVE": "直播",
			  "Loaded": "加载完毕",
			  "Progress": "进度",
			  "Fullscreen": "全屏",
			  "Non-Fullscreen": "退出全屏",
			  "Mute": "静音",
			  "Unmute": "取消静音",
			  "Playback Rate": "播放速度",
			  "Subtitles": "字幕",
			  "subtitles off": "关闭字幕",
			  "Captions": "内嵌字幕",
			  "captions off": "关闭内嵌字幕",
			  "Chapters": "节目段落",
			  "Close Modal Dialog": "关闭弹窗",
			  "Descriptions": "描述",
			  "descriptions off": "关闭描述",
			  "Audio Track": "音轨",
			  "You aborted the media playback": "视频播放被终止",
			  "A network error caused the media download to fail part-way.": "网络错误导致视频下载中途失败。",
			  "The media could not be loaded, either because the server or network failed or because the format is not supported.": "视频因格式不支持或者服务器或网络的问题无法加载。",
			  "The media playback was aborted due to a corruption problem or because the media used features your browser did not support.": "由于视频文件损坏或是该视频使用了你的浏览器不支持的功能，播放终止。",
			  "No compatible source was found for this media.": "无法找到此视频兼容的源。",
			  "The media is encrypted and we do not have the keys to decrypt it.": "视频已加密，无法解密。",
			  "Play Video": "播放视频",
			  "Close": "关闭",
			  "Modal Window": "弹窗",
			  "This is a modal window": "这是一个弹窗",
			  "This modal can be closed by pressing the Escape key or activating the close button.": "可以按ESC按键或启用关闭按钮来关闭此弹窗。",
			  ", opens captions settings dialog": ", 开启标题设置弹窗",
			  ", opens subtitles settings dialog": ", 开启字幕设置弹窗",
			  ", opens descriptions settings dialog": ", 开启描述设置弹窗",
			  ", selected": ", 选择",
			  "captions settings": "字幕设定",
			  "Audio Player": "音频播放器",
			  "Video Player": "视频播放器",
			  "Replay": "重播",
			  "Progress Bar": "进度小节",
			  "Volume Level": "音量",
			  "subtitles settings": "字幕设定",
			  "descriptions settings": "描述设定",
			  "Text": "文字",
			  "White": "白",
			  "Black": "黑",
			  "Red": "红",
			  "Green": "绿",
			  "Blue": "蓝",
			  "Yellow": "黄",
			  "Magenta": "紫红",
			  "Cyan": "青",
			  "Background": "背景",
			  "Window": "视窗",
			  "Transparent": "透明",
			  "Semi-Transparent": "半透明",
			  "Opaque": "不透明",
			  "Font Size": "字体尺寸",
			  "Text Edge Style": "字体边缘样式",
			  "None": "无",
			  "Raised": "浮雕",
			  "Depressed": "压低",
			  "Uniform": "均匀",
			  "Dropshadow": "下阴影",
			  "Font Family": "字体库",
			  "Proportional Sans-Serif": "比例无细体",
			  "Monospace Sans-Serif": "单间隔无细体",
			  "Proportional Serif": "比例细体",
			  "Monospace Serif": "单间隔细体",
			  "Casual": "舒适",
			  "Script": "手写体",
			  "Small Caps": "小型大写字体",
			  "Reset": "重启",
			  "restore all settings to the default values": "恢复全部设定至预设值",
			  "Done": "完成",
			  "Caption Settings Dialog": "字幕设定视窗",
			  "Beginning of dialog window. Escape will cancel and close the window.": "开始对话视窗。离开会取消及关闭视窗",
			  "End of dialog window.": "结束对话视窗"
			});
		  
		  
		  
		  
		  
		  
			var myPlayer = videojs('my-video');
			videojs("my-video").ready(function(){
				var myPlayer = this;
				myPlayer.play();
			});
			  
		</script> 
		<script src="https://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
    </div>
    
    <script type="text/javascript">
    	$("#realP").click(function()
    	{
    		console.log("${realPath}");
    		let ele = document.getElementById('realP');
    		ele.select();
    		document.execCommand('copy');  		
    	})
    	$("#ne").click(function ()
    	{
    		let k = ${curr} + 1;
    		console.log(k);
    		window.location.href="/video/video?name=" + k;
    	});
    </script>
</body>
</html>
