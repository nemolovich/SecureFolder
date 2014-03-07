@REM Nemolovich Software Runner
@REM
@REM Application: SecureFolder
@REM Verison: 0.0.4
@REM
@REM Required ENV vars:
@REM   JAVA_HOME - location of a JDK home dir

@echo off
call config.bat

set ERROR_CODE=0

@setlocal

if not "%JAVA_HOME%" == "" goto foundJavaHome

for %%i in (java.exe) do set JAVA_EXEC=%%~$PATH:i

if not "%JAVA_EXEC%" == "" (
  set JAVA_EXEC="%JAVA_EXEC%"
  goto OkJava
)

if not "%JAVA_EXEC%" == "" goto OkJava

echo.
echo ERROR: JAVA_HOME not found in your environment, and no Java
echo        executable present in the PATH.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation, or add "java.exe" to the PATH
echo.
goto error

:foundJavaHome
if EXIST "%JAVA_HOME%\bin\java.exe" goto foundJavaExeFromJavaHome

echo.
echo ERROR: JAVA_HOME exists but does not point to a valid Java home
echo        folder. No "\bin\java.exe" file can be found there.
echo.
goto error

:foundJavaExeFromJavaHome
set JAVA_EXEC="%JAVA_HOME%\bin\java.exe"

:OkJava
if NOT "%APPLICATION_HOME%"=="" goto cleanApplicationHome
set APPLICATION_HOME=%~dp0..
goto run

:cleanApplicationHome
if %APPLICATION_HOME:~-1%==\ set APPLICATION_HOME=%APPLICATION_HOME:~0,-1%

IF EXIST "%APPLICATION_HOME%\lib\%PRODUCT_NAME%-%PRODUCT_VERSION%-jar-with-dependencies.jar" goto run

echo.
echo ERROR: APPLICATION_HOME exists but does not point to a valid install
echo        directory: %APPLICATION_HOME%
echo.
goto error



@REM ==== START RUN ====
:run
echo %APPLICATION_HOME%

set PROJECT_HOME=%CD%

%JAVA_EXEC% -cp "%APPLICATION_HOME%\lib\%PRODUCT_NAME%-%PRODUCT_VERSION%-jar-with-dependencies.jar" fr.nemolovich.apps.%PRODUCT_PACKAGE%.%PRODUCT_NAME% "%*"
if ERRORLEVEL 1 goto error
goto end

:error
set ERROR_CODE=1

:end
@endlocal & set ERROR_CODE=%ERROR_CODE%

goto exit

:returncode
exit /B %1

:exit
call :returncode %ERROR_CODE%