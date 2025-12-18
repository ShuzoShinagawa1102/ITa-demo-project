@ECHO OFF
SETLOCAL

SET "BASE_DIR=%~dp0"
IF "%BASE_DIR:~-1%"=="\" SET "BASE_DIR=%BASE_DIR:~0,-1%"
SET "WRAPPER_DIR=%BASE_DIR%\.mvn\wrapper"
SET "WRAPPER_JAR=%WRAPPER_DIR%\maven-wrapper.jar"
SET "PROPS=%WRAPPER_DIR%\maven-wrapper.properties"

IF NOT EXIST "%PROPS%" (
  ECHO Missing %PROPS%
  EXIT /B 1
)

FOR /F "usebackq tokens=1,* delims==" %%A IN ("%PROPS%") DO (
  IF "%%A"=="wrapperUrl" SET "WRAPPER_URL=%%B"
  IF "%%A"=="distributionUrl" SET "DIST_URL=%%B"
)

IF NOT EXIST "%WRAPPER_DIR%" MKDIR "%WRAPPER_DIR%"

IF NOT EXIST "%WRAPPER_JAR%" (
  ECHO Downloading Maven Wrapper...
  powershell -NoProfile -ExecutionPolicy Bypass -Command ^
    "$u='%WRAPPER_URL%'; $o='%WRAPPER_JAR%'; (New-Object Net.WebClient).DownloadFile($u,$o)"
  IF ERRORLEVEL 1 EXIT /B 1
)

java -Dmaven.multiModuleProjectDirectory="%BASE_DIR%" -classpath "%WRAPPER_JAR%" org.apache.maven.wrapper.MavenWrapperMain %*

ENDLOCAL
