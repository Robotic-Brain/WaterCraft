@echo off
set /p ANT_PATH=<antPath.txt
call "%ANT_PATH%/bin/ant"
pause