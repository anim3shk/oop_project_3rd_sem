@echo off
setlocal
cd /d "%~dp0"

where javac >nul 2>nul
if errorlevel 1 (
    echo Java Development Kit not found. Install a JDK 8 or newer and make sure javac is on PATH.
    pause
    exit /b 1
)

if not exist build mkdir build
javac -encoding UTF-8 -d build *.java
if errorlevel 1 (
    echo Build failed.
    pause
    exit /b 1
)

java -cp build RestaurantBillingApp