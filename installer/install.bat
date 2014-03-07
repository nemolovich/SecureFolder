@echo off
cls
call config.bat
set PRODUCT_ACTION=Installing

call NemolovichInfos.bat

set ERROR_CODE=""

if not exist "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%" (goto createdir) else (goto checklib)
:checklib
if not exist "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/lib" (goto createlib) else (goto checkbin)
:checkbin
if not exist "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/bin" (goto createbin) else (goto checkico)
:checkico
if not exist "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/icons" (goto createico) else (goto checkres)
:checkres
if not exist "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/resources/batchs" (goto createres) else (goto cplib)

:createdir
echo Creating folder %ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/LICENSE
md "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%"
goto checklib
:createlib
echo Creating folder ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/lib
md "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/lib"
goto checkico
:createico
echo Creating folder ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/icons
md "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/icons"
goto checkbin
:createbin
echo Creating folder %ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/bin
md "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/bin"
goto checkres
:createres
echo Creating folder %ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/resources/batchs
md "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/resources/batchs"
goto cplib

:cplib
echo Copying file %ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/lib/%PRODUCT_NAME%-%PRODUCT_VERSION%-jar-with-dependencies.jar
cp "../target/%PRODUCT_NAME%-%PRODUCT_VERSION%-jar-with-dependencies.jar" "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/lib/%PRODUCT_NAME%-%PRODUCT_VERSION%-jar-with-dependencies.jar"

echo Copying file %ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/LICENSE
cp "../LICENSE" "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/LICENSE"
echo Copying file %ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/README.md
cp "../README.md" "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/README.md"
echo Copying file %ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/bin/runner.bat
cp "runner.bat" "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/bin/runner.bat"
echo Copying file %ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/bin/config.bat
cp "config.bat" "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/bin/config.bat"
echo Copying file %ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/bin/secureFodler.properties
cp "../secureFodler.properties" "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/bin/secureFodler.properties"
echo Copying file %ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/icons/unlock.ico
cp "icons/unlock.ico" "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/icons/unlock.ico"
echo Copying file %ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/icons/lock.ico
cp "icons/lock.ico" "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/icons/lock.ico"
echo Copying file %ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/resources/batchs/linux.properties
cp "icons/lock.ico" "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/resources/batchs/linux.properties"
echo Copying file %ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/resources/batchs/win.properties
cp "icons/lock.ico" "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/resources/batchs/win.properties"


echo Installation done!
