from django.urls import path, re_path
from django.conf.urls import url


from . import views

app_name = "book"

urlpatterns = [
    path('', views.book_list, name='list' ),
    path('detail/<pk>/', views.book_detail, name="detail"),    
    path ('new/', views.book_new, name='new'),
    path ('edit/<pk>', views.book_edit, name='edit'), 
    path ('del/<pk>', views.book_del, name='del'), 
    path ('searchData/', views.searchData, name='search'), 


]
