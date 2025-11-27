@echo off
REM JScanLite Runner Script
REM Usage: run.bat [optional: <path-to-java-file-or-directory>]

REM Build the classpath
set CLASSPATH=target\JScanLite-1.0-SNAPSHOT.jar
set CLASSPATH=%CLASSPATH%;%USERPROFILE%\.m2\repository\com\github\javaparser\javaparser-core\3.25.10\javaparser-core-3.25.10.jar

REM Run the application
java -cp "%CLASSPATH%" troyan.adam.App %*