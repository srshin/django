from django.urls import path
from . import views

app_name = 'kmong'

urlpatterns = [
    path('', views.index, name="index"),
]

