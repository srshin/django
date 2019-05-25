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
