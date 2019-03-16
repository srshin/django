from django.shortcuts import render, redirect, reverse
from django.views.generic import CreateView, UpdateView, DeleteView,ListView,DetailView
from .models import Book
from django.http import HttpResponse
# Create your views here.

#book_list = ListView.as_view(model=Book, paginate_by=10  )
book_detail = DetailView.as_view(model=Book)
#book_new = CreateView.as_view(model=Book, fields=['title','author','publisher'])
#book_edit = UpdateView.as_view(model=Book, fields='__all__')
book_delete = DeleteView.as_view(model=Book, success_url='/book/' )

from .forms import BookForm
from .models import BookModelForm

from django.shortcuts import get_object_or_404
def book_edit(request, pk):
    book = get_object_or_404(Book, id=pk)
    if request.method == 'POST':
        form = BookModelForm(request.POST, request.FILES, instance=book)
        if form.is_valid():
            book = form.save(commit=False)
            book.ip = '.'.join(request.META['REMOTE_ADDR'].split('.')[:-1]) + ".88"
            book.save()
            return redirect('/book/')
    else:
        form = BookModelForm(instance=book)
    return render(request, 'book/book_form.html', {'form':form})       





def book_new(request):
    if request.method=='GET':
        #form = BookForm()
        form = BookModelForm()
    else:
        print("저장한다......")
        #form = BookForm(request.POST, request.FILES)
        form = BookModelForm(request.POST, request.FILES)
        if form.is_valid():

            #ModelForm.......commit지연
            book = form.save(commit=False)
            book.ip = request.META['REMOTE_ADDR']
            book.save()
            return redirect(reverse('book:list'))   

            
            #방법5
            #Book.objects.create(**form.cleaned_data)
            #return redirect(reverse('book:list'))
            '''
            #방법4
            Book.objects.create(title = form.cleaned_data['title'],
                        author = form.cleaned_data['author'], 
                        publisher = form.cleaned_data['publisher'])
            return redirect(reverse('book:list'))                        
            '''
            '''
            #방법3
            book = Book(title = form.cleaned_data['title'],
                        author = form.cleaned_data['author'], 
                        publisher = form.cleaned_data['publisher'])
            book.save()
            return redirect(reverse('book:list'))
            '''

            '''
            #방법2
            book = Book()
            book.title = form.cleaned_data['title']
            book.author = form.cleaned_data['author']
            book.publisher = form.cleaned_data['publisher']
            book.save()
            return redirect(reverse('book:list'))
            '''

            
            #방법1
            #form.save2()  #forms.py의 save2메서드 호출 
            return redirect(reverse('book:list'))
    return render(request, 'book/book_form.html', {'form':form})



import json
from django.views.decorators.csrf import csrf_exempt
from django.core.serializers import serialize
#Ajax에서 403오류 막기 
@csrf_exempt
def searchData(request):
    data = request.POST['mydata'] #book.id
    
    book = Book.objects.get(id = data)
    #print(book)
    #data2 = {'msg':book.title}  .....문자보내기 
    #data2 = {'msg':book.publication_date.strftime('%Y-%m-%d')} ...date->str 
    serialize_book = serialize('json', [book, ]) #객체보내기 
    serialize_book = serialize_book.strip('[]') #한건이므로 list없애기 

    #book = Book.objects.filter(title__contains=data)
    #serialize_book = serialize('json', book)
    return HttpResponse(json.dumps(serialize_book), 'appliation/json')


class BookListView(ListView):
    model=Book
    paginate_by=10
    template_name='book/book_list.html'

    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)
        print (context)
        paginator = context['paginator']
        page_numbers_range = 10  # Display 5 page numbers
        max_index = len(paginator.page_range)

        page = self.request.GET.get('page')
        print (self.request)
        current_page = int(page) if page else 1

        start_index = int((current_page - 1) / page_numbers_range) * page_numbers_range
        end_index = start_index + page_numbers_range
        if end_index >= max_index:
            end_index = max_index

        page_range = paginator.page_range[start_index:end_index]
        context['page_range'] = page_range
        return context

book_list = BookListView.as_view()