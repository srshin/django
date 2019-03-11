from django.db import models
from django.shortcuts import reverse
from django import forms
from django.core.validators import MinLengthValidator

# Create your models here.
def minlength10_validator(value) :
    if len (value) <3  :
        raise forms.ValidationError('3자리 이상 가능합니다. ..')

class Book(models.Model) : #db
    title = models.CharField(max_length=50)
    author =models.CharField( max_length=50, validators = [MinLengthValidator(3)]) # 
    publisher =models.CharField( max_length=50, validators=[minlength10_validator]) #사용자가 만듦
    publication_date = models.DateField(auto_now_add=True)
    ip = models.CharField(max_length=50)

    def __str__(self):
        return self.title
    class Meta:
        ordering = ['-id']
    def get_absolute_url(self): #success_url이 없으면 자동 호출됨. 
        return reverse('book:list')


class BookModelForm(forms.ModelForm):
    class Meta : 
        model = Book
        fields = ['title', 'author', 'publisher']
        labels = {'title' : 'ModelForm책제목', 'author':'ModelForm저자', 'publisher': 'ModelForm출판사'}
        help_texts = {'author': '작가 이름을 3자리 이상으로 입력하세요', 'ip': 'ip는 자동으로 추가됩니다. '}
