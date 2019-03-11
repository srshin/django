from .models import Book, BookModelForm
from django.views.generic.edit import CreateView, UpdateView, DeleteView
from django.views.generic import ListView, DetailView
import json
from django.views.decorators.csrf import csrf_exempt
from django.http import HttpResponse
from django.core.serializers import serialize
from .forms import BookForm
from django.shortcuts import render, redirect, resolve_url, reverse
from django.shortcuts import get_object_or_404


# Create your views here.
# 자동으로 create하고나서 success인 경우 model의 get_absolute_url로 이동함. 
#book_list = ListView.as_view(model =  Book, paginate_by=10) # #object_list를 가지고 book_list.html로 render 
book_detail = DetailView.as_view(model = Book)  #object를 가지고 book_detail.html로 render 
#book_new = CreateView.as_view(model =  Book, fields='__all__') # book_form.html 
#book_edit = UpdateView.as_view(model = Book, fields='__all__') # book_form.html
book_del = DeleteView.as_view(model =  Book, success_url='/book/') # book_confirm_delete.html

def book_edit (request,pk):
    book=get_object_or_404(Book, id=pk)
    if request.method == 'POST' :
        form = BookModelForm(request.POST, request.FILES, instance=book) 
        if form.is_valid() :
            book = form.save(commit= False)
            book.ip = '.'.join(request.META['REMOTE_ADDR'].split('.')[:-1])+'.88'
            book.save()
            return redirect('/book/')
    else : #get방식
        form = BookModelForm(instance=book)
    return render(request, 'book/book_form.html', {'form':form})
    
def book_new(request) :
    if request.method=='GET' :
        #form = BookForm()
        form = BookModelForm()
    else : 
        #ModelForm ... commit 지연
        form = BookModelForm(request.POST, request.FILES)
        if form.is_valid():
            book = form.save(commit=False)
            book.ip = request.META['REMOTE_ADDR']
            book.save()
            #방법 5
            #Book.objects.create(**form.cleaned_data)

            #방법 4
            '''
            Book.objects.create(title = form.cleaned_data['title']
            ,author = form.cleaned_data['author']
            ,publisher = form.cleaned_data['publisher'])
            '''
            #방법 3
            '''
            book = Book (title = form.cleaned_data['title']
            ,author = form.cleaned_data['author']
            ,publisher = form.cleaned_data['publisher'])
            book.save()
            '''

            #방법 2
            '''
            book = Book()
            book.title = form.cleaned_data['title']
            book.author = form.cleaned_data['author']
            book.publisher = form.cleaned_data['publisher']
            book.save()
            '''

            #방법 1
            '''
            print('request POST ',request.POST)
            print(form)
            print(form.cleaned_data) #{}
            form.save()
            '''
            return redirect(reverse('book:list'))
            
    return render(request, 'book/book_form.html', {'form':form})


class BookListView(ListView):
    model=Book
    paginate_by=10
    template_name='book/book_list.html'

    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)
        paginator = context['paginator']
        page_numbers_range = 5  # Display only 5 page numbers
        max_index = len(paginator.page_range)

        page = self.request.GET.get('page')
        current_page = int(page) if page else 1

        start_index = int((current_page - 1) / page_numbers_range) * page_numbers_range
        end_index = start_index + page_numbers_range
        if end_index >= max_index:
            end_index = max_index

        page_range = paginator.page_range[start_index:end_index]
        context['page_range'] = page_range
        return context
book_list= BookListView.as_view()

#Ajax에서 403 오류 막기
@csrf_exempt
def searchData(request):
    data = request.POST['mydata'] #book.id
    print(data)
    book = Book.objects.get(id=data)
    #books = Book.objects.filter(title__contains=data)

    #data2={'msg':book.title} # 문자 보내기
    #data2={'msg':book.publication_date.strftime('%Y-%m-%d')} #date -> str
    serialize_book = serialize('json', [book, ]) #객체 보내기 iterable만 됨. 따라서 list로 만들어야 함. 
    serialize_book = serialize_book.strip('[]') #한건이므로 list없애기
    #serialize_book = serialize('json', books) #객체 보내기 iterable만 됨. 따라서 list로 만들어야 함. 

    print(serialize_book)
    return HttpResponse(json.dumps(serialize_book), 'application/json')
