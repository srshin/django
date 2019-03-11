from django.shortcuts import render, redirect, resolve_url
from django.template.loader import render_to_string
from django.urls.base import reverse
from .models import Article
from django.shortcuts import get_object_or_404
from django.http import HttpResponse
from django.views.generic import DetailView
from django.views import View 
from django.views.generic.base import TemplateView
from django.views.generic import ListView
from django.utils import timezone
from .forms import ContactForm
from django.views.generic.edit import CreateView, UpdateView, DeleteView
from django.views.generic.edit import FormView

class ContactView (FormView):
    template_name = 'shop/contact.html'
    form_class = ContactForm 
    success_url = '/shop/thanks/' #urls.py
    def form_valid(self, form):
        print(form)
        print(form.cleaned_data)
        print(form.cleaned_data['name'])
        print(form.is_valid())
        form.send_email()
        return super().form_valid(form)

def success(request):
    return render(request, 'shop/success.html')

# 자동으로 create하고나서 success인 경우 model의 get_absolute_url로 이동함. 
article_new = CreateView.as_view(model = Article, fields='__all__') # article_form.html 
article_edit = UpdateView.as_view(model = Article, fields='__all__') # article_form.html
article_del = DeleteView.as_view(model = Article, success_url='/shop/') # article_confirm_delete.html
# Create your views here.
class Person():
    def __init__(self, name):
        self.name = name
    def say_hello(self):
        return self.name + ",안녕하세요~~~"
    def __str__(self):
        return self.name +"님"

from django.utils import timezone


def template_test(request):
    article = Article.objects.get(id=1)
    myname = '정진'
    people = ['정표','왕기','아라']
    p = Person("정은")

    dt = timezone.now()
    str_dt = dt.strftime("%Y-%m-%d %H:%M:%S")
    past_dt = timezone.datetime(2005,8,31)
    future_dt = timezone.datetime(2019,5,21)
  

    return render(request, 'shop/template_test.html', 
               {'article' : article, 
                'first_name':myname, 
                 'people':people,
                 'person':p,
                 'student_list' : [],
                 'datetime_obj':dt,
                 'dt':dt, "sdt":str_dt, "past_dt":past_dt, "future_dt":future_dt,
                 'value' :[], 'value2' :None, 'value3' : ['a', 'b', 'c']
                 }
               )
def template_test2(request):
    article = Article.objects.get(id=3)
    myname = '정진2'
    people = ['정표2','왕기','아라']
    p = Person("정은2")


    return render(request, 'shop/template_test.html', 
               {'article' : article, 
                'first_name':myname, 
                 'people':people,
                 'person':p
                 }
               )

'''def article_list(request):
    qs = Article.objects.all()
    q = request.GET.get('q','')#request.getParameter('q')
    if q:
        qs = qs.filter(title__contains=q)
    return render(request, 'shop/article_list.html', 
             {'article_list':qs, 'q2':q})
'''
#article_list = ListView.as_view(model=Article, paginate_by=10) # http://127.0.0.1:8000/shop/?page=4
class ArticleListView (ListView):
    model = Article
    paginate_by = 10

    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)
        context["myMessage"] = 'ListView상속' 
        context['today'] = timezone.now() 
        q = self.request.GET.get('q','') #q가 없을 때 뭘 할지 두번째 parameter에서 정해줌. 
        context['q2'] = q
        return context
article_list = ArticleListView.as_view()
    


def article_detail(request, id2):
    #instance = Article.objects.get(id=id2)
    instance = get_object_or_404(Article,id=id2)
    print(instance)
    return render(request, 'shop/article_detail.html', {'article':instance})

def generate_view_fn(model) :
    def view_fn(request, id2):
        instance = get_object_or_404(model, id=id2)
        instance_name = model._meta.model_name
        print('instance name :', instance_name)
        template_name = '{}/{}_detail2.html'.format(model._meta.app_label, instance_name)
        print ('앱 이름은', model._meta.app_label)
        return render(request, template_name, {instance_name:instance}) 
    return view_fn

