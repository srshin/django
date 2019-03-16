from django.urls import path
from . import views

app_name = "bookmark"

urlpatterns = [
    path('', views.BookmarkLV.as_view(), name="list"),
    path('<pk>/', views.BookmarkDV.as_view(), name="detail2"),
    #http://127.0.0.1:8000/bookmark/list/
    #http://127.0.0.1:8000/bookmark/5/detail/
]

