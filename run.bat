@echo off
echo Compiling project...
call compile.bat
if %errorlevel% neq 0 (
    echo Compilation Failed! Please check the errors above.
    pause
    exit /b %errorlevel%
)

echo Starting Application...
set CP=src\main\java;lib\*
java -cp "%CP%" gui.LoginGUI