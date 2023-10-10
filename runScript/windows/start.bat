@echo off 
::if "%1" == "h" goto begin 
::mshta vbscript:createobject("wscript.shell").run("%~nx0 h",0)(window.close)&&exit 
:::begin

set SERVICEAPP_NAME=easy-pm-admin-1.0.0.jar
set SERVICEAPP_DEPHOME=D:\springboot\zhukangkangtwo\easy-pm-admin
set SERVICEAPP_LOGHOME=%SERVICEAPP_DEPHOME%\logs

::config for 4C8G
set JAVA_OPTS=-Xms1024m -Xmx1024m  ^
-XX:PermSize=256M ^
-XX:MaxPermSize=256m ^
-Xss1m ^
-Xmn768m ^
-XX:+AggressiveOpts ^
-XX:+UseBiasedLocking ^
-XX:+CMSParallelRemarkEnabled ^
-XX:+UseConcMarkSweepGC ^
-XX:ParallelGCThreads=4 ^
-XX:SurvivorRatio=4 ^
-XX:TargetSurvivorRatio=85 ^
-verbose:gc ^
-XX:+PrintGCDetails ^
-XX:+PrintGCDateStamps ^
-XX:+PrintHeapAtGC ^
-Xloggc:%SERVICEAPP_LOGHOME%\gc.log ^
-XX:+HeapDumpOnOutOfMemoryError ^
-XX:HeapDumpPath=%SERVICEAPP_LOGHOME%\dump.logs 

start javaw -jar %JAVA_OPTS% %SERVICEAPP_DEPHOME%\%SERVICEAPP_NAME%  --spring.config.location=%SERVICEAPP_DEPHOME%\config\application-prod.yml

exit