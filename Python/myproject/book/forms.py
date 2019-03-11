#book입력 수정 html에서 사용위해 (client를 위한 것임) 
# 
from django import forms
from .models import Book

def minlength3_validator(value) :
    if len(value) <3 : 
        raise forms.ValidationError('3글자 이상입니다...')


class BookForm (forms.Form) :
    title = forms.CharField(label='forms제목')
    author =forms.CharField(label='forms저자', validators=[minlength3_validator])
    publisher =forms.CharField(label='forms출판사', required=False)

    # def save(self, commit=True):
    #     book = Book(**self.cleaned_data) #{'title':'', 'author':'' ....}
    #     if commit :
    #         book.save()
    #     return book
