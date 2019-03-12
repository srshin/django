from django.urls import path, re_path
from django.conf.urls import url
from . import views

app_name = "main"

urlpatterns = [
    path('', views.index, name='index'),
    path('list/', views.major_list, name='list'),
    path('<pk>/detail/', views.major_detail, name='detail'),
    path('new/', views.major_new, name='new'),
    path('<pk>/edit/', views.major_edit, name='edit'),
    path('<pk>/delete/', views.major_delete, name='delete'),
    path('search/', views.major_search, name='search'),
]
