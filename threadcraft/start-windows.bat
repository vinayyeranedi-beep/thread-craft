@echo off
title THREADCRAFT - Starting demo server...
cd /d "%~dp0"

if not exist "node_modules" (
  echo Installing dependencies the first time this runs, please wait...
  call npm install
  if errorlevel 1 (
    echo.
    echo Install failed. Make sure Node.js is installed: https://nodejs.org
    pause
    exit /b 1
  )
)

echo.
echo Starting the demo. Your browser will open automatically.
echo Leave this window open while viewing the site - closing it stops the demo.
echo.
call npm run dev
pause
