from django.shortcuts import render
from .models import Major, Student
from django.views.generic.edit import CreateView, UpdateView, DeleteView
from django.views.generic import ListView, DetailView
from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from django.core.paginator import Paginator
# 자동으로 create하고나서 success인 경우 model의 get_absolute_url로 이동함. 

#student_list = ListView.as_view(model =  Student, template_name="mainapp/student_all_list.html", paginate_by=10) # #object_list를 가지고 object_list.html로 render 
student_detail = DetailView.as_view(model = Student, template_name="mainapp/student_view_detail.html")  #object를 가지고 object_detail.html로 render 
student_new = CreateView.as_view(model =  Student, template_name="mainapp/student_input_form.html", fields='__all__') # object_form.html 
student_edit = UpdateView.as_view(model = Student, template_name="mainapp/student_input_form.html", fields='__all__') # object_form.html
student_delete = DeleteView.as_view(model =  Student, template_name="mainapp/student_delete.html", success_url='/main/student/list') # object_confirm_delete.html

#방법1 : Class에서 제공하는 default method를 이용하는 방법
#major_list = ListView.as_view(model =  Major, template_name="mainapp/major_all_list.html", paginate_by=10) # #object_list를 가지고 object_list.html로 render 
major_detail = DetailView.as_view(model = Major, template_name="mainapp/major_view_detail.html")  #object를 가지고 object_detail.html로 render 
major_new = CreateView.as_view(model =  Major, template_name="mainapp/major_input_form.html", fields='__all__') # object_form.html 
major_edit = UpdateView.as_view(model = Major, template_name="mainapp/major_input_form.html", fields='__all__') # object_form.html
major_delete = DeleteView.as_view(model =  Major, template_name="mainapp/major_delete.html", success_url='/main/major/list') # object_confirm_delete.html

#방법2 :  class를 재정의하고 as_view를 이용하는 방법 
'''
class MajorListView(ListView):
    model=Major
    paginate_by=10
    template_name='mainapp/major_all_list.html'

    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)
        #print (context)
        paginator = context['paginator']
        page_numbers_range = 10  # Display 5 page numbers
        max_index = len(paginator.page_range)

        page = self.request.GET.get('page')
        print ('self.request', self.request)
        current_page = int(page) if page else 1

        start_index = int((current_page - 1) / page_numbers_range) * page_numbers_range
        end_index = start_index + page_numbers_range
        if end_index >= max_index:
            end_index = max_index

        page_range = paginator.page_range[start_index:end_index]
        context['page_range'] = page_range
        print(context)
        return context

major_list = MajorListView.as_view()
'''
#방법3 : method를 사용하는 방법
@csrf_exempt
def major_list (request):
    if request.method == 'GET' :
        page = request.GET.get('page', 1)
        template_name='mainapp/major_all_list.html'
        majors = Major.objects.all()
    else: #search         
        template_name='mainapp/major_all_list_2.html'
        mydata = request.POST.get('mydata', '')   
        page = request.POST.get('page', 1)
        majors = Major.objects.filter(title__contains = mydata)

    paginator = Paginator(majors, 5) # Show 5 contacts per page
    try:
        page_obj = paginator.page(page)
    except PageNotAnInteger:
        page_obj = paginator.page(1)
    except EmptyPage:
        page_obj = paginator.page(paginator.num_pages)

    page_numbers_range = 5  # Display only 5 page numbers
    max_index = len(paginator.page_range)
    current_page = int(page) if page else 1
    start_index = int((current_page - 1) / page_numbers_range) * page_numbers_range
    end_index = start_index + page_numbers_range
    if end_index >= max_index:
        end_index = max_index

    page_range = paginator.page_range[start_index:end_index]
    major = paginator.get_page(page)

    print(major)
    return render(request, template_name, {'major_list':major, 'paginator':paginator,  'is_paginated': True, 
              'page_obj':page_obj,'object_list':page_obj, 'page_range': page_range })

@csrf_exempt
def student_list (request):
    if request.method == 'GET' :
        page = request.GET.get('page', 1)
        template_name='mainapp/student_all_list.html'
        students = Student.objects.all()
    else: #search         
        template_name='mainapp/student_all_list_2.html'
        mydata = request.POST.get('mydata', '')   
        page = request.POST.get('page', 1)
        students1 = Student.objects.filter(name__contains = mydata)
        students2 = Student.objects.filter(skill__contains = mydata)
        students3 = Student.objects.filter(major__title__contains = mydata)
        students = students1 | students2 | students3

    paginator = Paginator(students, 5) # Show 5 contacts per page
    try:
        page_obj = paginator.page(page)
    except PageNotAnInteger:
        page_obj = paginator.page(1)
    except EmptyPage:
        page_obj = paginator.page(paginator.num_pages)

    page_numbers_range = 5  # Display only 5 page numbers
    max_index = len(paginator.page_range)
    current_page = int(page) if page else 1
    start_index = int((current_page - 1) / page_numbers_range) * page_numbers_range
    end_index = start_index + page_numbers_range
    if end_index >= max_index:
        end_index = max_index

    page_range = paginator.page_range[start_index:end_index]
    student = paginator.get_page(page)

    return render(request, template_name, {'student_list':student, 'paginator':paginator,  'is_paginated': True, 
              'page_obj':page_obj,'object_list':page_obj, 'page_range': page_range })


def index(request):
    response = render(request, 'mainapp/index.html', {'message':"Student Project"})    
    return response

