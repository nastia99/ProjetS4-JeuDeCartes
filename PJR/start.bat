@ECHO OFF
echo Loading...
cmd /C npm i --only=prod
echo Loading complete
start http://localhost:5000
node lib/start