article_detail = generate_view_fn(Article)
# function 기반 뷰 
# class기반 뷰
'''
class DetailView(object): #object를 생략가능 
    def __init__(self, model):
        self.model = model

    def get_object(self, *args, **kwargs):
        return get_object_or_404(self.model ,id=kwargs["id2"])

    def get_template_name(self):
        instance_name = self.model._meta.model_name
        template_name = '{}/{}_detail2.html'.format(self.model._meta.app_label, 
                             instance_name)
        return template_name
    
    def dispath(self, request,*args, **kwargs ):
        instance_name = self.model._meta.model_name
        return render(request, self.get_template_name(), 
                      {instance_name:self.get_object(*args, **kwargs)})

    @classmethod
    def as_view(cls, model ):

        def view(request, *args, **kwargs):
            self = cls(model) #모델생성 
            return self.dispath(request,*args, **kwargs )
        return view

article_detail = DetailView.as_view(Article)
'''
             

article_detail = DetailView.as_view(model=Article)  #url 요청시 id를 pk로 해야함. 

class ArticleDV(DetailView) :
    model = Article
#article_detail = ArticleDV.as_view()
class MyView(View) :
    def get(self, request, *args, **kwargs) :
        return HttpResponse('MyView의 get메서드 ')


def article_reversetest(request):

    #url만들기
    a = reverse('shop:list')
    print('shop:list와 같이 요청함:', a)

    a = reverse('shop:detail', args=[1])  #/shop/1/detail/
    print('shop:detail와 같이 요청함:', a)

    a = reverse('shop:detail', kwargs={'id2':3})  #/shop/3/detail/
    print('shop:detail와 같이 요청함:', a)

    a = resolve_url('shop:detail', 22)  
    return redirect(a)
    #return redirect('shop:list')  #주소창이 바뀐다. 



def test3(request, year, month, day):
    print(year, month, day)
    return HttpResponse(year)

def article_insert(request):
    aa = Article(title='목요일', body='날씨가좋아요', status='p')
    aa.save()
    Article.objects.create(title='금요일', body='날씨가 더 좋아요', status='p')
    return HttpResponse("입력합니다.")

def article_update(request):
    #aa = Article.objects.get(id=1)
    '''
    aa = Article.objects.first()
    aa.title = "!!!!진짜로 금요일입니다."
    print(aa)
    aa.save()
    '''

    '''
    qs = Article.objects.filter(title__contains='1')
    print(qs)
    for aa in qs:
        aa.title = '1입니다.'
        aa.save()
    '''
    qs = Article.objects.filter(title__contains='3')
    qs.update(title = '3입니다.')
    print("방법1:", qs.query)
    s = str(qs.query)
    print("방법2:",s)
    return HttpResponse("수정합니다.")

from django.db.models import Q
def article_delete(request):
    qs = Article.objects.filter(Q(title__contains='9') | Q(title__contains='5'))
    qs.delete()
    return HttpResponse("삭제합니다.")    

def test1(request):
    response = render(request, 'shop/test1.html', {'mymessage':"render이용하기"})    
    return response

def test2(request):
    s = render_to_string('shop/test1.html', {'mymessage':"render_to_string이용하기"})    
    return HttpResponse(s)

def test3(request):
    if request.method == 'GET':
        #get방식으로 값 읽을때 : request.GET.get('q')
        response = render(request, 'shop/csrf_input.html')    
        return response
    else:
        title = request.POST['title']
        body = request.POST['body']
        status = request.POST['status']
        aa = Article(title=title, body=body, status=status)
        aa.save()
        
        return HttpResponse("입력합니다.")

        
class HomePageView(TemplateView) :
    template_name = 'shop/home.html'
    def get_context_data(self, **kwargs) :
        context = super().get_context_data(**kwargs) 
        context['article_list5'] = Article.objects.all()[:5]
        print(context)
        return context

