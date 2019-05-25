# djangoapp
django-from the scratch to the deployment  
## Django tutorial
https://docs.djangoproject.com/ko/2.2/intro/tutorial01/

0. activate virtual env
(base) d:\BigData\Git\djangoapp>conda activate encore

1. Create a project
```
(encore) d:\BigData\Git\djangoapp>python -m django --version
(encore) d:\BigData\Git\djangoapp>django-admin startproject mysite
(encore) d:\BigData\Git\djangoapp>cd mysite
(encore) d:\BigData\Git\djangoapp\mysite>python manage.py runserver
Starting development server at http://127.0.0.1:8000/
```
2. Create a app (poll)  
```
(encore) D:\BigData\Git\aws-django\mysite>python manage.py startapp polls
mysite\polls\views.py 수정
mysite\polls\urls.py 생성
mysite\mysite\urls.py 수정
(encore) D:\BigData\Git\aws-django\mysite>python manage.py runserver
접속 : http://127.0.0.1:8000/polls/
```
3. Database Setup  
https://docs.djangoproject.com/en/2.1/ref/settings/#databases  
3.1 Database생성 root/1234  
```
mysql> show databases;
mysql> create database polls;
mysql> create database maratron character set utf8
mysql> use polls
```
3.2 code수정  
mysite\mysite\settings.py 수정  
3.3  db반영  
```
(encore) D:\BigData\Git\aws-django\mysite>python manage.py migrate
```
4. Create models  
4.1 model 추가   
mysite\polls\models.py 수정  
4.2 app 등록  
mysite\mysite\settings.py 수정  
4.3 migration생성 : model 변경 반영  
```
(encore) D:\BigData\Git\aws-django\mysite>python manage.py makemigrations polls
(encore) D:\BigData\Git\aws-django\mysite>python manage.py showmigrations polls
(encore) D:\BigData\Git\aws-django\mysite>python manage.py migrate --fake tts zero
```
4.4 생성된 migration을 db에 반영 
```
(encore) D:\BigData\Git\aws-django\mysite>python manage.py migrate polls
```
5. Create admin user  
5.1 수퍼유저 생성 admin/1234  
```
(encore) D:\BigData\Git\aws-django\mysite>python manage.py createsuperuser 
(encore) D:\BigData\Git\aws-django\mysite>python manage.py runserver
```
접속 : http://127.0.0.1:8000/admin/login  
5.2 admin에 poll app model 등록    
mysite\polls\admin.py 수정  
접속 : http://127.0.0.1:8000/admin/login  
6. Manage static files  
6.1 create folder under app : templates, static    
mysite\polls\templates\polls\index.html    
mysite\polls\static\polls\style.css    


## 앱 엔진 app engine 표준환경에서 장고 배포 
* 공식문서 
https://cloud.google.com/python/django/appengine?hl=ko
* 참고 블로그
https://medium.com/@BennettGarner/deploying-a-django-application-to-google-app-engine-f9c91a30bd35

### 준비사항
1. 구글프로젝트 만들기
프로젝트이름: srshin 프로젝트 아이디: srshin
주의: 이름과 아이디 동일하게 설정 (이후 아이디를 계속 사용)
2. 결제 설정
3. Cloud SDK 설치
4. API 사용설정

### Database mysql 설치 
1. SQL Proxy 설치
cloud_sql_proxy.exe
2. Cloud SQL 인스턴스 만들기
인스턴스이름: googlemysql
루트비밀번호: 1234
3. 인스턴스 연결 이름 확인
gcloud sql instances describe googlemysql
인스턴스연결connectionName이름: srshin:asia-northeast1:googlemysql
4. local database 정지(port충돌)
윈도우-> 서비스 -> MySQL 정지
5. Cloud SQL 프록시를 시작
cloud_sql_proxy.exe -instances="srshin:asia-northeast1:googlemysql"=tcp:3306
6. mysql로 접속하여 데이타베이스 생성 (반드시 utf-8으로)
mysql --host 127.0.0.1 --user root --password
mysql> create database maratron character set utf8
mysql> select schema_name, default_character_set_name from information_schema.schemata;

### Local 에서 Cloud SQL migrate
1. delete all .py under migrations folder
2. make new migiration files
python manage.py makemigrations
python manage.py showmigrations
python manage.py migrate
3. server에서 접속 확인
python manage.py runserver
4. 수퍼유저 생성
python manage.py createsuperuser (admin / 1234) 
5. 강제 반영
python manage.py migrate --fake tts zero

### Deploy app to Google App Engine
1. /mysite/settings.py 수정
* DB관련
* DEBUG = False
* ALLOWED_HOSTS = ['*']
2. gather static files
(encore) D:\BigData\Git\aws-django\mysite>python manage.py collectstatic
3. create files for google app engine
/app.yaml
/main.py
/requirements.txt
4. deploy the app
D:\BigData\Git\aws-django\mysite>gcloud app deploy
gcloud app browse
gcloud app logs tail -s default



