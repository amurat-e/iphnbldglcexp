@echo off
set INPFILE="D:\My Downloads\export.xml"
set OUTFILE="C:\Users\Murat ERGIN\Desktop\AME\bldexp.xls"
set JAVA_HOME=D:\jdevstudio10133\jdk\bin
set CLASSPATH=D:\work\jdev\iphnbldglcexp\ipbldglccexp\classes
%JAVA_HOME%\java.exe -client -classpath %CLASSPATH% iphnbldexp %INPFILE% %OUTFILE%
pause