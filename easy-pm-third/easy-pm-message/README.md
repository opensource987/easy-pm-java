##调试链接
    https://next.api.aliyun.com/api/Dysmsapi/2017-05-25/SendSms?spm=5176.12207334.help.11.5daf1cbe5LySLa&params={%22PhoneNumbers%22:%2215889963910%22,%22TemplateCode%22:%22SMS_193027088%22,%22TemplateParam%22:%22{\%22code\%22,\%221234\%22}%22}&sdkStyle=old&lang=JAVA&tab=DEMO
## 模块添加对应依赖
```xml
<dependency>
    <groupId>com.mdframework</groupId>
    <artifactId>easy-pm-message</artifactId>
</dependency>
```


## yml文件添加对应配置
```yaml
aliyun:
  message:
    signName: xxxx
    domain: dysmsapi.aliyuncs.com
    #验证码短信模板id
    templateCode: SMS_xxxxx
    accessKeyId: xxxxxx
    accessSecret: xxxxxxxxx
```
