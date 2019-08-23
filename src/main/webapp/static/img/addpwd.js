 function Encrypt(){
	         var key = CryptoJS.enc.Utf8.parse("abcdefgabcdefg12");   
	         var pwd =document.getElementById("pwd").value
	         var srcs = CryptoJS.enc.Utf8.parse(pwd);  
	         var encrypted = CryptoJS.AES.encrypt(srcs, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7});  
	         document.getElementById("password").value =encrypted.toString();
	         return encrypted.toString();
}