## 一键部署流程
1. 将开发完毕的数据库脚本放入`database`目录下，重命名为`init.sql`, 提前修改好`admin`的密码。
2. 将`.env`文件内所需的内容填充完整，注意`publicKey`需要与前端配置保持一致。
3. 将前端工程配置好`publicKey`后打包，将打包后的文件放入`nginx/html`目录下。
4. 生产环境需采用`prod`模式启动，将会强制校验弱密码，未修改`admin`密码将会无法登陆。
5. 执行`docker compose up -d`命令启动容器即可。