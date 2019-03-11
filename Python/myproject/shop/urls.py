from django.urls import path, re_path
from . import views
from django.views.generic.base import RedirectView


app_name = 'shop'

urlpatterns = [
    path ('thanks/', views.success),
    path ('contact/', views.ContactView.as_view()),
    path ('go/', RedirectView.as_view(url='https://djangoproject.com')),
    path ('go2/', RedirectView.as_view(url='/shop/home')),
    path ('new/', views.article_new, name='new'),
    path ('edit/<pk>', views.article_edit, name='edit'), 
    path ('del/<pk>', views.article_del, name='del'), 

    path('', views.article_list, name='list' ),
    path('mine/', views.MyView.as_view()),
    path('home/', views.HomePageView.as_view()),
#    path('detail/<pk>/', views.article_detail, name="detail"),
    path('detail/<pk>/', views.ArticleDV.as_view(), name="detail"),
    path('reversetest/', views.article_reversetest, name="rtest2"),
    path('temptest/', views.template_test, name='ttest'),
    path('temptest2/', views.template_test2, name='ttest2'),
    re_path(r'^(?P<year>\d{4})(?P<month>\d{2})(?P<day>\d{2})/$', views.test3, name="test3"),
    path('insert/', views.article_insert, name='insert' ),
    path('delete/', views.article_delete, name='delete' ),
    path('update/', views.article_update, name='update' ),
    path('test1/', views.test1, name="rtest1"),
    path('test2/', views.test2),
    path('test3/', views.test3),
    #path('test4/', views.test4),

    
]
