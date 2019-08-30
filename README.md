# AutoMcdonalds

## Login Json  
*By decompiling tw.com.mcddaily apk*

Key        | Value
-----------|-------------------------
account    | account  
password   | password
OrderNo    | DeviceUUID + yyyyMMddHHmmssSSS  
mask       | encrypt(appVersion + account + password + OrderNo + "Android" + Build.VERSION.RELEASE + Build.MODEL + DeviceUUID + yyyy/MM/dd HH:mm:ss)
