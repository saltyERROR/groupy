@echo off
echo *commands
echo run     : run .txt files as sources in groupy
echo version : echo version of groupy
echo set     : set work-space
echo;
echo *deprecated commands
echo build   : build groupy
echo update  : update groupy
echo;
:loop
echo ^:^: enter a command
set /p ANS="$ "
echo;
call %ANS%
echo;
goto loop