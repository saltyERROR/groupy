:del all files except src

rem FileExtension
assoc .g=txtfile

rem Processor
cd ..\..\pcs\sys\src
javac *.java -d ..\bin

rem Runtime
cd ..\..\..\rtm\sys\src
javac *.java -d ..\bin

cd ..\..\..\pcs\bin
