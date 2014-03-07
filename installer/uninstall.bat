@echo off
cls
call config.bat
set PRODUCT_ACTION=Uninstalling

call NemolovichInfos.bat

echo Removing folder %ProgramFiles%/NemolovichApps/%PRODUCT_NAME%
rmdir /S /Q "%ProgramFiles%/NemolovichApps/%PRODUCT_NAME%"

echo Uninstallation done!