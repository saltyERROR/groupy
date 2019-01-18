:del all files except src

assoc .g=txtfile
cd ..\lib\src
javac io\*.java -d ..\
cd ..\..\pcs\src
javac Processor.java -d ..\
cd ..\..\rtm\src
javac Runner.java -d ..\
cd ..\..\bin