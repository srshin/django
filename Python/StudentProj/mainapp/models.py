from django.db import models
from django.shortcuts import reverse
from django import forms
from django.core.validators import MinLengthValidator

# Create your models here.
class Major(models.Model):
    title = models.CharField(max_length=100, verbose_name='전공명')

    def __str__(self):
        return self.title
    class Meta:
        ordering = ['-id']
    def get_absolute_url(self): #success_url이 없는 경우 자동 호출되므로 반드시 정의. 
        return reverse('main:list')
