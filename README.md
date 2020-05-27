# AutoMcdonalds  
Make mcddaily run automatically on pc.   
http://jcchen.csie.org:9010 (For testing only, do not have auto draw lottery feature and not always online.)

## Newest encryption algorithm (since version 2.2.1) 
``` 
InputData = Build.VERSION.RELEASE + Build.MODEL + DeviceUUID + yyyy/MM/dd HH:mm:ss + appVersion + account + password + OrderNo + "Android"  
PreProcessData = "McD" + InputData + "App"  
DESEncrypt = DES(MD5(PreProcessData), key, iv)  
Base64Encode = base64_encode(DESEncrypt)  
Encrypt.encode = Base64Encode[0:4] + MD5(PreProcessData) + Base64Encode[len-4:len]  
``` 
