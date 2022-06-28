
chcp 65001 


REM caddy file-server --listen  :8080 --browse --root .
REM caddy.exe  --listen :8080   --root  "D:\work_nutz\static-website-blog\"
REM caddy.exe  --listen :8080   --root  "D:\work_nutz\static-website-blog\"
 

cd caddy-server 
caddy.exe  run  -config Caddyfile


pause