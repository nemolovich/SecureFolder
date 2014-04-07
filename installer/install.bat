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
if not exist "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/icons" (goto createico) else (goto copylib)

:createdir
echo Creating folder %ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/LICENSE
md "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%"
goto checklib
:createlib
echo Creating folder %ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/lib
md "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/lib"
goto checkbin
:createbin
echo Creating folder %ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/bin
md "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/bin"
goto checkico
:createico
echo Creating folder %ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/icons
md "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/icons"
goto copylib

:copylib
echo Copying file %ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/lib/%PRODUCT_NAME%-%PRODUCT_VERSION%.jar
cd ..
cd target
copy /Y "%PRODUCT_NAME%-%PRODUCT_VERSION%-jar-with-dependencies.jar" "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/lib/%PRODUCT_NAME%-%PRODUCT_VERSION%.jar"

cd ..
echo Copying file %ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/LICENSE
copy /Y "LICENSE" "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/LICENSE"
echo Copying file %ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/README.md
copy /Y "README.md" "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/README.md"

cd installer
echo Copying file %ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/bin/runner.bat
copy /Y "runner.bat" "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/bin/runner.bat"
echo Copying file %ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/bin/config.bat
copy /Y "config.bat" "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/bin/config.bat"

cd icons
echo Copying file %ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/icons/unlock.ico
copy /Y "unlock.ico" "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/icons/unlock.ico"
echo Copying file %ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/icons/lock.ico
copy /Y "lock.ico" "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%/icons/lock.ico"
cd ..

echo Installation done!
