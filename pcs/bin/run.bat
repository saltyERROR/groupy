set /p ANS="enter the file name : "
echo;
cd ..\lib
for /f %%a in (..\res\work-space.txt) do (
java Processor "%%a\\%ANS%.txt"
)
cd ..\bin
