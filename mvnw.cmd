@ECHO OFF
SETLOCAL

SET "MVNW_REPOURL=https://repo.maven.apache.org/maven2"
SET "BASE_DIR=%~dp0"
SET "WRAPPER_JAR=%BASE_DIR%.mvn\wrapper\maven-wrapper.jar"
SET "WRAPPER_PROPERTIES=%BASE_DIR%.mvn\wrapper\maven-wrapper.properties"

IF NOT EXIST "%WRAPPER_PROPERTIES%" (
  ECHO Missing %WRAPPER_PROPERTIES% 1>&2
  EXIT /B 1
)

IF NOT EXIST "%WRAPPER_JAR%" (
  ECHO Downloading Maven wrapper jar... 1>&2
  IF NOT EXIST "%BASE_DIR%.mvn\wrapper" mkdir "%BASE_DIR%.mvn\wrapper"
  powershell -Command "Invoke-WebRequest -Uri '%MVNW_REPOURL%/io/takari/maven-wrapper/0.5.6/maven-wrapper-0.5.6.jar' -OutFile '%WRAPPER_JAR%'" || EXIT /B 1
)

SET "JAVA_EXE=java"
IF NOT "%JAVA_HOME%"=="" SET "JAVA_EXE=%JAVA_HOME%\bin\java"

"%JAVA_EXE%" -jar "%WRAPPER_JAR%" --settings "%BASE_DIR%.mvn\wrapper\settings.xml" %*
ENDLOCAL
