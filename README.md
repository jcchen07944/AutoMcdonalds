# AutoMcdonalds

## Login Json  
*By decompiling tw.com.mcddaily apk*

Key        | Value
-----------|-------------------------
account    | account  
password   | password
OrderNo    | DeviceUUID + yyyyMMddHHmmssSSS  
mask       | encrypt(Build.VERSION.RELEASE + Build.MODEL + DeviceUUID + yyyy/MM/dd HH:mm:ss + appVersion + account + password + OrderNo + "Android")

InputData = Build.VERSION.RELEASE + Build.MODEL + DeviceUUID + yyyy/MM/dd HH:mm:ss + appVersion + account + password + OrderNo + "Android"  
PreProcessData = "McD" + InputData + "App"  
DESEncrypt = DES(MD5(PreProcessData), key, iv)  
Base64Encode = base64_encode(DESEncrypt)  
encrypt = Base64Encode[0:4] + MD5(PreProcessData) + Base64Encode[len-4:len]  
