#!/bin/bash
cd "$(dirname "$0")"

if [ ! -d "node_modules" ]; then
  echo "Installing dependencies the first time this runs, please wait..."
  npm install || { echo "Install failed. Make sure Node.js is installed: https://nodejs.org"; exit 1; }
fi

echo ""
echo "Starting the demo. Your browser will open automatically."
echo "Leave this terminal open while viewing the site - closing it stops the demo."
echo ""
npm run dev
