@echo off
set CP=src\main\java;lib\*
javac -cp "%CP%" src\main\java\gui\*.java src\main\java\dao\*.java src\main\java\model\*.java src\main\java\util\*.java
if %errorlevel% neq 0 (
    echo Compilation Failed!
    exit /b %errorlevel%
)
echo Compilation Successful.
