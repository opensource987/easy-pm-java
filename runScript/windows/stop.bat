@echo off
setlocal enabledelayedexpansion
set port=8085
for /f "tokens=1-5" %%a in ('netstat -ano ^| find ":%port%"') do (
    if "%%e%" == "" (
        set pid=%%d
    ) else (
        set pid=%%e
    )
    echo !pid!
    taskkill /f /pid !pid! 
    GOTO :OUTFOR
)
:OUTFOR
pause