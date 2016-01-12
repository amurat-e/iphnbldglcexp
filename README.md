iphnbldglcexp
-------------------------------------------------------------- 
App Name: iphnbldglcexp
App Type: java
Last Update: 12/01/2016
Writen by A. Murat Ergin
-------------------------------------------------------------- 
Description: 
This application reads xml file that created by iPhone's 
health application and extracts blood glucose data to xls file

Requirtments:
-------------------------------------------------------------
java SE JDK 1.5 or above
External library. It must be include -classpath or project. 
jexcelapi_2_6_12.zip 
If using SE JDK greather thab version JDK 1.5 add lib  
Because it must be compailed JDK and should have source Dir 

Execute (UNIX)
-------------------------------------------------------------
export CLASSPATH=../iphnbldglcexp/.adf:../iphnbldglcexp/ipbldglccexp/classes:/Users/alimuratergin/jdeveloper/mywork/iphnbldglcexp/ipbldglccexp/jexcelapi/jxl.jar
java -classpath $CLASSPATH  iphnbldexp input_file_location_/input_file_name_.xml output_file_location_/output_file_name_.xls


