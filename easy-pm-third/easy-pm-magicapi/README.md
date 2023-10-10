##官网链接
    https://gitee.com/ssssssss-team/magic-api
## 模块添加对应依赖
```xml
<dependency>
    <groupId>com.mdframework</groupId>
    <artifactId>easy-pm-magicapi</artifactId>
</dependency>
```


## yml文件添加对应配置
```yaml
#api配置web页面入口
magic-api:
  web: /magic/web
  prefix: /magic-api  # 接口前缀
  resource:
    type: database  # 配置接口存储方式，这里选择存在数据库中
    table-name: magic_api_file  # 数据库中的表名
    readonly: false # 是否是只读模式
  swagger-config:
     # 资源名称
    name: MagicAPI
    # 资源描述
    description: MagicAPI v1.0 接口文档
    # 资源位置
    location: /v2/api-docs/magic-api/swagger2.json
    # 文档版本
    version: 1.0
    # 文档标题
    title: MagicAPI v1.0 接口文档
  response: |- #配置JSON格式，格式为magic-script中的表达式
    {
      code: code,
      msg: message,
      data,
      timestamp,
      requestTime,
      executeTime,
    }
  response-code-config:
    success: 200 #执行成功的code值
    invalid: 400 #参数验证未通过的code值
    exception: -1 #执行出现异常的code值
  security-config:  # 安全配置
    username: admin # 登录用的用户名
    password: admin123 # 登录用的密码
#    location: classpath:magic-api
# 其它配置请参考 https://ssssssss.org/config/

```
###数据库添加表
```sql
CREATE TABLE `magic_api_file` (
   `file_path` varchar(512) NOT NULL,
   `file_content` mediumtext,
   PRIMARY KEY (`file_path`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
```


###启动访问地址：
    http://host:port/${contextPath}/magic/web/index.html
    
####注意：
    1、如果放在admin，记得要放行，在SecurityConfig ，已经配置好，可以参考
 ```java
    .antMatchers("/magic/web/**").anonymous()
```
