@echo off
cls
set PRODUCT_NAME=SecureFolder
set PRODUCT_VERSION=0.0.4

call NemolovichInfos.bat

if not exist "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%" (goto createdir) else (goto checklib)
:checklib
if not exist "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/lib" (goto createlib) else (goto checkbin)
:checkbin
if not exist "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/bin" (goto createbin) else (goto checkico)
:checkico
if not exist "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/icons" (goto createico) else (goto cplib)

:createdir
md "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%"
goto checklib
:createlib
md "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/lib"
goto checkbin
:createbin
md "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/icons"
goto checkico
:createbin
md "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/bin"
goto cplib

:cplib
cp "../target/%PRODUCT_NAME%-%PRODUCT_VERSION%-jar-with-dependencies.jar" "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/lib/%PRODUCT_NAME%-%PRODUCT_VERSION%-jar-with-dependencies.jar"

cp "../LICENSE" "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/LICENSE"
cp "../README.md" "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/README.md"
cp "runner.bat" "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/bin/runner.bat"
cp "icons/unlock.ico" "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/icons/unlock.ico"
cp "icons/lock.ico" "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/icons/lock.ico"