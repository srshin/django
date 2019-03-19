from django.urls import path, re_path
from django.conf.urls import url
from . import views

app_name = "main"

urlpatterns = [
    path('', views.index, name='index'),
    path('major/list/', views.major_list, name='major_list'),
    path('<pk>/major/detail/', views.major_detail, name='major_detail'),
    path('major/new/', views.major_new, name='major_new'),
    path('<pk>/major/edit/', views.major_edit, name='major_edit'),
    path('<pk>/major/delete/', views.major_delete, name='major_delete'),

    path('student/list/', views.student_list, name='student_list'),
    path('<pk>/student/detail/', views.student_detail, name='student_detail'),
    path('student/new/', views.student_new, name='student_new'),
    path('<pk>/student/edit/', views.student_edit, name='student_edit'),
    path('<pk>/student/delete/', views.student_delete, name='student_delete'),


]
